package a4.Movable.Projectile;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.GameObject;
import a4.Movable.Projectile.Grenade;
import a4.Movable.Vehicle.Tank;
import a4.Movable.Vehicle.Vehicle;
import a4.Sound.Sound;

public class SpikedGrenade extends Grenade {
	private Spike[] spikes;
	private double spikeOffset = 0;
	private double spikeInc = 0.05;
	private double maxSpikeOffset = 0.5; 
	private Sound hitTank;
	
	public SpikedGrenade(Tank t, Sound explosion, Sound hit) {
		super(t, explosion, hit);
		setCreator(t);
		hitTank = hit;
		spikes = new Spike[4];
				
		Spike s0 = new Spike(t);
		s0.translate(0, 3);
		spikes[0] = s0;
		
		Spike s1 = new Spike(t);
		s1.translate(0, 5);
		s1.rotate(-90);
		spikes[1] = s1;
		
		Spike s2 = new Spike(t);
		s2.translate(0, 3);
		s2.rotate(180);
		spikes[2] = s2;
		
		Spike s3 = new Spike(t);
		s3.translate(0, 5);
		s3.rotate(90);
		spikes[3] = s3;
	}
	
	@Override
	public boolean collidesWith(GameObject otherObj) {
		if (otherObj instanceof Vehicle) {
			Vehicle v = (Vehicle)otherObj;
			if (getCreator() == v) {
				return false;
			}
		}	
				
		return false;
	}

	@Override
	public void handleCollision(GameObject otherObj) {
		setDestroyed(true);	
		
		if (otherObj instanceof Tank) {
			hitTank.play();
		}
	}
	
	@Override
	public void update(int elapsedTime) {
		spikeOffset += spikeInc;
		for (Spike s : spikes) { 
			s.translate(0, spikeOffset);
		} 
		
		if (Math.abs(spikeOffset) >= maxSpikeOffset) { 
			spikeInc *= -1 ; 
		}
		
		super.update(elapsedTime);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		
		AffineTransform saveAT = g2d.getTransform();
				
		g2d.transform(getMyTranslation()); 
		g2d.transform(getMyRotation()); 
		g2d.transform(getMyScale()); 
						
		for (Spike s : spikes) { 
			s.draw(g2d);
		} 
		
		g2d.setTransform (saveAT) ;
	}	
}
