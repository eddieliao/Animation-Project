package rotatingSquares;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

// Simplified docs: https://processing.org/reference/
// JavaDocs: http://processing.github.io/processing-javadocs/core/

public class Main extends PApplet
{
	// This number bounces up and down.  The larger it is,
	// the more space between squares
	private float stepSize;
	
	// This number decides how many times to "split the circle".
	// Larger numbers give a smoother, rounder look.  Smaller
	// numbers give a rougher, more angular look.
	private int angleCount;
	
	// This remembers if we're "bouncing" up or down.  It flips
	// once we've bounced up or down enough.
	private boolean stepUp;

	public static void main(String[] args) 
	{
		// This string MUST match your package and class name
		PApplet.main("rotatingSquares.Main");

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
		size(640, 360);
	}
	
	// Do any one-time initialization here, like initializing fields
	public void setup()
	{
		stepSize = (float) 0.025;
		
		// Feel free to change angleCount! 
		// This number decides how many times to "split the circle".
		// Larger numbers give a smoother, rounder look.  Smaller
		// numbers give a rougher, more angular look.
		angleCount = 500;
		
		stepUp = false;
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		float angle = (float) ((2.0 * Math.PI) / angleCount);
		float centerX = g.width / 2;
		float centerY = g.height / 2;

		// Before drawing this frame, reset the window to all white
		g.background(255, 255, 255);
		
		// Don't fill in the squares
		g.fill = false;
		
		// Always call this before doing any transformations
		g.pushMatrix();
		
		// Temporarily shift everything so that the center of
		// the window is given coordinates 0,0.  Without this,
		// the rotate call below would do some weird stuff. 
		g.translate(centerX, centerY);

		// This loop draws all the squares.  The for-loop
		// header shows how stepSize affects the look.  The
		// larger stepSize is, the fewer iterations (because step
		// goes up by a lot more each time).
		for (float step = 0; step <= 200; step += stepSize)
		{
			// Rotate what we're about to draw by one more angle subdivision
			g.rotate(angle);

			// Each square drawn with a random color
			g.stroke((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
			
			// Finally, draw the square.  Since we moved 0,0 to be the
			// center of the window,
			g.rect(
					-step,		// upper-left x
					-step,		// upper-left y
					step * 2, 	// width
					step * 2 	// height
					);
		}

		// Always call this after doing any transformations, to reset
		// the translation, angles, and scale back to normal
		g.popMatrix();
		
		// The frame is complete, so update our state so that the
		// next frame uses a new stepSize
		if (stepUp)
		{
			stepSize += 0.01;
		}
		else
		{
			stepSize -= 0.01;
		}
		
		// This decides if we need to bounce.  Once stepSize
		// has gotten small enough or large enough, flip stepUp
		if (stepSize < 0.025 || stepSize > 3)
		{
			stepUp = !stepUp;
		}
	}
}
