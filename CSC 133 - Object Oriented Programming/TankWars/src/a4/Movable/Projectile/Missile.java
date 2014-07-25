package a4.Movable.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.Landscape.LandscapeItem;
import a4.Movable.Vehicle.Tank;
import a4.Movable.Vehicle.Vehicle;
import a4.Sound.Sound;
import a4.GameObject;
import a4.Collisions.ICircleBound;
import a4.Collisions.ISquareBound;

/**
 * Missiles
 * @author Cody Lanier
 *
 */
public class Missile extends Projectile implements ISquareBound {
	private int width = 4;
	private int height = 8;
	
	private Sound blowUp;
	private Sound hitTank;
	
	/**
	 * Missile constructor
	 */
	public Missile(Tank t, Sound explosion, Sound hit) {
		setLifetime(10);		
		setColor(Color.BLACK);
		setSpeed(t.getSpeed() + 5);
		super.setDirection(t.getDirection());		
		blowUp = explosion;
		hitTank = hit;
		setCreator(t);
		
		getMyTranslation().setTransform(t.getMyTranslation());		
	}
	
	/**
	 * Missile's cannot change direction, empty method
	 */
	@Override
	public void setDirection(double dir) {
		
	}	

	@Override
	public void draw(Graphics2D g2d) {	
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getMyTranslation());
		g2d.transform(super.getMyRotation());
				
		int centerX = 0;
		int centerY = 0;
		g2d.setColor(getColor());
		int[] xPoints = {centerX-2, centerX+2, centerX+2, centerX, centerX-2};
		int[] yPoints = {centerY-3, centerY-3, centerY+3, centerY+5, centerY+3};
		g2d.fillPolygon(xPoints , yPoints , 5);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean collidesWith(GameObject otherObj) {
		if (otherObj instanceof Vehicle) {
			Vehicle v = (Vehicle)otherObj;
			if (getCreator() == v) {
				return false;
			}
		}	
		
		double centerX = getMyTranslation().getTranslateX();
		double centerY = getMyTranslation().getTranslateY();
		double otherX = otherObj.getMyTranslation().getTranslateX();
		double otherY = otherObj.getMyTranslation().getTranslateY();		
		
		int L1 = (int)centerX - getWidth()/2;
		int R1 = (int)centerX + getWidth()/2;
		int T1 = (int)centerY - getHeight()/2;
		int B1 = (int)centerY + getHeight()/2;
		
		if (otherObj instanceof ISquareBound && !(otherObj instanceof LandscapeItem)) {
			ISquareBound sqObj = (ISquareBound)otherObj;
			int L2 = (int)otherX - sqObj.getWidth()/2;
			int R2 = (int)otherX + sqObj.getWidth()/2;
			int T2 = (int)otherY - sqObj.getHeight()/2;
			int B2 = (int)otherY + sqObj.getHeight()/2;
			
			if (R1 >= L2 && L1 <= R2) {
				if (T1 <= B2 && B1 >= T2) {
					return true;
				}
				return false;
			}
		}
		else if (otherObj instanceof ICircleBound && !(otherObj instanceof LandscapeItem)) {
			ICircleBound cirObj = (ICircleBound)otherObj;
			int circleX = (int)otherX;
			int circleY = (int)otherY;
			int radius = cirObj.getDiameter()/2;
						
			int distanceX = Math.abs((int)centerX - circleX);
			int distanceY = Math.abs((int)centerY - circleY);
			int cornerDistance = ((getWidth()/2)^2 + (getHeight()/2)^2);
			
			if (distanceX > (getWidth()/2 + radius) || distanceY > (getHeight()/2 + radius)) {
				return false;
			}
			else if (distanceX <= getWidth()/2 || distanceY <= getHeight()/2) {
				return true;
			}
			return cornerDistance <= (radius^2);
		}
		return false;
	}

	@Override
	public void handleCollision(GameObject otherObj) {
		setDestroyed(true);	
		
		if (otherObj instanceof Projectile) {
			blowUp.play();
			if (otherObj instanceof SpikedGrenade) {
				((Projectile) otherObj).setDestroyed(true);
			}
		}
		else if (otherObj instanceof Tank) {
			hitTank.play();
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	public void rotate(double degrees) {
		
	}
	
	public AffineTransform getMyRotation() {
		return null;
	}
}
