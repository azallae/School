package games.treasurehunt2014.objects;

import java.awt.Color;

import sage.scene.shape.*;

public class Pearl extends Sphere {
	int value;
	Color myColor;
	
	public Pearl(int v) {
		super(v*.5, 50, 25, new Color(255,255,255));
		value = v;
	}

	public int getValue() {
		return value;
	}
}
