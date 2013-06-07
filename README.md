cs56-music-mp3-player
=====================

An app that can play music from an mp3 file

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Current State of Project:
- The project has AudioPlayerGUI with previous, next, and play buttons, as well as a JTable that autopopulates with file info from the resources/ folder.
- JavaSound code from an online example (source in javadoc) in AudioPlayer.java and AudioUtil.java. This currently does not work (see below), but may have some usefullness to the future coders of this repo.
- JMFTest.java is the code we tried to use with JMF (see below). Checkout the source link (in javadoc) if you're curious, but be warned that the JMF framework seems to be outdated.
- build.xml contains targets with descriptions.
- lib/ contains JFM and JUnit jar files.
- resources contain some sample .wav and .mp3 files.

Summary of progress with useability reports:
JavaFX
We originally thought JavaFX would fulfill our needs, and it certainly has the functionality to do so. The problem lies in compiling and distributing. From cursory research, JFX does not live in a JAR file in the lib/ directory of your project, but instead needs to be built with a special JFX compiler or JFX ant task. Also, CSIL uses OpenJDK, which doesn't have JavaFX installed, but instead OpenJFX (http://openjdk.java.net/projects/openjfx/). 
Usability: This still has the potential to be the most elegant solution for this project, but it would require a full dedication to JFX over the course of 2 or 3 project point cycles.

JavaMediaFramework (JMF)
We then researched online and decided to use JMF, but after a lot of tinkering (and the fact that no audio would play), we discovered it hasn't been updated since the early 2000's. 
Useability: Plain and simple, it's an outdated framework that no longer works.

JavaSound
We then decided to use an implementation of an MP3 player we found online that uses the JavaSound API. To our dismay, still doesn't work. The AudioPlayer file was updated with JavaDoc / appropriate author information to facilitate use in the future. However, we can't figure out why no audio will play. 
Useability: The documentation for the framework was not very clear, but it is still a valid option to build this project around.

Best of luck!

Evan and Ian
Spring 2013

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~