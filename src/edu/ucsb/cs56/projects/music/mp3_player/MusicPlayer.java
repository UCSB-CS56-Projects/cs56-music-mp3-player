package edu.ucsb.cs56.projects.music.mp3_player;

import java.io.*;

import javax.media.*;
import javax.media.Format;
import javax.media.format.AudioFormat;

/**
   MusicPlayer represents a music player.
*/

public class MusicPlayer{
    //each MediaPlayer plays one song
    // private ArrayList<MediaPlayer> songPlayers;

    public static void play(String filename)
    {
    	Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		try{
			Player player = Manager.createPlayer(new MediaLocator(new File("resources/" + filename).toURI().toURL()));
			player.start();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("done");
    }
    
}
