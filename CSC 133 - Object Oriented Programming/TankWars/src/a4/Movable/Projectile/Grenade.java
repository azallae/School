package a4.Movable.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import a4.GameObject;
import a4.Collisions.ICircleBound;
import a4.Movable.Vehicle.Tank;
import a4.Sound.Sound;

public class Grenade extends Projectile implements ICircleBound {
	private int diameter;		
		
	public Grenade(Tank t, Sound explosion, Sound hit) {
		setLifetime(5);
		diameter = 4;
		setColor(Color.MAGENTA);
		setCreator(t);
		super.setDirection(t.getDirection());	
		super.setSpeed(t.getSpeed() + 2);
					
		getMyTranslation().setTransform(t.getMyTranslation());	
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
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getMyTranslation());
		g2d.transform(getMyRotation());
		g2d.transform(getMyScale());
		
		int radius = getDiameter()/2;
		Point upperLeft = new Point(-radius, -radius);
		g2d.setColor(getColor());
		g2d.fillOval(upperLeft.x, upperLeft.y, getDiameter(), getDiameter());
		
		g2d.setTransform(saveAT);
	}
	
	@Override
	public void update(int elapsedTime) {
		rotate(4);
		super.update(elapsedTime);
	}

	@Override
	public int getDiameter() {
		return diameter;
	}

}
