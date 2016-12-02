cs56-music-mp3-player
=====================

An app that can play music from an mp3 file

project history
===============
```
 |TBD | TBD | TBD | F16 (solipsism648) | SP13 (ianvernon) An app that can play music from an mp3 file
```

--------

Current State of Project:
- The project has AudioPlayerGUI with a JToolbar with previous, next, and play buttons, a JTable that autopopulates with file info from the resources/ folder, a JTextArea that populates with lyrics of a selected song, and a JLabel that holds a JIcon that displays the album artwork of a selected song.
- JavaSound code from an online example (source in javadoc) in AudioPlayer.java and AudioUtil.java. This currently does not work (see below), but may have some usefullness to the future coders of this repo.
- JMFTest.java is the code we tried to use with JMF (see below). Checkout the source link (in javadoc) if you're curious, but be warned that the JMF framework seems to be outdated.
- beaglebuddy.jar used to read MP3 file info
- jl1.0.1.jar used to control MP3 playback
- build.xml contains targets with descriptions.
- lib/ contains jar files used
- resources/ contain some sample files.
- resources/images/ holds image files used in the GUI

Summary of progress with useability reports:
- JavaFX  <br />
  We originally thought JavaFX would fulfill our needs, and it certainly has the functionality to do so. The problem lies in compiling and distributing. From cursory research, JFX does not live in a JAR file in the lib/ directory of your project, but instead needs to be built with a special JFX compiler or JFX ant task. Also, CSIL uses OpenJDK, which doesn't have JavaFX installed, but instead OpenJFX (http://openjdk.java.net/projects/openjfx/).  <br />
  Usability: This still has the potential to be the most elegant solution for this project, but it would require a full dedication to JFX over the course of 2 or 3 project point cycles.

- JavaMediaFramework (JMF)  <br />
  We then researched online and decided to use JMF, but after a lot of tinkering (and the fact that no audio would play), we discovered it hasn't been updated since the early 2000's.  <br /> 
  Useability: Plain and simple, it's an outdated framework that no longer works.

- JavaSound  <br />
  We then decided to use an implementation of an MP3 player we found online that uses the JavaSound API. To our dismay, still doesn't work. The AudioPlayer file was updated with JavaDoc / appropriate author information to facilitate use in the future. However, we can't figure out why no audio will play.  <br />
  Useability: The documentation for the framework was not very clear, but it is still a valid option to build this project around.

Best of luck!

Evan and Ian
Spring 2013
F16 final remarks:
=====================
- BeagleBuddy API <br />
  The BeagleBuddy API (beaglebuddy.jar) is used to read MP3 metadata. See www.beaglebuddy.com for more documentation on what this API does, and its added functionalities.
- JavaZOOM API (JLayer addition) <br />
  The JLayer API of the JavaZOOM project (jl1.0.1jar) is used to play MP3 files. See http://www.javazoom.net/javalayer/javalayer.html for more documentation on what this API does, and its capabilties in manipulating MP3 playback.
- Apache TIKA API <br />
  Apache TIKA API was originally used, before BeagleBuddy, to read ID3 tag metadata from an MP3 files. It proved to be too clunky, with heavy memory usage, but its capabilities might be useful in the future. See https://tika.apache.org/ for documentation. <br />
- AudioPlayerGUI.java <br />
  As of right now, ALL the functionality is found in the GUI portion of this project, and it shouldn't be if that file is supposed to ONLY be a GUI. This is a poor coding mistake, so refactoring the code to have a seperate class for functionality would be very OO! <br />
  
  Best of luck!
  
  Matthew Rodriguez
  Fall 2016
