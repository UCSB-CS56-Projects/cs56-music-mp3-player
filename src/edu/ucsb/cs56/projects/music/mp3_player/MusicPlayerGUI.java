import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
   MusicPlayerGUI represents a GUI for interacting with the MusicPlayer class. A MusicPlayer can play and pause an mp3, as well as skip to another song in a list of songs in a folder. 
   
   @author Ian Vernon
   @author Evan Moelter
   @version CS56 Spring 2013
*/

public class MusicPlayerGUI
{
    private JButton playButton;
    private JButton nextButton;
    private JButton prevButton;
    
    private JFrame frame;
    private JTable infoTable;
    private JTextField currentlyPlaying;

    private MusicPlayer mp;

    /**
       launches the JFrame, populates it with previous, play / pause, skip buttons, current song field, and table with song information
    */
    
    public void go(){
	frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// @@@ STUB FINISH ME
    }
    
    /**
       inner class for playButton
    */
    
    public class PlayButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    //@@STUB FINISH ME
	}
    }

    /**
       inner class for nextButton
    */
    
    public class NextButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    //@@STUB FINISH ME
	}
    }

    /**
       inner class for prevButton
    */
    
    public class PrevButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    //@@STUB FINISH ME
	}
    }

    /**
       main method to create instance of GUI, start it
    */
    
    public static void main(String[] args)
    {
	MusicPlayerGUI mpGUI = new MusicPlayerGUI();
	mpGUI.go();
    }
    
}
