package a4.Movable.Vehicle;

/**
 * For objects that can steer (change direction)
 * @author Cody Lanier
 *
 */
public interface ISteerable {
	public void steerTo(double newDirection);
	public void turnLeft();
	public void turnRight();
}
