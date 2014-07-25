package a4.Movable;

import a4.Game;
import a4.GameObject;
import a4.Collisions.ICollider;

/**
 * Objects that can move
 * @author Cody Lanier
 *
 */
public abstract class MovableItem extends GameObject implements ICollider {
	private int currSpeed;
	private double currDirection;
	
	/**
	 * 
	 * @return An object's speed
	 */
	public int getSpeed() {
		return currSpeed;
	}
	
	/**
	 * Sets an object's speed
	 * @param speed
	 */
	public void setSpeed(int speed) {
		if (speed >= 0) {
			this.currSpeed = speed;
		}
		else {
			this.currSpeed = 0;
		}
	}

	/**
	 * 
	 * @return An object's current direction
	 */
	public double getDirection() {
		return currDirection;
	}
	
	/**
	 * Set an object's direction
	 * @param dir
	 */
	public void setDirection(double dir) {
		dir = dir%360;
		this.currDirection = dir;
		super.getMyRotation().setToIdentity();
		super.rotate(dir);
	}
	
	/**
	 * Moves an object (update its location)
	 * based on its speed and direction
	 */
	public void move(int elapsedTime) {		
		int divideBy = Game.getTickSpeed()/Game.getPreferedUpdateSpeed();
		
		double angle = (double)Math.toRadians(90 + getDirection());		
		double deltaX = (double)Math.cos(angle) * getSpeed();
		double deltaY = (double)Math.sin(angle) * getSpeed();
		translate(deltaX/divideBy, deltaY/divideBy);		
	}
	
	public abstract void update(int elapsedTime);
}
