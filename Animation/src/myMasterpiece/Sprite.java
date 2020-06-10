package myMasterpiece;

import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Sprite extends PApplet{
	
	private PGraphics g;
	private KeyEvent key;
	private PImage img;
	
	public Sprite(PGraphics g)
	{
		this.g = g;
		this.key = key;
	}
	
	public void setup()
	{
		img = loadImage("/Animation/src/images/Left Stand.png");
	}
	
	public void processMove()
	{		
		if(key.getKeyCode() == KeyEvent.VK_A)
		{
			img = loadImage("images/Left Stand.png");
		}

		if(key.getKeyCode() == KeyEvent.VK_D)
		{
			img = loadImage("Right Stand.png");
		}
	}
	
	public void drawSprite()
	{
		g.image(img, 0, 0);
	}
}
