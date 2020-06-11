package myMasterpiece;

import processing.core.PGraphics;
import processing.core.PImage;

public class Bars {
	
	private PGraphics g;
	private int health;
	private int mana;
	
	public Bars(PGraphics g, int health, int mana)
	{
		this.g = g;
		this.health = health;
		this.mana = mana;
	}
	
	public void drawBars(PImage outline, PImage healthbar, PImage manabar)
	{	
		g.tint(0, 0, 0);
		g.image(healthbar, 9, 8);
		g.noTint();
		g.image(healthbar, 9, 8); //9 to -158
		g.tint(0, 0, 0);
		g.image(manabar, -4, 19);
		g.noTint();
		g.image(manabar, -139, 19); //-139 to -4 
		g.image(outline, 0, 5);
	}
}
