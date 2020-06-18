package animation;

import java.awt.event.KeyEvent;

import processing.core.PFont;
import processing.core.PGraphics;

public class Counter {
	
	private PGraphics g;
	private int score;
	private PFont font;
	
	public Counter(PGraphics g, PFont font)
	{
		this.g = g;
		score = 0;
		this.font = font;
	}
	
	public void changeScore(int num)
	{
		score += num;
	}

	public void displayScore()
	{
		g.textFont(font);
		g.fill(255, 255, 0);
		g.text("Score:" + score, 430, 30);
	}
}
