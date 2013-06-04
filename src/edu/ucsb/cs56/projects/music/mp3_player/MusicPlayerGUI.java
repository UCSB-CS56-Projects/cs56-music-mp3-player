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
    private JTable infoTable;
    private String[] columnNames = {"Name", "Artist", "Album", "Genre"};
    private JTextField currentlyPlaying;

    private JFrame frame;
    private JPanel controlPanel;
    private JPanel tablePanel;
    private Container panel;

    private MusicPlayer mp;

    /**
       launches the JFrame, populates it with previous, play / pause, skip buttons, current song field, and table with song information
    */
    
    public void go(){
	frame = new JFrame();
	panel = frame.getContentPane();
	controlPanel = new JPanel();
	tablePanel = new JPanel();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	playButton = new JButton(">");
	nextButton = new JButton(">>");
	prevButton = new JButton("<<");

	playButton.addActionListener(new PlayButtonListener());
	nextButton.addActionListener(new NextButtonListener());
	prevButton.addActionListener(new PrevButtonListener());
	
	currentlyPlaying = new JTextField("");
	currentlyPlaying.setEditable(false);
	
	Object[][] placeHolder = new Object[5][5];

	infoTable = new JTable(placeHolder, columnNames);

	controlPanel.add(prevButton);
	controlPanel.add(playButton);
	controlPanel.add(nextButton);
	controlPanel.add(currentlyPlaying);
	tablePanel.add(infoTable);
	
	frame.getContentPane().add(BorderLayout.NORTH, controlPanel);
	frame.getContentPane().add(BorderLayout.CENTER, tablePanel);
	frame.setSize(500,500);
	frame.setVisible(true);
	
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
