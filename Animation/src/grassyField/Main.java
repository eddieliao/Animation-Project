package grassyField;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

// Simplified docs: https://processing.org/reference/
// JavaDocs: http://processing.github.io/processing-javadocs/core/


public class Main extends PApplet
{
	private ArrayList<Flower> flowers;
	
	public static void main(String[] args)
	{
		// This string MUST match your package and class name
		PApplet.main("grassyField.Main");

		// DO NOT TOUCH THIS MAIN METHOD
		//
		// Instead, the following methods are called, in this order:
		//
		// settings -- this determines the size of the window
		//
		// setup -- any initialization should be put here
		//			(in this case, there is none)
		//
		// draw (repeatedly) -- this is called over and over again
		//						in a loop.  Each call is a like a single
		//						frame in the animation.
		//						(in this case, noLoop forces it to be called only once)
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
		flowers = new ArrayList<Flower>();
		
		for (int i = 0; i < 15; i++) 
		{
			int randomX = (int) (Math.random() * width);
			flowers.add(new Flower(g, randomX));
		}
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		g.colorMode(HSB);

		// Draw blue sky
		g.background(134, 74, 255);
		
		// Draw grassy ground
		g.fill(81, 97, 219);
		g.rect(0, 300, width, 100);

		// Shift all the flowers to the right
		for (Flower flower : flowers)
		{
			flower.shiftRight();
		}
		
		// 2% of the time, add a new flower on the left
		if (Math.random() < 0.02)
		{
			flowers.add(new Flower(g, 0));
		}

		// Draw all the flowers
		for (Flower flower : flowers)
		{
			flower.drawFlower();
		}
	}
}
