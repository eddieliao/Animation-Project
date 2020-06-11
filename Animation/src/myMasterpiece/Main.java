package myMasterpiece;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.charset.Charset;

// Simplified docs: https://processing.org/reference/
// JavaDocs: http://processing.github.io/processing-javadocs/core/

public class Main extends PApplet
{
	// Declare any private fields you need to help keep track of
	// stuff while your masterpiece animates itself
	
	private Sprite sprite;
	
	private PImage frontStand;
	private PImage frontStandShield;
	
	private PImage leftStand;
	private PImage leftStandShield;
	private PImage leftDead;
	private PImage leftHit;
	private PImage leftAttack;
	
	private PImage rightStand;
	private PImage rightStandShield;
	private PImage rightDead;
	private PImage rightHit;
	private PImage rightAttack;
	
	private Bars bars;
	private PImage barOutline;
	private PImage healthbar;
	private PImage manabar;
	
	private char prevKey;
	private int startMs;
	private boolean attack;
	private String prevDirection;
	
	public static void main(String[] args) 
	{
		// This string MUST match your package and class name
		PApplet.main("myMasterpiece.Main");

		// DO NOT ADD ANYTHING ELSE TO THIS MAIN METHOD
		//
		// Instead, the following methods are called, in this order:
		//
		// settings -- this determines the size of the window
		//
		// setup -- any initialization should be put here
		//
		// draw (repeatedly) -- this is called over and over again
		//						in a loop.  Each call is a like a single
		//						frame in the animation.
	}

	// This method determines the size of the window and does nothing else 
	public void settings() 
	{
		// These numbers decide how big the window is.
		// You can change these if you don't like the size!
		size(600, 300);
	}
	
	// Do any one-time initialization here, like initializing fields	
	public void setup()
	{
		sprite = new Sprite(g);
		bars = new Bars(g, 20, 0);
		
		frontStand = loadImage("images/Front Stand.png");
		frontStandShield = loadImage("images/Front Block.png");
		
		leftStand = loadImage("images/Left Stand.png");
		leftStandShield = loadImage("images/Left Shield.png");
		leftDead = loadImage("images/Left Dead.png");
		leftHit = loadImage("images/Left Hit.png");
		leftAttack = loadImage("images/Left Attack.png");
		
		rightStand = loadImage("images/Right Stand.png");
		rightStandShield = loadImage("images/Right Shield.png");
		rightDead = loadImage("images/Right Dead.png");
		rightHit = loadImage("images/Right Hit.png");
		rightAttack = loadImage("images/Right Attack.png");
		
		barOutline = loadImage("images/Bars.png");
		healthbar = loadImage("images/Life Bar.png");
		manabar = loadImage("images/Mana Bar.png");
		
		g.image(frontStand, 260, 100);
		
		startMs = millis();
		attack = false;
		prevDirection = "";
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		frame.toFront();
		
		g.background(0 /* red */ , 128 /* green */, 0/* blue */);
		
		// Calculate Health and Mana before every drawBars
		if (key == 'k') // Kill command
		{
			bars.changeHealth(-20);
		}
		
		bars.calculateHealth();
		bars.calculateMana();
		bars.drawBars(barOutline, healthbar, manabar);

		if (bars.getHealth() <= 0)
		{
			if (prevKey == 'a')
			{	
				sprite.drawSprite(leftDead);
			}
			
			else
			{
				sprite.drawSprite(rightDead);
			}
			
			noLoop();
		}
		
		if (attack)
		{
			if (millis() < startMs + 175)
			{
				if (prevDirection.equals("left"))
				{
					sprite.attackSprite(leftAttack, "left");
					key = 'a';
				}
				
				else if (prevDirection.equals("right"))
				{
					sprite.attackSprite(rightAttack, "right");
					key = 'd';
				}
			}
			
			else
			{
				attack = false;
			}
		}
		
		else
		{
			if (key == 's')
			{
				sprite.buyShield();
			}
			
			else if (key == 'j')
			{
				startMs = millis();
				if (prevKey == 'a')
				{
					sprite.attackSprite(leftAttack, "left");
					prevDirection = "left";					
				}
					
				else
				{
					sprite.attackSprite(rightAttack, "right");
					prevDirection = "right";
				}
				
				startMs = millis();
				attack = true;
					
			}
			
			else if (key == 'a' || keyCode == LEFT)
			{	
				prevKey = 'a';
				if (sprite.getShield())
				{
					sprite.drawSprite(leftStandShield);
				}
				
				else
				{
					sprite.drawSprite(leftStand);
				}

			}
			
			else if (key == 'd' || keyCode == RIGHT)
			{
				prevKey = 'd';
				if (sprite.getShield())
				{
					sprite.drawSprite(rightStandShield);
				}
				
				else
				{
					sprite.drawSprite(rightStand);
				}
			}
			
			else
			{
				if (sprite.getShield())
				{
					sprite.drawSprite(frontStandShield);
				}
				
				else
				{
					sprite.drawSprite(frontStand);
				}
			}
		}
	}
}
