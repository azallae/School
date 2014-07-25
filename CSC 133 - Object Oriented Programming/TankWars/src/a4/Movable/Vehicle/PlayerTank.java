 package a4.Movable.Vehicle;

import java.awt.Color;

import a4.Sound.Sound;

public class PlayerTank extends Tank {		
	public PlayerTank(Sound missile, Sound tank, Sound rock, Sound tree, Sound grenade) {
		super();
		setColor(Color.BLUE);
		setTankCollide(tank);
		setRockCollide(rock);
		setTreeCollide(tree);
		setFireMissile(missile);
		setFireGrenade(grenade);
	}

	@Override
	public void update(int elapsedTime) {
		move(elapsedTime);
	}
}
