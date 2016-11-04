package edu.ucsb.cs56.projects.music.mp3_player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
   AudioPlayer is a class that plays music / audio files. 
   
   source code is from: http://stackoverflow.com/questions/14959566/java-error-when-trying-to-use-mp3plugin-for-playing-an-mp3-file/14959818#14959818
   
   
   @author fredcrs ( http://stackoverflow.com/users/377398/fredcrs )
   @author Ian Vernon
   @author Evan Moelter
   @version CS56 Spring 2013

*/

public class AudioPlayer
{
    /* buffer size of 4096 bytes - is a standard */
    private int bufferSize = 4096; 

    /* volatile modifer guarantees communication happens between threads
       when one thread writes to volatile variable, and another thread sees that write, first thread
       tells second about memory's contents up until that write
    */

    private volatile boolean paused = false;
    private final Object lock = new Object();
    private SourceDataLine line;
    private int secondsFade = 0;
    /*private ArrayList<AudioPlayerListener> _listeners = new ArrayList<AudioPlayerListener>();*/

    /** stops audio that is playing if it is currently playing */
    
    public void stop()
    {
        if(line != null)
        {
            line.stop();
            line.close();
        }
    }

    /** checks to see if currently playing audio is paused
	@return true if currently playing audio is paused
    */

    public boolean isPaused()
    {
        return this.paused;
    }
    
    /** pauses currently playing audio if it is playing */
    
    public void pause()
    {
        if(!this.isPaused())
            paused = true;
    }

    /** resumes currently playing audio if it is paused */

    public void resume()
    {
        if(this.isPaused())
        {
	    /*
	      ensures threads are synchronized with synchronized statement,
	      which synchronizes a specific part of a method (as opposed to 
	      using full synchronized modifier in method declaration, which
	      would make all parts of a method synchronized
	    */
            synchronized(lock){
                lock.notifyAll();
                paused = false;
            }
        }
    }

    /** plays the audio from file
	@param file file to play audio from
	@exception UnsupportedAudioFileException if audio format is one that cannot be played
	@exception IOException if stream written to file cannot be opened / closed
	@exception LineUnavailableException if file is unavailable / is in use by another application
	@exception InterruptedException if thread is waiting, sleeping, otherwise occupied
    */
	public void play(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
    {
        AudioInputStream encoded = AudioSystem.getAudioInputStream(file);
        AudioFormat encodedFormat = encoded.getFormat();
        AudioFormat decodedFormat = this.getDecodedFormat(encodedFormat);
        Long duration = null;
        AudioInputStream currentDecoded = AudioSystem.getAudioInputStream(decodedFormat, encoded);
        line = AudioSystem.getSourceDataLine(decodedFormat);
        line.open(decodedFormat);
        line.start();
        boolean fezFadeIn = false;
        boolean fezFadeOut = false;
        byte[] b = new byte[this.bufferSize];
        int i = 0;
        Map properties = null;
        try 
        {
            properties = AudioUtil.getMetadata(file);
            duration = (Long) properties.get("duration");
        } 
        catch (Exception ex)
        {
            duration = 0L;
        }

        duration = duration < 0 ? 0 : duration;

        synchronized(lock)
        {
            //parameter to fade in and fade out audio
            long paramFade = (secondsFade*2+1)*1000000;
            while(true)
            {
                if(secondsFade > 0 && !fezFadeIn && duration >= paramFade)
                {
                    fezFadeIn = true;
                    fadeInAsync(this.secondsFade);
                }

                if( secondsFade > 0 &&
                        duration > paramFade &&
                        !fezFadeOut &&
                        line.getMicrosecondPosition() >= duration - ((this.secondsFade+1)*1000000) )
                {
                    //this.fireAboutToFinish();
                    fadeOutAsync(this.secondsFade);
                    fezFadeOut = true;
                }

                if(paused == true)
                {
                    line.stop();
                    lock.wait();
                    line.start();
                }

                i = currentDecoded.read(b, 0, b.length);
                if(i == -1)
                    break;

                line.write(b, 0, i);
            }
        }

        if(  !fezFadeOut && line.isOpen() )
            //this.fireAboutToFinish();

        line.drain();
        line.stop();
        line.close();
        currentDecoded.close();
        encoded.close();
    }
    /** fades in audio 
	@param seconds amount of seconds it takes for audio to fully fade in
    */

    public synchronized void fadeInAsync(final int seconds)
    {
        if(line != null && line.isOpen())
        {
            Thread t = new Thread(new Fader(true, this, secondsFade));
            t.start();
        }
    }

    /** fades out audio
       @param seconds amount of seconds it takes for audio to fully fade out
    */

    public synchronized void fadeOutAsync(final int seconds)
    {
        if(line != null && line.isOpen())
        {
            Thread t = new Thread(new Fader(false, this, secondsFade));
            t.start();            
        }
    }

    /** sets volume to value
	@param value value between 0.0 and 1.0 to set volume to. converted to decibels and set accordingly
    */

    public void setVolume(double value) 
    {
        if(!line.isOpen())
            return;
        // value is between 0 and 1
        value = (value<=0.0)? 0.0001 : ((value>1.0)? 1.0 : value);
        try
        {
            float dB = (float)(Math.log(value)/Math.log(10.0)*20.0);
            ((FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN)).setValue(dB);
        }
        catch(Exception ex)
        {
	//fix this later
        }
    }

    /** checks if audio is currently playing
	@return true if audio is currently playing
    */

    public boolean isPlaying()
    {
        return (line != null && line.isOpen());
    }

    /** decodes format of audio file with information about encoding, sample rate, sample size in bits, # of channels, frame size, and frame rate
	@param format encoded AudioFormat  that has arrangement of data in sound stream / how bits in binary sound data are tsored
	@return decodedFormat AudioFormat with information about how bits are stored in binary sound data
    */
    protected AudioFormat getDecodedFormat(AudioFormat format)
    {
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,  // Encoding to use
                format.getSampleRate(),           // sample rate (same as base format)
                16,               // sample size in bits (thx to Javazoom)
                format.getChannels(),             // # of Channels
                format.getChannels()*2,           // Frame Size
                format.getSampleRate(),           // Frame Rate
                false                 // Big Endian
        );
        return decodedFormat;    
    }

    /** gets the size of the buffer that audio plays out through
       @return bufferSize size of buffer
    */

    public int getBufferSize()
    {
        return bufferSize;
    }

    /** sets the size of the buffer to a value greater than zero */

    public void setBufferSize(int bufferSize)
    {
        if(bufferSize <= 0)
            return;
        this.bufferSize = bufferSize;
    }

    /**
     * @return number of seconds if takes for audio to fade out
     */
    public int getSecondsFade() {
        return secondsFade;
    }

    /**
     * @param secondsFade the number of seconds it will take for audio to fade out
     */
    public void setSecondsFade(int secondsFade) {
        if(secondsFade < 0 || secondsFade > 10)
            throw new IllegalArgumentException("Number of seconds cannot be < 0 or > 10: "+secondsFade);
        this.secondsFade = secondsFade;
    }

    /** AudioPlayerListener class... where is this?
     * @param a AudioPlayerListener to add to list of AudioPlayer
     
    public void addAudioPlayerListener(AudioPlayerListener a)
    {
        this._listeners.add(a);
    }

    /**
     * @param a AudioPlayerListener to remove from the list of audio player listeners for this AudioPlayer
     
     public void removeAudioPlayerListener(AudioPlayerListener a)
    {
        this._listeners.remove(a);
    }

    /**
     * for every AudioPlayerListener in the list of listeners, tell AudioPlayer that it's about to finish playing
     

    private void fireAboutToFinish()
    {
        for(AudioPlayerListener a : this._listeners)
            a.aboutToFinish(this);
    }
    */
}

/** work on later if time */ 
class Fader implements Runnable
{
    private boolean fadeIn;
    private int seconds=0;
    private final AudioPlayer player;
    private float increaseParam;

    public Fader(boolean fadeIn, AudioPlayer player, int secondsToFade) 
    {
        this.fadeIn = fadeIn;
        this.seconds = secondsToFade;
        this.player = player;
        if(fadeIn)
            increaseParam = 0.01F;
        else
            increaseParam = -0.01F;
    }

    @Override
    public void run() 
    {
        try
        {
            encapsulateRun();
        }
        catch(Exception ex)
        {
            if(fadeIn)
                player.setVolume(1.0F);
            else
                player.setVolume(0.0F);
        }
    }

    private void encapsulateRun() throws Exception
    {
        synchronized(player)
        {            
            float per;
            if(fadeIn)
            {
                Logger.getLogger(getClass().getName()).info("Fazendo fade in");
                per = 0.0F;
            }
            else
            {
                Logger.getLogger(getClass().getName()).info("Fazendo fade out");
                per = 1.0F;
            }
            player.setVolume(per);
            if(fadeIn)
            {
                while(per < 1.00F)
                {
                    per = per + increaseParam;
                    player.setVolume(per);
                    Thread.sleep(10*seconds);
                }
            }
            else
            {
                while(per > 0.00F)
                {
                    per = per + increaseParam;
                    player.setVolume(per);
                    Thread.sleep(10*seconds);
                }
            }
        }
    }

}
