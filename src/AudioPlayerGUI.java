package edu.ucsb.cs56.projects.music.mp3_player;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.util.Vector;

/* Third party API: JLayer (http://www.javazoom.net/javalayer/javalayer.html)
    used to play MP3 files
 */
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

/* Third party API: BeagleBuddy (https:/www.beaglebuddy.com)
    used to read metadata within MP3 files
 */

import com.beaglebuddy.id3.pojo.AttachedPicture;
import com.beaglebuddy.mp3.MP3;
import com.beaglebuddy.id3.enums.PictureType;

/**
 * AudioPlayerGUI represents a GUI for an MP3 player. (It also has functionality, which it shouldn't, if it is only going to be a GUI)
 * Current functions:
 * Displays ID3 metadata of MP3 song files(found in a specified folder) in a table
 * Displays lyrics of a selected song in a text area
 * Displays album artwork of a selected song in as an icon in a label
 * Plays a selected song (does not pause, does not stop until song is finished)
 *
 * For a quick reference guide to more GUI components, see http://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
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
    private JPanel albumInfoPanel;
    private JPanel lyricsInfoPanel;

    private JTable infoTable;
    private JScrollPane tableScroller;
    private Vector<String> columnNames;
    private Vector<Vector> songRows;

    private JToolBar controlBar;
    private JTextField currentlyPlaying;
    private String currentlySelectedSong;
    private JButton playButton;
    private JButton nextButton;
    private JButton prevButton;

    private JTextArea lyricsTextArea;
    private JScrollPane lyricsScroller;
    private JTextField lyricsTextAreaDescriber;

    private JLabel albumImageLabel;
    private ImageIcon defaultAlbumImage;
    private JTextField albumImageLabelDescriber;

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
        albumInfoPanel = new JPanel();
        lyricsInfoPanel = new JPanel();
        currentlyPlaying = new JTextField();
        albumImageLabelDescriber = new JTextField();
        lyricsTextAreaDescriber = new JTextField();
        lyricsTextArea = new JTextArea();
        columnNames = new Vector<String>();
        songRows = new Vector<Vector>();
        controlBar = new JToolBar();
        playButton = new JButton();
        nextButton = new JButton();
        prevButton = new JButton();
        albumImageLabel = new JLabel();
        defaultAlbumImage = new ImageIcon("resources/images/noAlbum.png");
        imgLocation = "resources/images/";
        songLocation = "resources/";
        currentlySelectedSong = "";
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
        frame.setSize(800,600);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        albumInfoPanel.setLayout(new BorderLayout());
        lyricsInfoPanel.setLayout(new BorderLayout());
        currentlyPlaying.setText("Currently playing: ");
        currentlyPlaying.setHorizontalAlignment(JTextField.CENTER);
        currentlyPlaying.setEditable(false);
        modifyButton(playButton, "play.gif", "Play the currently selected song");
        modifyButton(nextButton, "next.gif", "Go to the next song");
        modifyButton(prevButton, "prev.gif", "Go to the previous song");
        controlBar.add(prevButton);
        controlBar.add(playButton);
        controlBar.add(nextButton);
        controlBar.setFloatable(false);
        lyricsTextArea.setRows(5);
        lyricsTextArea.setColumns(20);
        lyricsTextArea.setEditable(false);
        lyricsTextArea.setLineWrap(true);
        lyricsTextArea.setWrapStyleWord(true);
        lyricsTextAreaDescriber.setText("Lyrics for selected song");
        lyricsTextAreaDescriber.setEditable(false);
        lyricsTextAreaDescriber.setHorizontalAlignment(JTextField.CENTER);
        lyricsScroller = new JScrollPane(lyricsTextArea);
        lyricsScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        albumImageLabel.setIcon(defaultAlbumImage);
        albumImageLabelDescriber.setText("Album artwork for selected song");
        albumImageLabelDescriber.setEditable(false);
        albumImageLabelDescriber.setHorizontalAlignment(JTextField.CENTER);
        populateTable(songLocation);
        infoTable = new JTable(songRows, columnNames);
        infoTable.getTableHeader().setBackground(Color.CYAN);
        infoTable.getTableHeader().setReorderingAllowed(false);
        infoTable.setEnabled(false);
        tableScroller = new JScrollPane(infoTable);
        addAllActionListeners();
        // STUB FINISH ME
    }

    /**
     * adds all modified GUI components to main containers of GUI
     */

    public void addGUIComponents() {
        controlPanel.add(currentlyPlaying);
        controlPanel.add(controlBar);

        albumInfoPanel.add(BorderLayout.NORTH, albumImageLabelDescriber);
        albumInfoPanel.add(BorderLayout.CENTER, albumImageLabel);

        lyricsInfoPanel.add(BorderLayout.NORTH,lyricsTextAreaDescriber);
        lyricsInfoPanel.add(BorderLayout.CENTER,lyricsScroller);

        songInfoPanel.add(lyricsInfoPanel);
        songInfoPanel.add(albumInfoPanel);

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
     * verifies whether a file is an MP3 file
     * @param song the file that is being verified as an MP3
     * @return boolean true or false if the file is an MP3 file
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
     * @param songTitle name of song file as dictated by its ID3Tag metadata
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
     * changes the album artwork of the albumImageLabel depending on which song is clicked on the infoTable
     * uses BeagleBuddy parser to get byte array representing image data of the song file
     * @param songTitle name of song file as dictated by its ID3Tag metadata
     */

    public void modifyAlbumImageLabel(String songTitle) {
        File[] fileArray = new File(songLocation).listFiles();
        for (File file : fileArray) {
            if (isMP3(file)) {
                try {
                    MP3 songMP3 = new MP3(file);
                    if (songMP3.getTitle().equals(songTitle)) {
                        albumImageLabel.setIcon(defaultAlbumImage); // Resets back to default album image in case clicked song has no album artwork
                        AttachedPicture albumPicData = songMP3.getPicture(PictureType.FRONT_COVER);
                        if (albumPicData.getImage().length != 0) {
                            // Creates image from byte array representing song album artwork , then creates new scaled image from that
                            ImageIcon albumImageIcon = new ImageIcon(albumPicData.getImage());
                            Image albumImage = albumImageIcon.getImage();
                            Image scaledAlbumImage = albumImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                            albumImageLabel.setIcon(new ImageIcon(scaledAlbumImage));
                        }
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
                currentlySelectedSong = songTitle;
                modifyLyricTextBox(songTitle);
                modifyAlbumImageLabel(songTitle);
            }
        });
    }

    /**
     * inner class for playButton
     */

    public class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File[] fileArray = new File(songLocation).listFiles();
            for (File file : fileArray) {
                if (isMP3(file)) {
                    try {
                        MP3 songMP3 = new MP3(file);
                        if (songMP3.getTitle().equals(currentlySelectedSong)) {
                            currentlyPlaying.setText("Currently playing: " + currentlySelectedSong);
                            // Lambda expression for abstract run method in Runnable interface
                            Runnable playJob = () -> {
                                try {
                                    FileInputStream fis = new FileInputStream(file);
                                    BufferedInputStream bis = new BufferedInputStream(fis);
                                    Player songPlayer = new Player(bis);
                                    songPlayer.play();
                                } catch (IOException | JavaLayerException ex) {
                                    ex.printStackTrace();
                                }
                            };
                            Thread songMediaThread = new Thread(playJob);
                            songMediaThread.start();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
       inner class for nextButton
    */

    public class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // STUB FINISH ME
        }
    }

    /**
       inner class for prevButton
    */

    public class PrevButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
