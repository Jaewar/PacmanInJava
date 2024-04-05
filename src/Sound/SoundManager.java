package Sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 30, 2024
 * Class Description: 
 * 		This class will act as the Sound source. plays a selected clip when called, adjustments for volume control added in the event
 * 		it needs to be included.
 */

public class SoundManager {

	Clip clip;
	URL soundURL[] = new URL[30];
	FloatControl fc;
	public int volumeScale = 1;
	float volume;
	
	AudioFormat format;
	Info info;

	public SoundManager() {
		soundURL[0] = getClass().getResource("/sounds/pacman_beginning.wav");
		soundURL[1] = getClass().getResource("/sounds/pacman_chomp.wav");
		soundURL[2] = getClass().getResource("/sounds/pacman_death.wav");
		soundURL[3] = getClass().getResource("/sounds/pacman_eatghost.wav");
		soundURL[4] = getClass().getResource("/sounds/siren_1.wav");
		
		info = new DataLine.Info(Clip.class, format);
	}

	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.start();
	}
	
	public void playMusic(int i) {
		setFile(i);
		play();	
	}

	public void playSE(int i) {
		// method created to play a sound effect without override the previous (I.E
		// eating pellets)
		try {
			if ((clip == null) || (!clip.isActive())) {
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
				clip = (Clip)AudioSystem.getLine(info);
				clip.open(ais);
				fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				clip.start();
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

	public void checkVolume() {
		if (volumeScale > 5) {
			volumeScale = 5;
		}
		switch (volumeScale) {
		case 0:
			volume = -80f;
			break;
		case 1:
			volume = -20f;
			break;
		case 2:
			volume = -12f;
			break;
		case 3:
			volume = -5f;
			break;
		case 4:
			volume = 1f;
			break;
		case 5:
			volume = 6f;
			break;
		}

		fc.setValue(volume);
	}

}
