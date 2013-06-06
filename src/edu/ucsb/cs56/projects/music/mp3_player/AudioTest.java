package edu.ucsb.cs56.projects.music.mp3_player;

import java.io.File;
import java.io.*;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

import java.net.URL;

public class AudioTest {
	public static void main(String[] args) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		//File f = new File("/resources/3DayWeekend.mp3");
		//URL u = f.toURI().toURL();
		try{
			Player player = Manager.createPlayer(new MediaLocator(new File("resources/3DayWeekend.mp3").toURI().toURL()));
			System.out.println("starting...");
			player.start();
			System.out.println("ending...");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}