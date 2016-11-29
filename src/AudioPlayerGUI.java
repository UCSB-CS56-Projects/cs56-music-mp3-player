package edu.ucsb.cs56.projects.music.mp3_player;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.util.Vector;
<<<<<<< HEAD

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
=======
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
>>>>>>> aremote/master

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
<<<<<<< HEAD
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
=======
    private JTextField prevText;
    private JTextField playText;
    private JTextField nextText;
    private JTextArea description;

    private JFrame frame;
    private JPanel controlPanel;
    private Container panel;
    private JScrollPane tableScroller;
    private JLabel javaIcon;
>>>>>>> aremote/master

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
    
<<<<<<< HEAD
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
=======
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
>>>>>>> aremote/master
    }

    /**
     * verifies whether a file is an MP3 file
     * @param song the file that is being verified as an MP3
     * @return boolean true or false if the file is an MP3 file
     */

<<<<<<< HEAD
    public boolean isMP3(File song) {
        if(!(song.isFile())) return false;
        String nameOfSong = song.getName();
        String fileExt = "";
        int index = nameOfSong.lastIndexOf('.');
        if (index > 0) fileExt = nameOfSong.substring(index + 1);
        if (fileExt.equals("mp3")) return true;
        else return false;
=======
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
>>>>>>> aremote/master
    }

    /**
     * populates a row in the infoTable with data related to the specific song in that row
     * see BeagleBuddy Javadoc (http://www.beaglebuddy.com/content/pages/javadocs/index.html) for more methods to access MP3 metadata
     * @param song MP3 file to be parsed by BeagleBuddy
     * @return thisSong vector of strings representing metadata of song
     */

<<<<<<< HEAD
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
=======
    public class PlayButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    //@@ STUB FINISH ME
	}
>>>>>>> aremote/master
    }

    /**
       inner class for nextButton
    */
<<<<<<< HEAD

    public class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // STUB FINISH ME
        }
=======
    
    public class NextButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    //@@STUB FINISH ME  
	}
>>>>>>> aremote/master
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
