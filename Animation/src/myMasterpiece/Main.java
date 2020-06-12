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
	private PImage leftBlock;
	
	private PImage rightStand;
	private PImage rightStandShield;
	private PImage rightDead;
	private PImage rightHit;
	private PImage rightAttack;
	private PImage rightBlock;
	
	private ArrayList<Enemy> enemies;
	
	private PImage leftStandEnemy;
	private PImage leftAttack1Enemy;
	private PImage leftAttack2Enemy;
	private PImage leftAttack3Enemy;
	private PImage leftAttack4Enemy;
	private PImage leftAttack5Enemy;
	private ArrayList<PImage> leftAttackArray; 
	
	private PImage rightStandEnemy;
	private PImage rightAttack1Enemy;
	private PImage rightAttack2Enemy;
	private PImage rightAttack3Enemy;
	private PImage rightAttack4Enemy;
	private PImage rightAttack5Enemy;
	private ArrayList<PImage> rightAttackArray;
	
	private Bars bars;
	private PImage barOutline;
	private PImage healthbar;
	private PImage manabar;
		
	private PImage background;
	
	private char prevKey;
	private int startMs;
	private boolean attack;
	private boolean block;
	private String prevDirection;
	private int endLag;
	
	private int enemyMs;
	private int enemyAttackMs;
	private int enemyEndlagMs;
	
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
		
		enemies = new ArrayList<Enemy>();
		for (int count = 0; count < 10; count ++)
		{
			double rand = Math.random();
			if (rand < 0.5)
			{
				int x = -50 + (-120 * count);
				enemies.add(new Enemy(g, "left", x));
			}
			
			else
			{
				int x = 600 + (120 * count);
				enemies.add(new Enemy(g, "right", x));
			}
		}
		
		frontStand = loadImage("images/Front Stand.png");
		frontStandShield = loadImage("images/Front Block.png");
		
		leftStand = loadImage("images/Left Stand.png");
		leftStandShield = loadImage("images/Left Shield.png");
		leftDead = loadImage("images/Left Dead.png");
		leftHit = loadImage("images/Left Hit.png");
		leftAttack = loadImage("images/Left Attack.png");
		leftBlock = loadImage("images/Left Block.png");
		
		rightStand = loadImage("images/Right Stand.png");
		rightStandShield = loadImage("images/Right Shield.png");
		rightDead = loadImage("images/Right Dead.png");
		rightHit = loadImage("images/Right Hit.png");
		rightAttack = loadImage("images/Right Attack.png");
		rightBlock = loadImage("images/Right Block.png");
		
		leftStandEnemy = loadImage("enemyImages/Left Stand.png");
		leftAttack1Enemy = loadImage("enemyImages/Left Attack 1.png");
		leftAttack2Enemy = loadImage("enemyImages/Left Attack 2.png");
		leftAttack3Enemy = loadImage("enemyImages/Left Attack 3.png");
		leftAttack4Enemy = loadImage("enemyImages/Left Attack 4.png");
		leftAttack5Enemy = loadImage("enemyImages/Left Attack 5.png");
		
		leftAttackArray = new ArrayList<PImage>();
		
		for (int count = 1; count < 6; count++)
		{
			leftAttackArray.add(loadImage("enemyImages/Left Attack " + count + ".png"));
		}
		
		rightStandEnemy = loadImage("enemyImages/Right Stand.png");
		rightAttack1Enemy = loadImage("enemyImages/Right Attack 1.png");
		rightAttack2Enemy = loadImage("enemyImages/Right Attack 2.png");
		rightAttack3Enemy = loadImage("enemyImages/Right Attack 3.png");
		rightAttack4Enemy = loadImage("enemyImages/Right Attack 4.png");
		rightAttack5Enemy = loadImage("enemyImages/Right Attack 5.png");
		
		rightAttackArray = new ArrayList<PImage>();
		
		for (int count = 1; count < 6; count++)
		{
			rightAttackArray.add(loadImage("enemyImages/Right Attack " + count + ".png"));
		}
		
		barOutline = loadImage("images/Bars.png");
		healthbar = loadImage("images/Life Bar.png");
		manabar = loadImage("images/Mana Bar.png");
		
		background = loadImage("images/Background.png");
		
		g.image(frontStand, 260, 100);
		
		startMs = millis();
		attack = false;
		block = false;
		prevDirection = "";
		endLag = millis();
		
		enemyMs = millis();
		enemyAttackMs = millis();
		enemyEndlagMs = millis();
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		frame.toFront();
		
		g.background(0 /* red */ , 128 /* green */, 0/* blue */);
		
		background.resize(0, 1500);
		g.image(background, -18, -850);
		
		// Calculate Health and Mana before every drawBars
		if (key == 'x') // Kill command
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
		
		else if (attack)
		{
			if (millis() < startMs + 175)
			{
				if (prevDirection.equals("left"))
				{
					sprite.actionSprite(leftAttack, "left", "attack");
					key = 'a';
				}
				
				else if (prevDirection.equals("right"))
				{
					sprite.actionSprite(rightAttack, "right", "attack");
					key = 'd';
				}
			}
			
			else
			{
				endLag = millis();
				attack = false;
			}
		}
		
		else if (block)
		{
			if (millis() < startMs + 250)
			{
				if (prevDirection.equals("left"))
				{
					sprite.actionSprite(leftBlock, "left", "block");
					key = 'a';
				}
				
				else if (prevDirection.equals("right"))
				{
					sprite.actionSprite(rightBlock, "right", "block");
					key = 'd';
				}
			}
			
			else
			{
				endLag = millis();
				block = false;
			}
		}
		
		else
		{
			if (key == 's')
			{
				sprite.buyShield();
			}
			
			if (key == 'j' && millis() > endLag + 175)
			{
				startMs = millis();
				if (prevKey == 'a')
				{
					sprite.actionSprite(leftAttack, "left", "attack");
					prevDirection = "left";					
				}
					
				else
				{
					sprite.actionSprite(rightAttack, "right", "attack");
					prevDirection = "right";
				}
				
				startMs = millis();
				attack = true;
					
			}
			
			else if(key == 'k' && millis() > endLag + 250)
			{
				startMs = millis();
				if (prevKey == 'a')
				{
					sprite.actionSprite(leftBlock, "left", "block");
					prevDirection = "left";					
				}
					
				else
				{
					sprite.actionSprite(rightBlock, "right", "block");
					prevDirection = "right";
				}
				
				startMs = millis();
				block = true;
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
		
		for (Enemy enemy : enemies)
		{
			if (millis() < enemy.getMs() + 600)
			{
				if (enemy.getSide().equals("left"))
				{
					enemy.drawSprite(leftStandEnemy);
				}
				
				else
				{
					enemy.drawSprite(rightStandEnemy);
				}
			}
			
			else
			{
				if (enemy.getSide().equals("left"))
				{
					if(enemy.getPos() > 195)
					{
						if (millis() < enemy.getEndlagMs() + 2000)
						{
							System.out.println("Ms: " + millis());
							enemy.changePos(0);
							enemy.changeMs(millis());
							enemy.drawSprite(leftStandEnemy);
						}
						
						else
						{
							if (enemy.getAttackCount() >= 4)
							{
								enemy.changeAttackCount(-5);
								enemy.changeAttackMs(millis() + 50);
								enemy.changeEndlagMs(millis());
								System.out.println("Endlag: " + enemy.getEndlagMs());
							}
							
							else if (millis() > enemy.getAttackMs() + 50)
							{
								enemy.changeAttackCount(1);
								if (enemy.getAttackCount() < 4)
								{
									enemy.drawSprite(leftAttackArray.get(enemy.getAttackCount()));
									enemy.changeAttackMs(millis());
								}
							}
							
							else
							{
								if (enemy.getAttackCount() > -1)
								{
									enemy.drawSprite(leftAttackArray.get(enemy.getAttackCount()));	
								}
							}
						}
					}
						
					else
					{
						enemy.changePos(30);
						enemy.changeMs(millis());
						enemy.drawSprite(leftStandEnemy);	
					}

				}
					
				else
				{
					if(enemy.getPos() < 315)
					{
						enemy.changePos(0);
						enemy.changeMs(millis());
						enemy.drawSprite(leftStandEnemy);
					}
						
					else
					{
						enemy.changePos(-30);
						enemy.changeMs(millis());
						enemy.drawSprite(leftStandEnemy);	
					}
				}
			}
		}
	}
}
