package catAndMice;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

// Simplified docs: https://processing.org/reference/
// JavaDocs: http://processing.github.io/processing-javadocs/core/

public class Main extends PApplet{
	
	private ArrayList<AnimatedFigure> figures;
	private PImage catImg;
	private PImage mouseImg;
	

	public static void main(String[] args) 
	{
		// This string MUST match your package and class name
		PApplet.main("catAndMice.Main");

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
		size(640, 360);
	}
	
	// Do any one-time initialization here, like initializing fields
	public void setup()
	{
		figures = new ArrayList<AnimatedFigure>();
		catImg = loadImage("cartoon-cat.png");
		mouseImg = loadImage("mouse.png");
	}

	// This gets called over and over again, once for each animation frame
	public void draw() 
	{
		// White background
		g.background(255, 255, 255);
		
		// Use a pic of a cat for our pointer
		g.image(catImg, mouseX, mouseY, 50, 50);
		
		if (mousePressed)
		{
			// Add a HairballVortex to our list of figures to animate on each step
			figures.add(new HairballVortex(g, mouseX, mouseY));
			if (Math.random() < 0.1)
			{
				// 10% of the time also add a WanderingMouse to our list
				// of figures to animate on each step
				figures.add(new WanderingMouse(g, mouseX, mouseY, mouseImg));
			}
		}
		
		// Animate all the figures we've added so far
		for (AnimatedFigure figure : figures)
		{
			figure.step();
		}
	}
}
