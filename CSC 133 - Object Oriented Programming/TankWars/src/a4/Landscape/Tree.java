package a4.Landscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import a4.Game;
import a4.Collisions.ICircleBound;

/**
 * Trees
 * @author Cody Lanier
 *
 */
public class Tree extends LandscapeItem implements ICircleBound {
	private int diameter;
		
	/**
	 * Tree constructor
	 */
	public Tree() {
		Random rnd = new Random();

		diameter = 0;
		while (diameter < 7) {
			diameter = rnd.nextInt(21);
		}
				
		translate(rnd.nextInt((int)Game.getWorldRight()), rnd.nextInt((int)Game.getWorldTop()));
		
		setColor(Color.GREEN);
	}
	
	/**
	 * 
	 * @return The tree's diameter
	 */
	public int getDiameter() {
		return diameter;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
				
		g2d.transform(super.getMyTranslation());
				
		int radius = getDiameter()/2;
		g2d.setColor(getColor());
		g2d.fillOval(-radius, -radius, getDiameter(), getDiameter());
		
		g2d.setTransform(saveAT);
	}
}
