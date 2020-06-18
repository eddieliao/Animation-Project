package animation;

import processing.core.PGraphics;
import processing.core.PImage;

public class Bars {
	
	private PGraphics g;
	
	private int health;	
	private int maxHealth;
	private int barHealth;
	
	private int mana;
	private int maxMana;
	private int barMana;
	
	public Bars(PGraphics g, int health, int mana)
	{
		this.g = g;
		
		this.health = health;
		maxHealth = 20;
		
		this.mana = mana;
		maxMana = 100;
	}
	
	public void calculateHealth()
	{
		double percentage = (double) (health) / maxHealth;
		barHealth = (int)(167 * percentage);
	}
	
	public void changeHealth(int num)
	{
		health = health + num;
		if (health > 20)
		{
			health = 20;
		}
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void calculateMana()
	{
		double percentage = (double) (mana) / maxMana;
		barMana = (int)(135 * percentage);
	}
	
	public void changeMana(int num)
	{
		mana = mana + num;
		if (mana > 100)
		{
			mana = 100;
		}
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public void drawBars(PImage outline, PImage healthbar, PImage manabar)
	{	
		g.tint(0, 0, 0);
		g.image(healthbar, 9, 8);
		g.noTint();
		g.image(healthbar, -158 + barHealth, 8); //9 to -158
		g.tint(0, 0, 0);
		g.image(manabar, -4, 19);
		g.noTint();
		g.image(manabar, -139 + barMana, 19); //-139 to -4 
		g.image(outline, 0, 5);
	}
}
