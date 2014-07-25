package a4.Collisions;

import a4.GameObject;

public interface ICollider {
	public boolean collidesWith(GameObject otherObj);
	public void handleCollision(GameObject otherObj);
}
