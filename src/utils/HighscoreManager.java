package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import main.Engine;

/*
 * Author:			Jacob Stewart
 * Project:			Pacman in Java
 * Date Started:	March 30, 2024
 * Class Description: 
 * 		This class is responsible for file validation and reading and writing the current highscore.
 */

public class HighscoreManager {

	private Engine engine;
	
	private final String scoreFile = "highscore.txt";
	
	public HighscoreManager(Engine e) {
			this.engine = e;
	}
	
	public void writeScore() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(scoreFile));
			// writing highscore only when the score is higher than the previous high score.
			if (engine.score >= engine.highScore) {
				// protecting the UI, if score ends up being this large, this is the final value.
				if (engine.score > 999999) {
					engine.score = 999999;
				}
				bw.write("" + engine.score);
			}
			// closing buffered writer
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadScore() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(scoreFile));
			
			String s;
			
			while ((s = br.readLine()) != null) {
				engine.highScore = Integer.parseInt(s);
			}
			
			if (engine.highScore > 999999) {
				engine.highScore = 999999;
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// file was not found so we will create with writeScore.
			writeScore();
		} catch (NumberFormatException e) {
			// values in file were not solely integers defaulting the high score
			engine.highScore = 0;
			e.printStackTrace();
		} catch (IOException e) {
			// issue reading the given file defaulting high score
			engine.highScore = 0;
			e.printStackTrace();
		}
	}

}
