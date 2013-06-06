package edu.ucsb.cs56.projects.music.mp3_player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.media.*;

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
    private Vector<String> columnNames;
    private Vector<Vector> songRows;
    private JTextField currentlyPlaying;

    private JFrame frame;
    private JPanel controlPanel;
    private Container panel;
    private JScrollPane tableScroller;

    /**
       launches the JFrame, populates it with previous, play / pause, skip buttons, current song field, and table with song information
    */
    
    public void go(){
	frame = new JFrame();
	panel = frame.getContentPane();
	controlPanel = new JPanel();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	playButton = new JButton(">");
	nextButton = new JButton(">>");
	prevButton = new JButton("<<");

	playButton.addActionListener(new PlayButtonListener());
	nextButton.addActionListener(new NextButtonListener());
	prevButton.addActionListener(new PrevButtonListener());

	
	currentlyPlaying = new JTextField("");
	currentlyPlaying.setEditable(false);

	columnNames = new Vector<String>();
        columnNames.add("Name");
        columnNames.add("Artist");
        columnNames.add("Genre");
	
	songRows = new Vector<Vector>();
	
	populateTable("resources");

	infoTable = new JTable(songRows, columnNames);
	tableScroller = new JScrollPane(infoTable);
	tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	infoTable.setFillsViewportHeight(true);
	
	controlPanel.add(prevButton);
	controlPanel.add(playButton);
	controlPanel.add(nextButton);
	controlPanel.add(currentlyPlaying);
	
	frame.getContentPane().add(BorderLayout.NORTH, controlPanel);
	frame.getContentPane().add(BorderLayout.CENTER, tableScroller);
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
	// this might need to throw an illegal argument exception
	/* get names of files in resources folder */ 
	File[] fileList = new File(folderName).listFiles();
	/* Vector of Vectors, with each inner Vector containing information about a song */
	for(File song : fileList)
	    {
		if(song.isFile())
		    {
			/* thisSong represents a row in the table of songs corresponding to a specific song */
			Vector<String> thisSong = new Vector<String>();
			thisSong.add(song.getName());
			/* add this row to the 2D Vector that contains all the songs in the table */
			songRows.addElement(thisSong);
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
	    //@@STUB FINISH ME
	    MusicPlayer.play("Snare.mp3");
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
