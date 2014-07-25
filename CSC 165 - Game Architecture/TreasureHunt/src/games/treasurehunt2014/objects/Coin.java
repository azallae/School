package games.treasurehunt2014.objects;

import graphicslib3D.Vector3D;

import java.awt.Color;

import sage.scene.shape.*;

public class Coin extends Cylinder {
	int value;
	
	public Coin(int v) {
		value = v;
		setHeight(v*.2);
		setRadius(v*2);
		setSlices(100);
		setStacks(1);
		setSolid(true);
		setColor(new Color(207,181,59));
	}

	public int getValue() {
		return value;
	}
	
	public void update(float elapsedTimeMS) {
		Vector3D Yaxis = new Vector3D(0,1,0);
		rotate(1.0f, Yaxis);
		updateLocalBound();
	}
}
