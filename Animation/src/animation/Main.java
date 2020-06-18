package animation;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
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
	
	private PImage heal;
	
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
	private ArrayList<PImage> leftAttackArray; 
	
	private PImage rightStandEnemy;
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
	private boolean healCheck;
	private boolean leftHitCheck;
	private boolean rightHitCheck;
	private String prevDirection;
	private int endLag;
	
	private int enemyMs;
	private int enemyAttackMs;
	private int enemyEndlagMs;
	
	private PFont font;
	private Counter counter;
	
	public static void main(String[] args) 
	{
		// This string MUST match your package and class name
		PApplet.main("animation.Main");

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
		
//		String[] fontList = PFont.list();
//		printArray(fontList);
		font = createFont("fonts/Teletactile-3zavL.ttf", 20);
		counter = new Counter(g, font);
		
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
		
		heal = loadImage("images/Heal.png");
		
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
		
		leftAttackArray = new ArrayList<PImage>();
		
		for (int count = 1; count < 6; count++)
		{
			leftAttackArray.add(loadImage("enemyImages/Left Attack " + count + ".png"));
		}
		
		rightStandEnemy = loadImage("enemyImages/Right Stand.png");
		
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
		healCheck = false;
		leftHitCheck = false;
		rightHitCheck = false;
		prevDirection = "";
		endLag = millis();
		
		enemyMs = millis();
		enemyAttackMs = millis();
		enemyEndlagMs = millis();
		
		System.out.println("Controls: J - Attack");
		System.out.println("          K - Block (when shield is equipped)");
		System.out.println("          L - Heal (when mana bar is full)");
		System.out.println("          S - Equip Shield");
		System.out.println("          <- or A - Left");
		System.out.println("          -> or D - Right");
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		frame.toFront();
		
		g.background(0 /* red */ , 128 /* green */, 0/* blue */);
		
		background.resize(0, 1500);
		g.image(background, -18, -850);
		
		counter.displayScore();
		
		// Calculate Health and Mana before every drawBars
		if (key == 'x') // Kill command
		{
			bars.changeHealth(-20);
		}
		
		if (key == 'z') // Fill mana command
		{
			bars.changeMana(100);
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
					
					for (int count = 0; count < 10; count ++)
					{
						if (enemies.get(count).getSide().equals("left") && enemies.get(count).getPos() > 195)
						{
							enemies.remove(count);
							double rand = Math.random();
							if (rand < 0.5)
							{
								int x = -50;
								enemies.add(new Enemy(g, "left", x));
							}
							
							else
							{
								int x = 600;
								enemies.add(new Enemy(g, "right", x));
							}
							
							if (bars.getMana() < 100)
							{
								bars.changeMana(3);
							}
							
							counter.changeScore(5);
						}
					}
				}
				
				else if (prevDirection.equals("right"))
				{
					sprite.actionSprite(rightAttack, "right", "attack");
					key = 'd';
					
					for (int count = 0; count < 10; count ++)
					{
						if (enemies.get(count).getSide().equals("right") && enemies.get(count).getPos() < 315)
						{
							enemies.remove(count);
							double rand = Math.random();
							if (rand < 0.5)
							{
								int x = -50;
								enemies.add(new Enemy(g, "left", x));
							}
							
							else
							{
								int x = 600;
								enemies.add(new Enemy(g, "right", x));
							}
							
							if (bars.getMana() < 100)
							{
								bars.changeMana(3);
							}
							
							counter.changeScore(5);
						}
					}
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
		
		else if (healCheck)
		{
			if (millis() < startMs + 150)
			{
				sprite.healSprite(heal);
			}
			
			else
			{
				healCheck = false;
			}
		}
		
		else if (leftHitCheck)
		{
			if (millis() < startMs + 200)
			{
				sprite.actionSprite(leftHit, "left", "hit");
			}
			
			else
			{
				leftHitCheck = false;
			}
		}
		
		else if (rightHitCheck)
		{
			if (millis() < startMs + 200)
			{
				sprite.actionSprite(rightHit, "right", "hit");
			}
			
			else
			{
				rightHitCheck = false;
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
			
			else if(key == 'k' && millis() > endLag + 250 && sprite.getShield())
			{
				startMs = millis();
				if (prevKey == 'a')
				{
					sprite.actionSprite(leftBlock, "left", "block");
					prevDirection = "left";	
					sprite.changeLeftBlock(true);
				}
					
				else
				{
					sprite.actionSprite(rightBlock, "right", "block");
					prevDirection = "right";
					sprite.changeRightBlock(true);
				}
				
				startMs = millis();
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
					sprite.changeLeftBlock(false);
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
					sprite.changeRightBlock(false);
				}
			}
			
			else if (key == 'l' && bars.getMana() == 100)
			{
				startMs = millis();
				key = prevKey;
				sprite.healSprite(heal);
				bars.changeHealth(5);
				bars.changeMana(-100);
				healCheck = true;
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
						if (millis() < enemy.getEndlagMs() + 2500)
						{
//							System.out.println("Ms: " + millis());
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
								
								if (sprite.getLeftBlock() == false || sprite.getShield() == false)
								{
									bars.changeHealth(-1);
									sprite.drawSprite(leftHit);
									leftHitCheck = true;
									startMs = millis();
									counter.changeScore(-1);
								}
//								System.out.println("Endlag: " + enemy.getEndlagMs());
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
					if (enemy.getPos() < 315)
					{
						if (millis() < enemy.getEndlagMs() + 2500)
						{
//							System.out.println("Ms: " + millis());
							enemy.changePos(0);
							enemy.changeMs(millis());
							enemy.drawSprite(rightStandEnemy);
						}
						
						else
						{
							if (enemy.getAttackCount() >= 4)
							{
								enemy.changeAttackCount(-5);
								enemy.changeAttackMs(millis() + 50);
								enemy.changeEndlagMs(millis());
								if (sprite.getRightBlock() == false || sprite.getShield() == false)
								{
									bars.changeHealth(-1);
									sprite.drawSprite(rightHit);
									rightHitCheck = true;
									startMs = millis();
									counter.changeScore(-1);
								}
//								System.out.println("Endlag: " + enemy.getEndlagMs());
							}
							
							else if (millis() > enemy.getAttackMs() + 50)
							{
								enemy.changeAttackCount(1);
								if (enemy.getAttackCount() < 4)
								{
									enemy.drawSprite(rightAttackArray.get(enemy.getAttackCount()));
									enemy.changeAttackMs(millis());
								}
							}
							
							else
							{
								if (enemy.getAttackCount() > -1)
								{
									enemy.drawSprite(rightAttackArray.get(enemy.getAttackCount()));	
								}
							}
						}
					}
					
					else
					{
						enemy.changePos(-30);
						enemy.changeMs(millis());
						enemy.drawSprite(rightStandEnemy);	
					}
				}
			}
		}
	}
}
