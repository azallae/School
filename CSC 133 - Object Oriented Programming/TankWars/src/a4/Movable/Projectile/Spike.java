package a4.Movable.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import a4.GameObject;
import a4.Collisions.ICircleBound;
import a4.Movable.Vehicle.Tank;

public class Spike extends Projectile implements ICircleBound {
	private Point top, bottomLeft, bottomRight;
	
	public Spike(Tank t) {
		top = new Point(0, 2);
		bottomLeft = new Point(-1, -1);
		bottomRight = new Point(1, -1);
		setColor(Color.MAGENTA);
		setCreator(t);
	}

	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getMyRotation());
		g2d.transform(getMyScale());
		g2d.transform(getMyTranslation());
		
		g2d.setColor(getColor());
		int [] xPts = new int [] {top.x, bottomLeft.x, bottomRight.x};
		int [] yPts = new int [] {top.y, bottomLeft.y, bottomRight.y};
		g2d.fillPolygon (xPts, yPts, 3); 
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean collidesWith(GameObject otherObj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(GameObject otherObj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDiameter() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
