package myMasterpiece;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import java.awt.Color;
import java.awt.event.KeyEvent;

// Simplified docs: https://processing.org/reference/
// JavaDocs: http://processing.github.io/processing-javadocs/core/

public class Main extends PApplet
{
	// Declare any private fields you need to help keep track of
	// stuff while your masterpiece animates itself
	
	private Sprite sprite;
	private PImage img;
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
		img = loadImage("images/Left Stand.png");
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		// Typically, you'll do something like this to clear the
		// screen before drawing your frame.  Feel free to change
		// the color.
		g.background(0 /* red */ , 128 /* green */, 0/* blue */);

		// Then call methods on g to draw stuff.  This is just an example,
		// feel free to remove.  See the links at the top of this file
		// for documentation on the drawing methods you can call on g
		g.image(img, 260, 100);
	}
}
