package catAndMice;

// An "AnimatedFigure" is just a thing that knows how to
// animate itself on each step.  It can do 
// whatever it wants on each step
public interface AnimatedFigure
{
	// Inform the figure that it's time to animate itself
	// for a single step
	public void step();
}
