package catAndMice;

import java.awt.Color;

import processing.core.PGraphics;

// A HairballVortex animates itself by drawing bigger and bigger
// circles until it's big enough to
// just go dormant and do nothing afterward
public class HairballVortex implements AnimatedFigure
{
	private PGraphics g;
	
	// The center coordinates of the circles
	private int centerX;
	private int centerY;
	
	// What animation step are we on?
	private int step;
	
	// What color did we choose for this vortex?
	private Color color;
	
	public HairballVortex(PGraphics gP, int centerXP, int centerYP)
	{
		// The caller of our constructor gives us a PGraphics
		// to draw with, and the center of the circles to draw
		g = gP;
		centerX = centerXP;
		centerY = centerYP;
		
		// Start at step 0
		step = 0;
		
		// Pick a random color for all the circles we'll draw
		color = new Color(getRand(), getRand(), getRand());
	}
	
	private int getRand()
	{
		return (int) (Math.random() * 256);
	}
	
	// This gets called on each step to tell us to animate
	// the vortex a single step
	public void step()
	{
		// Keep track of how many times we're called
		step++;
		
		// After 50 steps, go dormant and do nothing
		if (step > 50)
		{
			return;
		}
		
		// Draw concentric circles that get bigger and bigger,
		// up to our step count.
		g.stroke(color.getRGB());
		g.noFill();
		for (int i=0; i < step; i += 2)
		{
			g.ellipse(centerX, centerY, i, i);
		}
	}
}
