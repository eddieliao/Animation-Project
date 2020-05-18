package lineTunnel;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

// Simplified docs: https://processing.org/reference/
// JavaDocs: http://processing.github.io/processing-javadocs/core/

public class Main extends PApplet
{
	// This number bounces up and down.  The larger it is,
	// the more space between lines
	private float stepSize;
	
	// This remembers if we're "bouncing" up or down.  It flips
	// once we've bounced up or down enough.
	private boolean stepUp;
	

	public static void main(String[] args) 
	{
		// This string MUST match your package and class name
		PApplet.main("lineTunnel.Main");

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
		stepSize = g.width / 2;
		stepUp = false;
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		// Before drawing this frame, reset the window to all white
		g.background(255, 255, 255);

		int centerY = g.height / 2;
		int centerX = g.width / 2;
		
		// Everything drawn will be black (R,G,B: 0,0,0)
		g.stroke(0, 0, 0);
		
		// These loops draw all the lines.  The for-loop
		// headers show how stepSize affects the look.  The
		// larger stepSize is, the fewer iterations (because step
		// goes up by a lot more each time).
		
		// This loop draws the lines that stretch from the middle
		// of the window to the top and bottom
		for (int x = 0; x < g.width; x += stepSize)
		{
			g.line(centerX, centerY, x, 0);
			g.line(centerX, centerY, x, g.height - 1);
		}
		
		// This loop draws the lines that stretch from the middle
		// of the window to the left and right sides
		for (int y=0; y < g.height; y += stepSize)
		{
			g.line(centerX, centerY, 0, y);
			g.line(centerX, centerY, g.width - 1, y);
		}
		
		// The frame is complete, so update our state so that the
		// next frame uses a new stepSize
		if (stepUp)
		{
			stepSize += 0.5;
		}
		else
		{
			stepSize -= 0.5;
		}
		
		// This decides if we need to bounce.  Once stepSize
		// has gotten small enough or large enough, flip stepUp
		if (stepSize < 2 || stepSize > g.width / 2)
		{
			stepUp = !stepUp;
		}
	}
}
