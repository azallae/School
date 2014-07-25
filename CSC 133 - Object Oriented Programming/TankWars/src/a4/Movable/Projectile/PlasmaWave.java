package a4.Movable.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import a4.GameObject;
import a4.Collisions.ICircleBound;
import a4.Collisions.ISquareBound;
import a4.Landscape.LandscapeItem;
import a4.Movable.Vehicle.Tank;
import a4.Movable.Vehicle.Vehicle;
import a4.Sound.Sound;

public class PlasmaWave extends Projectile implements ISquareBound {
	private Point[] ctrlPoints;
	private ArrayList<Line2D.Double> lines;
	private Random rnd;
	private final int maxLevel = 5;
	private Sound hitTank;
	private int width, height;
	
	public PlasmaWave(Tank t, Sound hit) {
		setColor(Color.BLACK);
		setLifetime(2);
		setCreator(t);
		hitTank = hit;
		
		rnd = new Random();
		ctrlPoints = new Point[4];
		ctrlPoints[0] = new Point(-rnd.nextInt(t.getWidth()*2), rnd.nextInt(t.getHeight()*2));
		ctrlPoints[1] = new Point(-rnd.nextInt(t.getWidth()*2), rnd.nextInt(t.getHeight()*2));
		ctrlPoints[2] = new Point(rnd.nextInt(t.getWidth()*2), rnd.nextInt(t.getHeight()*2));
		ctrlPoints[3] = new Point(rnd.nextInt(t.getWidth()*2), rnd.nextInt(t.getHeight()*2));
		
		setWidth();
		setHeight();
		
		lines = new ArrayList<Line2D.Double>();
		drawBezierCurve(ctrlPoints, 0);
		
		setSpeed(t.getSpeed() + 7);
		super.setDirection(t.getDirection());	
		getMyTranslation().setTransform(t.getMyTranslation());
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
		if (otherObj instanceof SpikedGrenade) {
			((Projectile) otherObj).setDestroyed(true);
		}
		else if (otherObj instanceof Tank) {
			hitTank.play();
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getMyTranslation());
		g2d.transform(getMyRotation());
				
		g2d.setColor(Color.PINK);
		for (int i = 0; i <= 2; i++) {
			g2d.drawLine((int)ctrlPoints[i].x, (int)ctrlPoints[i].y, (int)ctrlPoints[i+1].x, (int)ctrlPoints[i+1].y);
		}
		
		g2d.setColor(getColor());
		for (Line2D.Double l : lines) {
			g2d.drawLine((int)l.getP1().getX(), (int)l.getP1().getY(), (int)l.getP2().getX(), (int)l.getP2().getY());
		}	
		
		g2d.setTransform(saveAT);
	}

	@Override
	public int getWidth() {		
		return width;
	}
	
	public void setWidth() {
		int min = ctrlPoints[0].x;
		int max = ctrlPoints[0].x;
		for (Point p : ctrlPoints) {
			if (p.x < min) {
				min = p.x;
			}
			if (p.x > max) {
				max = p.x;
			}
		}
		width = Math.abs(max - min);
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	public void setHeight() {
		int min = ctrlPoints[0].y;
		int max = ctrlPoints[0].y;
		for (Point p : ctrlPoints) {
			if (p.y < min) {
				min = p.y;
			}
			if (p.y > max) {
				max = p.y;
			}
		}
		height = Math.abs(max - min);
	}
	
	private void drawBezierCurve(Point2D[] pts, int level) {
		if (straightEnough(pts) || level > maxLevel) {
			lines.add(new Line2D.Double(pts[0], pts[3]));
		}
		else {			
			Point2D[] subLeft, subRight;
			subLeft = new Point2D[4];
			subRight = new Point2D[4];
			
			subdivideCurve(pts, subLeft, subRight);		
			drawBezierCurve(subLeft, level+1);
			drawBezierCurve(subRight, level+1);
		}
	}
	
	private void subdivideCurve(Point2D[] q, Point2D[] r, Point2D[] s) {
		r[0] = q[0];
		r[1] = new Point2D.Double((q[0].getX()+q[1].getX())/2, (q[0].getY()+q[1].getY())/2);
		r[2] = new Point2D.Double((((r[1].getX())/2) + (q[1].getX()+q[2].getX())/4), (((r[1].getY())/2) + (q[1].getY()+q[2].getY())/4));
		s[3] = q[3];
		s[2] = new Point2D.Double((q[2].getX()+q[3].getX())/2, (q[2].getY()+q[3].getY())/2);
		s[1] = new Point2D.Double(((q[1].getX()+q[2].getX())/4) + ((s[2].getX())/2), ((q[1].getY()+q[2].getY())/4) + ((s[2].getY())/2));
		r[3] = new Point2D.Double((r[2].getX()+s[1].getX())/2, (r[2].getY()+s[1].getY())/2);
		s[0] = r[3];
	}

	private boolean straightEnough(Point2D[] pts) {
		double epsilon = 0.001;
		double d1 = lengthOf(pts[0], pts[1]) + lengthOf(pts[1], pts[2]) + lengthOf(pts[2], pts[3]);
		double d2 = lengthOf(pts[0], pts[3]);
		
		if (Math.abs(d1-d2) < epsilon) {
			return true;
		} else {
			return false;
		}
	}
	
	private double lengthOf(Point2D p1, Point2D p2) {
		double deltaX = Math.abs(p1.getX() - p2.getX());
		double deltaY = Math.abs(p1.getY() - p2.getY());
		double cSq = deltaX*deltaX + deltaY*deltaY;
		return Math.sqrt(cSq);
	}

}
