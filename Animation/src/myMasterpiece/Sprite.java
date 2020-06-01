package myMasterpiece;

import java.awt.event.KeyEvent;

import processing.core.PGraphics;

public class Sprite {
	
	private PGraphics g;
	private KeyEvent key;
	private int x;
	private int y;
	
	public Sprite(PGraphics g, KeyEvent key, int x, int y)
	{
		this.g = g;
		this.key = key;
		this.x = x;
		this.y = y;
	}
	
	public void processMove()
	{
		if(key.getKeyCode() == KeyEvent.VK_W)
		{
			y++;
		}
		
		if(key.getKeyCode() == KeyEvent.VK_A)
		{
			x--;
		}
		
		if(key.getKeyCode() == KeyEvent.VK_S)
		{
			y--;
		}
		
		if(key.getKeyCode() == KeyEvent.VK_D)
		{
			x++;
		}
	}
	
	public void drawSprite()
	{
		g.image(img, a, b);
	}
}
