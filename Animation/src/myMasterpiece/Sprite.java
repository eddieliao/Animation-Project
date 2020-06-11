package myMasterpiece;

import processing.core.PGraphics;
import processing.core.PImage;

public class Sprite{
	
	private PGraphics g;
	private boolean shield;
	
	public Sprite(PGraphics g)
	{
		this.g = g;
		shield = false;
	}
	
	public void buyShield()
	{
		shield = true;
	}
	
	public boolean getShield()
	{
		return shield;
	}
	
	public void drawSprite(PImage sprite)
	{
		g.image(sprite, 260, 100);
	}
}
