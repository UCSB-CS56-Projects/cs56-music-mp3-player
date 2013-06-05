package edu.ucsb.cs56.projects.music.mp3_player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.util.ArrayList;

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
    private Object[] columnNames = {"Name", "Artist", "Genre"};
    private JTextField currentlyPlaying;

    private JFrame frame;
    private JPanel controlPanel;
    private Container panel;
    private JScrollPane tableScroller;

    private ArrayList<String> songNames;

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

	/* get names of mp3s in resources folder */
	File[] fileList = new File("resources").listFiles();
	songNames = new ArrayList<String>();
	for(File song : fileList)
	    {
		if(song.isFile())
		    {
			System.out.println(song.getName());
			songNames.add(song.getName());
			System.out.println("songNames.size()" + songNames.size());
		    }
	    }
	
	/* initialize table for song names, add names to table */

	Object[][] songs = new Object[songNames.size()][1];
       
	for(int i = 0; i < songNames.size(); i++)
	    {
		System.out.println("songNames.size()" + songNames.size());
		songs[i][1] = songNames.get(i);
		}
	infoTable = new JTable(songs, columnNames);
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
