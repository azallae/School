package a4.Movable.Vehicle;

import a4.Collisions.IDestroyable;
import a4.Movable.MovableItem;


/**
 * Vehicle objects
 * @author Cody Lanier
 *
 */
public abstract class Vehicle extends MovableItem implements ISteerable, IDestroyable {
	private boolean blocked;
	private boolean destroyed;
	
	public boolean isDestroyed() {
		return destroyed ;
	}
	
	public void setDestroyed(boolean b) {
		destroyed = b;
	}	
	
	/**
	 * 
	 * @return If the object is blocked or not
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * Set an object to blocked or unblocked
	 * @param blocked
	 */
	public void setBlocked(boolean blocked) {
		if (blocked) {
			setSpeed(0);
		}
		this.blocked = blocked;
	}

	/**
	 * Increment a vehicle's speed by 1,
	 * if it is not blocked
	 * @param inc
	 */
	public void incSpeed() {
		if (!isBlocked()) {
			setSpeed(getSpeed() + 1);
		}		
		else {
			System.out.println("You are blocked and cannot speed up until you change direction!");
		}
	}
	
	/**
	 * Decrement a vehicle's speed by 1
	 * @param inc
	 */
	public void decSpeed() {
		if (getSpeed() != 0) {
			setSpeed(getSpeed() - 1);
		}
	}
	
	@Override
	/**
	 * Steer a vehicle to a new direction
	 * unblocks a blocked vehicle
	 */
	public void steerTo(double dir) {
		super.setDirection(dir);	
		setBlocked(false);
	}
	
	/**
	 * Turn a vehicle right
	 */
	public void turnRight() {
		steerTo(getDirection() + 355);
		setBlocked(false);
	}

	/**
	 * Turn a vehicle left
	 */
	public void turnLeft() {
		steerTo(getDirection() + 5);
		setBlocked(false);
	}
}
