package a4;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import a4.Graphics.IDrawable;

/**
 * Game Objects
 * @author Cody Lanier
 *
 */
public abstract class GameObject implements IDrawable {
	private Color myColor;
	private AffineTransform myRotation, myTranslation, myScale, myTransform;
		
	public GameObject() {
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		myTransform = new AffineTransform();
	}
	
	/**
	 * 
	 * @return An object's color
	 */
	public Color getColor() {
		return myColor;
	}
	
	/**
	 * Sets an object's color
	 * @param myColor
	 */
	public void setColor(Color myColor) {
		this.myColor = myColor;
	}
	
	public void rotate(double degrees) {
		myRotation.rotate(Math.toRadians(degrees));
	}
	
	public void scale(double sx, double sy) {
		myScale.scale(sx, sy);
	}
	
	public void translate(double dx, double dy) {
		myTranslation.translate(dx, dy);
	}
	
	public AffineTransform getMyTranslation() {
		return myTranslation;
	}
	
	public AffineTransform getMyScale() {
		return myScale;
	}
	
	public AffineTransform getMyRotation() {
		return myRotation;
	}

	public AffineTransform getMyTransform() {
		myTransform.setToIdentity();
		myTransform.setTransform((AffineTransform) myTranslation.clone());
		myTransform.concatenate(getMyRotation());
		myTransform.concatenate(getMyScale());
		
		return myTransform;
	}

	public void setMyTransform(AffineTransform myTransform) {
		this.myTransform = myTransform;
	}
}
