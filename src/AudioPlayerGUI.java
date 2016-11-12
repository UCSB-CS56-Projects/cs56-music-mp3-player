package edu.ucsb.cs56.projects.music.mp3_player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* Third part library: Apache Tika (https://tika.apache.org/)
   used to read metadata within song files */
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
   AudioPlayerGUI represents a GUI for interacting with the AudioPlayer class. A AudioPlayer can play and pause an mp3, as well as skip to another song in a list of songs in a folder. 
   
   @author Ian Vernon
   @author Evan Moelter
   @version CS56 Spring 2013
*/

public class AudioPlayerGUI
{
    private JButton playButton;
    private JButton nextButton;
    private JButton prevButton;
    private JTable infoTable;
    private Vector<String> columnNames;
    private Vector<Vector> songRows;
    private JTextField currentlyPlaying;
    private JTextField prevText;
    private JTextField playText;
    private JTextField nextText;
    private JTextArea description;

    private JFrame frame;
    private JPanel controlPanel;
    private Container panel;
    private JScrollPane tableScroller;
    private JLabel javaIcon;

    /**
       launches the JFrame, populates it with previous, play / pause, skip buttons, current song field, and table with song information
    */
    
    public void go(){
	// Setting up the frame and panel of the GUI
	frame = new JFrame();
	panel = frame.getContentPane();
	controlPanel = new JPanel();
	frame.setTitle("CS 56 MP3 Player");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javaIcon = new JLabel(new ImageIcon("resources/javaSymbol.png"));
	description = new JTextArea("This is an MP3 player.\n", 250, 250);
	description.append("It displays information about MP3 files in a table.\n");
	description.append("The above buttons currently have no functionality.\n");
	description.append("It currently does not play music.\n");
	
	// Creating symbols for buttons based on Unicode decimal numbers
	String playButtonSymbol = Character.toString((char)9654);
	String nextButtonSymbol = Character.toString((char)9197);
	String prevButtonSymbol = Character.toString((char)9198);

	playButton = new JButton(playButtonSymbol);
	nextButton = new JButton(nextButtonSymbol);
	prevButton = new JButton(prevButtonSymbol);

	// Listeners for above buttons (CURRENTLY NEEDS IMPLEMENTATION)
	playButton.addActionListener(new PlayButtonListener());
	nextButton.addActionListener(new NextButtonListener());
	prevButton.addActionListener(new PrevButtonListener());
	
	currentlyPlaying = new JTextField("");
	currentlyPlaying.setEditable(false);

	columnNames = new Vector<String>();
        columnNames.add("Title");
        columnNames.add("Artist");
        columnNames.add("Genre");
	columnNames.add("Album");
	
	songRows = new Vector<Vector>();
	
	populateTable("resources");

	infoTable = new JTable(songRows, columnNames);
	tableScroller = new JScrollPane(infoTable);
	tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	infoTable.setFillsViewportHeight(true);
	
	controlPanel.add(prevButton);
	controlPanel.add(playButton);
	controlPanel.add(nextButton);
	controlPanel.add(currentlyPlaying);
	
	frame.getContentPane().add(BorderLayout.NORTH, controlPanel);
	frame.getContentPane().add(BorderLayout.CENTER, tableScroller);
	frame.getContentPane().add(BorderLayout.SOUTH, javaIcon);
	//frame.getContentPane().add(BorderLayout.SOUTH, description);
	frame.setSize(500,500);
	frame.setVisible(true);
	
	// @@@ STUB FINISH ME
    }
    
   
    /**
       populates infoTable with information about the songs contained in folder folderName
       @param folderName folder in which desired files to play in player are stored
    */

    public void populateTable(String folderName)
    {
	/* get names of files in resources folder */ 
	File[] fileList = new File(folderName).listFiles();
	/* Vector of Vectors, with each inner Vector containing information about a song */
	for(File song : fileList)
	    {
		/*ext represents the extension of the song (eg mp3, wav, mp4 etc)
		  used to check if the song is an mp3*/
		String ext ="";
		String nameOfSong = song.getName();
		int i = nameOfSong.lastIndexOf('.');
		if (i>0) ext = nameOfSong.substring(i+1);
		if(song.isFile() && ext.equals("mp3"))
		    {
			// third party library Apache Tika used below
			try
			    {
				InputStream iStream = new FileInputStream(song);
				ContentHandler handler = new DefaultHandler();
				Metadata metadata = new Metadata();
				Parser parser = new Mp3Parser();
				ParseContext parseCtx = new ParseContext();
				parser.parse(iStream, handler, metadata, parseCtx);
				iStream.close();
				/* thisSong represents a row in the table of songs 
				   corresponding to a specific song */
				Vector<String> thisSong = new Vector<String>();
				thisSong.add(metadata.get("title"));
				thisSong.add(metadata.get("xmpDM:artist"));
				thisSong.add(metadata.get("xmpDM:genre"));
				thisSong.add(metadata.get("xmpDM:album"));
				/* add this row to the 2D Vector that contains all the songs in the table */
				songRows.addElement(thisSong);
			    }
			catch (FileNotFoundException e) {
			    e.printStackTrace();
			}
			catch (IOException e) {
			    e.printStackTrace();
			}
			catch (SAXException e) {
			    e.printStackTrace();
			}
			catch (TikaException e) {
			    e.printStackTrace();
			}
		    }
	    }
    }
    
    /**
       inner class for playButton
    */

    public class PlayButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    //@@ STUB FINISH ME
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
	AudioPlayerGUI mpGUI = new AudioPlayerGUI();
	mpGUI.go();
    }
    
}
