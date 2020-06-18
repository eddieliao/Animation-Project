package animation;

import processing.core.PGraphics;
import processing.core.PImage;

public class Enemy {
	
	private PGraphics g;
	private int health;
	private int damage;
	private String side;
	private int x;
	private int currentPos;
	private int shift;
	private int attackCount;
	private int enemyMs;
	private int attackMs;
	private int endlagMs;
	
	public Enemy(PGraphics g, String side, int x)
	{
		this.g = g;
		health = 5;
		damage = 5;
		this.side = side;
		this.x = x;
		shift = 0;
		attackCount = -1;
		enemyMs = 0;
		attackMs = 0;
		endlagMs = 0;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public String getSide()
	{
		return side;
	}
	
	public int getPos()
	{
		return currentPos;
	}
	
	public int getAttackCount()
	{
		return attackCount;
	}
	
	public int getMs()
	{
		return enemyMs;
	}
	
	public int getAttackMs()
	{
		return attackMs;
	}
	
	public int getEndlagMs()
	{
		return endlagMs;
	}
	
	public void changeMs(int num)
	{
		enemyMs = num;
	}
	
	public void changeAttackMs(int num)
	{
		attackMs = num;
	}
	
	public void changeEndlagMs(int num)
	{
		endlagMs = num;
	}
	
	public void changeAttackCount(int num)
	{
		attackCount += num;
	}
	
	public void changeHealth(int num)
	{
		health += num;
	}
	
	public void changePos(int num)
	{
		shift += num;
	}
	
	public void drawSprite(PImage sprite)
	{
		sprite.resize(0, 60);
		g.image(sprite, x + shift, 120);
		currentPos = x + shift;
	}

}
