package a4.Movable.Projectile;

import a4.Collisions.IDestroyable;
import a4.Movable.MovableItem;
import a4.Movable.Vehicle.Vehicle;


/**
 * Projectile objects
 * @author Cody Lanier
 *
 */
public abstract class Projectile extends MovableItem implements IDestroyable {
	private boolean destroyed;
	private int lifetime;
	private Vehicle creator;
		
	public boolean isDestroyed() {
		return destroyed ;
	}
	
	public void setDestroyed(boolean b) {
		destroyed = b;
	}	
	
	public void setLifetime(int i) {
		lifetime = i*50;
	}

	/**
	 * 
	 * @return The remaining lifetime of a missile
	 */
	public int getLifetime() {
		return lifetime;
	}
	
	/**
	 * Decrement the lifetime of a missile
	 */
	private void decLifetime() {
		lifetime--;
		if (lifetime < 1) {
			setDestroyed(true);
		}
	}
	
	@Override
	public void update(int elapsedTime) {
		move(elapsedTime);
		decLifetime();
	}

	public Vehicle getCreator() {
		return creator;
	}

	public void setCreator(Vehicle creator) {
		this.creator = creator;
	}	
}
