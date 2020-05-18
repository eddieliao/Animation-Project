package grassyField;

import java.awt.Color;
import processing.core.PGraphics;

public class Flower
{
	private int x;
	private int y;
	private Color c;
	private PGraphics g;
	
	public Flower(PGraphics gP, int xP)
	{
		g = gP;
		x = xP;

		// Pick a random y-coordinate on the grassy portion at the bottom
		// (between 300 and the window's height)
		y = (int) (Math.random() * (g.height - 300 + 1)) + 300;
		
		// Pick a random color
		c = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}
	
	public void shiftRight()
	{
		x++;
	}
	
	public void drawFlower()
	{
		g.stroke(80, 200, 100);
		g.strokeWeight(3);
		g.line(x, y, x, y-30);
		g.fill(c.getRGB());
		g.noStroke();
		g.ellipse(x, y-30, 15, 13);
		g.rect(x-8, y-40, 16, 13);
		g.triangle(x+4, y-40, x+8, y-40, x+6, y-45);
		g.triangle(x-2, y-40, x+2, y-40, x, y-45);
		g.triangle(x-4, y-40, x-8, y-40, x-6, y-45);
	}
}
