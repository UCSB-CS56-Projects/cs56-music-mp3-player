cs56-music-mp3-player
=====================

An app that can play music from an mp3 file


Summary of progress: originally thought JavaFX would fulfill our needs. However, since CSIL uses OpenJDK, which doesn't have JavaFX installed, we cannot use it. We then researched online and decided to use JMF (Java Media Framework), but after a lot of tinkering (and the fact that no audio would play), we discovered it hasn't been updated since the early 2000's. We then decided to use an implementation of an MP3 player we found online, which, to our dismay, still doesn't work. The AudioPlayer file was updated with JavaDoc / appropriate author information to facilitate use in the future. However, we can't figure out why no audio will play.