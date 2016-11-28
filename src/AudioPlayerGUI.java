package edu.ucsb.cs56.projects.music.mp3_player;

import javax.swing.*;
import java.awt.Point;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import java.util.Vector;

/* Third party API: BeagleBuddy (https:/www.beaglebuddy.com)
    used to read metadata within mp3 files
 */
import com.beaglebuddy.mp3.MP3;

/**
 * AudioPlayerGUI represents a GUI for interacting with the AudioPlayer class. An AudioPlayer can play and pause an mp3, as well as skip to another song in a list of songs in a folder.
 *
 * @author Matthew Rodriguez
 * @version CS56 Fall 2016
*/

public class AudioPlayerGUI {

    // Instance variables of GUI

    private JFrame frame;
    private Container framePane;
    private JPanel controlPanel;
    private JPanel songInfoPanel;
    private JTable infoTable;
    private JScrollPane tableScroller;
    private JButton playButton;
    private JButton nextButton;
    private JButton prevButton;
    private JTextField currentlyPlaying;
    private JTextArea lyricsTextArea;
    private JScrollPane lyricsScroller;
    private Vector<String> columnNames;
    private Vector<Vector> songRows;
    private JToolBar controlBar;
    private String imgLocation;
    private String songLocation;

    /**
     * constructor for AudioPlayerGUI
     * constructs most instance variables except those that depend on the modification of certain variables
     */

    public AudioPlayerGUI() {
        frame = new JFrame();
        framePane = frame.getContentPane();
        controlPanel = new JPanel();
        songInfoPanel = new JPanel();
        currentlyPlaying = new JTextField();
        lyricsTextArea = new JTextArea();
        columnNames = new Vector<String>();
        songRows = new Vector<Vector>();
        playButton = new JButton();
        nextButton = new JButton();
        prevButton = new JButton();
        controlBar = new JToolBar();
        imgLocation = "resources/images/";
        songLocation = "resources/";
    }
    
    /**
     * launches the GUI, populating it with necessary components and data
     */

    public void run() {
        modifyGUI();
        addGUIComponents();
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * modifies all components of the GUI
     */

    public void modifyGUI() {
        frame.setTitle("CS 56 MP3 Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        // currentlyPlaying might need modification to dynamically be changed to currently playing song
        currentlyPlaying.setText("Currently playing: ");
        currentlyPlaying.setEditable(false);
        currentlyPlaying.setHorizontalAlignment(JTextField.CENTER);
        lyricsTextArea.append("Lyrics:\n");
        lyricsTextArea.setEditable(false);
        modifyButton(playButton, "play.gif", "Play the currently selected song");
        modifyButton(nextButton, "next.gif", "Go to the next song");
        modifyButton(prevButton, "prev.gif", "Go to the previous song");
        controlBar.add(prevButton);
        controlBar.add(playButton);
        controlBar.add(nextButton);
        controlBar.setFloatable(false);
        lyricsTextArea.setLineWrap(true);
        lyricsTextArea.setWrapStyleWord(true);
        lyricsTextArea.setRows(5);
        lyricsTextArea.setColumns(20);
        lyricsScroller = new JScrollPane(lyricsTextArea);
        lyricsScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        populateTable(songLocation);
        infoTable = new JTable(songRows, columnNames);
        infoTable.setEnabled(false);
        infoTable.getTableHeader().setBackground(Color.CYAN);
        infoTable.getTableHeader().setReorderingAllowed(false);
        tableScroller = new JScrollPane(infoTable);
        addAllActionListeners();
        // STUB FINISH ME
    }

    /**
     * adds all modified GUI components to main containers (controlPanel, framePane) of GUI
     */

    public void addGUIComponents() {
        controlPanel.add(currentlyPlaying);
        controlPanel.add(controlBar);

        songInfoPanel.add(lyricsScroller);

        framePane.add(BorderLayout.NORTH, controlPanel);
        framePane.add(BorderLayout.CENTER, tableScroller);
        framePane.add(BorderLayout.SOUTH, songInfoPanel);
        //STUB FINISH ME
    }

    /**
     * populates infoTable with information about the songs contained in folder folderName
     * @param folderName folder in which desired MP3 files to play in player are stored
     */

    public void populateTable(String folderName){
        columnNames.add("Title");
        columnNames.add("Artist");
        columnNames.add("Genre");
        columnNames.add("Album");
        File[] fileList = new File(folderName).listFiles();
        for(File file : fileList) {
            if(isMP3(file)) {
                songRows.add(tableID3Reader(file));
            }
        }
    }

    /**
     * verifies whether a file is both a song and an MP3
     * @param song the file that is being verified as an MP3
     * @return boolean true or false
     */

    public boolean isMP3(File song) {
        if(!(song.isFile())) return false;
        String nameOfSong = song.getName();
        String fileExt = "";
        int index = nameOfSong.lastIndexOf('.');
        if (index > 0) fileExt = nameOfSong.substring(index + 1);
        if (fileExt.equals("mp3")) return true;
        else return false;
    }

    /**
     * populates a row in the infoTable with data related to the specific song in that row
     * see BeagleBuddy Javadoc (http://www.beaglebuddy.com/content/pages/javadocs/index.html) for more methods to access MP3 metadata
     * @param song MP3 file to be parsed by BeagleBuddy
     * @return thisSong vector of strings representing metadata of song
     */

    public Vector<String> tableID3Reader(File song) {
        Vector<String> thisSong = new Vector<String>();
        try {
            MP3 mp3Song = new MP3(song);
            thisSong.add(mp3Song.getTitle()); //title
            thisSong.add(mp3Song.getBand()); //artist
            thisSong.add(mp3Song.getMusicType()); //genre
            thisSong.add(mp3Song.getAlbum()); //album
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thisSong;
    }

    /**
     * changes the lyrics in the lyricTextBox depending on which song is clicked on the infoTable
     * uses BeagleBuddy parser to get lyric ID3Tag metadata of song
     * @param songTitle name of song file as dictated by its ID3Tag metadats
     */

    public void modifyLyricTextBox(String songTitle) {
        File[] fileArray = new File(songLocation).listFiles();
        for (File file : fileArray) {
            if (isMP3(file)) {
                try {
                    MP3 songMP3 = new MP3(file);
                    if (songMP3.getTitle().equals(songTitle)) {
                        lyricsTextArea.setText(null);
                        lyricsTextArea.append("Lyrics for " + songTitle + ":\n");
                        lyricsTextArea.append(songMP3.getLyrics());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * changes an individual button
     * @param button button to be modified
     * @param imgName file name for icon of new button (found in resources/images)
     * @param toolTipTxt tool tip text for button
     */

    public void modifyButton(JButton button, String imgName, String toolTipTxt) {
        button.setIcon(new ImageIcon(imgLocation + imgName));
        button.setToolTipText(toolTipTxt);
    }

    /**
     * adds all action listener classes to each GUI component
     */

    public void addAllActionListeners() {
        playButton.addActionListener(new PlayButtonListener());
        nextButton.addActionListener(new NextButtonListener());
        prevButton.addActionListener(new PrevButtonListener());
        infoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                JTable thisTable = (JTable)e.getSource();
                int rowNum = thisTable.rowAtPoint(clickPoint);
                int columnNum = thisTable.columnAtPoint(clickPoint);
                String songTitle = (String)thisTable.getValueAt(rowNum, columnNum);
                modifyLyricTextBox(songTitle);
            }
        });
        /*
        infoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable thisTable = (JTable)e.getSource();
                    TableModel thisTableModel = thisTable.getModel();
                    int rowNum = thisTable.getSelectedRow();
                    int columnNum = thisTable.getSelectedColumn();
                    String songTitle = (String)thisTableModel.getValueAt(rowNum, columnNum);
                    lyricID3TagReader(songTitle);
                }
            }
        });
        */
    }

    /**
     * inner class for playButton
     */

    public class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // STUB FINISH ME
        }
    }

    /**
       inner class for nextButton
    */

    public class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // STUB FINISH ME
        }
    }

    /**
       inner class for prevButton
    */

    public class PrevButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // STUB FINISH ME
        }
    }

    /**
       main method to create instance of GUI, start it
    */

    public static void main(String[] args) {
        AudioPlayerGUI mpGUI = new AudioPlayerGUI();
        mpGUI.run();
    }
}
