package a4.Movable.Vehicle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Random;

import a4.Landscape.Rock;
import a4.Landscape.Tree;
import a4.Movable.Projectile.Missile;
import a4.Movable.Projectile.PlasmaWave;
import a4.Movable.Projectile.Projectile;
import a4.Movable.Projectile.SpikedGrenade;
import a4.Selection.ISelectable;
import a4.Sound.Sound;
import a4.Game;
import a4.GameObject;
import a4.GameWorld;
import a4.Collisions.*;

/**
 * Tanks
 * @author Cody Lanier
 *
 */
public abstract class Tank extends Vehicle implements ISquareBound, ISelectable {
	private final int MAXARMOR = 10;
	private final int MAXMISSILES = 50;
	private final int MAXSPIKEDGRENADES = 10;
	private final int MAXPLASMAWAVES = 4;
	
	private final int width = 20;
	private final int height = 20;
	private final int barrelWidth = 6;
	private final int barrelHeight = 9;
	
	private int armorStrength;
	private int missileCount;
	private int spikedGrenadeCount;
	private int plasmaWaveCount;
	private boolean selected;

	private Sound treeCollide;
	private Sound rockCollide;
	private Sound tankCollide;
	private Sound fireMissile;
	private Sound fireGrenade;
	
	
	/**
	 * Tank constructor
	 */
	public Tank() {
		Random rnd = new Random();
		
		setSpeed(rnd.nextInt(4));
		setDirection(rnd.nextInt(361));
		setArmorStrength(MAXARMOR);
		setMissileCount(MAXMISSILES);
		setSpikedGrenadeCount(MAXSPIKEDGRENADES);
		setPlasmaWaveCount(MAXPLASMAWAVES);
		
		translate(rnd.nextInt((int)Game.getWorldRight()), rnd.nextInt((int)Game.getWorldTop()));
	}
	
	/**
	 * 
	 * @return The tank's armor strength
	 */
	public int getArmorStrength() {
		return armorStrength;
	}
	
	/**
	 * Sets the tank's armor strength
	 * @param as
	 */
	private void setArmorStrength(int as) {
		if (as >= 0 && as <= MAXARMOR) { this.armorStrength = as; }		
	}
	
	/**
	 * Decrement the tank's armor
	 * @return True if the armor was decremented
	 * False when there is no armor to decrement
	 */
	public void decArmorStrength() {
		if (hasArmor()) { 
			this.armorStrength--;
		}
		else {
			setDestroyed(true);
		}
	}

	/**
	 * 
	 * @return Whether or not the tank has armor strength remaining
	 */
	private boolean hasArmor() {
		if (getArmorStrength() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return How many missiles the tank has
	 */
	public int getMissileCount() {
		return missileCount;
	}	
	
	/**
	 * Sets the number of missiles for the tank
	 * @param mc
	 */
	public void setMissileCount(int mc) {
		this.missileCount = mc;		
	}
	
	/**
	 * If the tank has a missile, fire it
	 * @return Whether or not a missile was fired
	 */
	public boolean fireMissile() {
		if (hasMissile()) {
			setMissileCount(getMissileCount() -1);
			fireMissile.play();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return If the tank has at least one missile or not
	 */
	public boolean hasMissile() {
		return (getMissileCount() > 0);
	}
	
	@Override
	public void drawNormal(Graphics2D g2d) {		
		int centerX = 0;
		int centerY = 0;
		g2d.setColor(getColor());
		g2d.fillRect(centerX-getWidth()/2, centerY-getHeight()/2, getWidth(), getHeight()); //tank body
		g2d.fillRect(centerX-getBarrelWidth()/2, centerY+(getHeight()/2), getBarrelWidth(), getBarrelHeight()); //weapon barrel
	}
	
	@Override
	public void drawHighlighted(Graphics2D g2d) {
		int centerX = 0;
		int centerY = 0;
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(centerX-(getWidth()/2)-2, centerY-(getHeight()/2)-2, getWidth()+4, getHeight()+getBarrelHeight()+4);
		drawNormal(g2d);
	}
	
	@Override
	public boolean contains(Point2D worldPoint) {
		Point2D localPoint = worldPoint;
		
		try {
			localPoint = getMyTransform().createInverse().transform(worldPoint, null);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isInside(localPoint);
	}
	
	private boolean isInside(Point2D localPoint) {
		double x = localPoint.getX();
		double y = localPoint.getY();
		
		return ((x >= -getWidth()/2 && x <= getWidth()/2) && (y >= -getHeight()/2 && y <= getHeight()/2));
	}

	public boolean collidesWith(GameObject otherObj) {
		if (otherObj instanceof Projectile) {
			Projectile p = (Projectile)otherObj;
			if (p.getCreator() == this) {
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
		
		if (otherObj instanceof ISquareBound) {
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
		else if (otherObj instanceof ICircleBound) {
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
	
	public void handleCollision(GameObject otherObj) {
		//Missiles handle the hitTank sound
		if (otherObj instanceof Missile) {
			decArmorStrength();	
			if (((Missile)otherObj).getCreator() instanceof PlayerTank && this instanceof EnemyTank)
				GameWorld.incScore(20);
		}
		else if (otherObj instanceof SpikedGrenade) {
			decArmorStrength();	
			decArmorStrength();
			((Projectile) otherObj).setDestroyed(true);
			if (((SpikedGrenade)otherObj).getCreator() instanceof PlayerTank && this instanceof EnemyTank)
				GameWorld.incScore(20);
		}		
		else if (otherObj instanceof PlasmaWave) {
			setDestroyed(true);
			if (((PlasmaWave)otherObj).getCreator() instanceof PlayerTank && this instanceof EnemyTank)
				GameWorld.incScore(20);
		}
		else {
			if (!isBlocked()) {
				if (otherObj instanceof Tank) {
					tankCollide.play();
				}
				else if (otherObj instanceof Tree) {
					treeCollide.play();
				}
				else if (otherObj instanceof Rock) {
					rockCollide.play();
				}
				
				setBlocked(true);
			}
		}
	}
	
	/**
	 * 
	 * @return The tank's width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return The tank height
	 */
	public int getHeight() {
		return height;
	}
	
	public int getBarrelWidth() {
		return barrelWidth;
	}
	
	public int getBarrelHeight() {
		return barrelHeight;
	}
	
	public void setSelected(boolean yesNO) {
		this.selected = yesNO;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void draw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getMyTranslation());
		g2d.transform(getMyRotation());
		g2d.transform(getMyScale());
		
		if (isSelected()) {
			drawHighlighted(g2d);
		}
		else {
			drawNormal(g2d);
		}
		
		g2d.setTransform(saveAT);
	}
	
	public Sound getTankCollide() {
		return tankCollide;
	}
	
	public Sound getRockCollide() {
		return rockCollide;
	}
	
	public Sound getTreeCollide() {
		return treeCollide;
	}
	
	public Sound getFireMissile() {
		return fireMissile;
	}
	
	public Sound getFireGrenade() {
		return fireGrenade;
	}
	
	public void setTankCollide(Sound s) {
		tankCollide = s;
	}
	
	public void setRockCollide(Sound s) {
		rockCollide = s;
	}
	
	public void setTreeCollide(Sound s) {
		treeCollide = s;
	}
	
	public void setFireMissile(Sound s) {
		fireMissile = s;
	}
	
	public void setFireGrenade(Sound s) {
		fireGrenade = s;
	}
	
	public boolean fireSpikedGrenade() {
		if (hasSpikedGrenade()) {
			setSpikedGrenadeCount(getSpikedGrenadeCount() - 1);
			fireGrenade.play();
			return true;
		}
		return false;
	}

	private int getSpikedGrenadeCount() {
		return spikedGrenadeCount;
	}
	
	private void setSpikedGrenadeCount(int sgc) {
		spikedGrenadeCount = sgc;
	}

	private boolean hasSpikedGrenade() {
		return (spikedGrenadeCount > 0);
	}
	
	public boolean firePlasmaWave() {
		if (hasPlasmaWave()) {
			setPlasmaWaveCount(getPlasmaWaveCount() - 1);
//			fireGrenade.play();
			return true;
		}
		return false;
	}

	private int getPlasmaWaveCount() {
		return plasmaWaveCount;
	}
	
	private void setPlasmaWaveCount(int sgc) {
		plasmaWaveCount = sgc;
	}

	private boolean hasPlasmaWave() {
		return (plasmaWaveCount > 0);
	}
}
