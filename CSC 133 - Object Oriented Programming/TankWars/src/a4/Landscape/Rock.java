package a4.Landscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import a4.Game;
import a4.Collisions.ISquareBound;

/**
 * Rocks
 * @author Cody Lanier
 *
 */
public class Rock extends LandscapeItem implements ISquareBound {
	private int width, height;
	
	/**
	 * Rock constructor
	 */
	public Rock() {
		Random rnd = new Random();
		
		width = 0;
		while (width < 4) {
			width = rnd.nextInt(21);
		}
		height = 0;
		while (height < 4) {
			height = rnd.nextInt(21);
		}
		
		translate(rnd.nextInt((int)Game.getWorldRight()), rnd.nextInt((int)Game.getWorldTop()));
		rotate(rnd.nextInt(361));
		
		setColor(Color.GRAY);
	}
	
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return The rock's height
	 */
	public int getHeight() {
		return height;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(super.getMyTranslation());
		g2d.transform(super.getMyRotation());
				
		g2d.setColor(getColor());
		g2d.fillRect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		
		g2d.setTransform(saveAT);
	}
}
