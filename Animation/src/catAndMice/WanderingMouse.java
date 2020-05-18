package catAndMice;

import processing.core.PGraphics;
import processing.core.PImage;

// A WanderingMouse animates itself by rotating and stepping forward
// a picture of a mouse
public class WanderingMouse implements AnimatedFigure 
{
	private static final double STEP_ROTATE_AMOUNT = Math.PI / 10;
	private static final double STEP_WALK_AMOUNT = 5.0;
	private PGraphics g;

	// What direction is the figure currently facing?
	private float angle;
	
	// Where is the figure right now?
	private int x;
	private int y;
	
	// Remembers the bitmap picture of the mouse
	private PImage img;
	
	// How big is the figure?
	private int mouseLength;
	
	// Based on the size of the figure, these figure out how
	// far the center is from the upper-left corner
	private int mouseOffsetX;
	private int mouseOffsetY;
	
	public WanderingMouse(PGraphics gP, int xP, int yP, PImage imgP)
	{
		g = gP;
		
		// Mouse starting location & image comes from our caller
		x = xP;
		y = yP;
		img = imgP;
		
		// The angle and size are picked randomly
		angle = (float) (Math.random() * 2 * Math.PI);
		mouseLength = (int) (Math.random() * 76 + 25);
		
		// Now that we know the size of the mouse, calculate
		// how far the center is from the upper-left corner
		mouseOffsetX = (-mouseLength) * 4 / 5;
		mouseOffsetY = (-mouseLength) / 2;
	}
	
	// This gets called to animate our WanderingMouse a single frame
	@Override
	public void step() 
	{
		if (Math.random() <= 0.75)
		{
			// 75% probability to move forward
			x += Math.cos(-angle - Math.PI / 2) * STEP_WALK_AMOUNT;
			y -= Math.sin(-angle - Math.PI / 2) * STEP_WALK_AMOUNT;
		}
		else
		{
			// 25% probability to rotate
			angle += (Math.random() > 0.5 ? STEP_ROTATE_AMOUNT : -STEP_ROTATE_AMOUNT);
		}
		
		// Always call this before doing any transformations
		g.pushMatrix();

		// Temporarily shift everything so that the center of
		// the WanderingMouse is given coordinates 0,0.  Without this,
		// the rotate call below would do some weird stuff. 
		g.translate(x, y);

		// Rotate what we're about to draw by the mouse's angle
		g.rotate(angle);
		
		// Draw the mouse
		g.image(img, mouseOffsetX, mouseOffsetY, mouseLength, mouseLength);
		
		// Always call this after doing any transformations, to reset
		// the translation, angles, and scale back to normal
		g.popMatrix();
	}
}
