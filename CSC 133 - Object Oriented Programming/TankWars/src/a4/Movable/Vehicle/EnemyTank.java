package a4.Movable.Vehicle;

import java.awt.Color;

import a4.Sound.Sound;
import a4.Strategy.EnemyTankStrategy;
import a4.Strategy.EveryOtherTickStrategy;
import a4.Strategy.EveryTickStrategy;
import a4.Strategy.IEnemyNPC;
import a4.Strategy.IStrategy;
import a4.Game;
import a4.GameWorld;

public class EnemyTank extends Tank implements IEnemyNPC {
	private EnemyTankStrategy currStrategy;
	private EveryTickStrategy everyTick;
	private EveryOtherTickStrategy everyOtherTick;
	
	private int ticks = 0;
	
	public EnemyTank(GameWorld gw, Sound missile, Sound tank, Sound rock, Sound tree) {
		super();
		setColor(Color.RED);
		everyTick = new EveryTickStrategy(gw, this);
		everyOtherTick = new EveryOtherTickStrategy(gw, this);
		setStrategy(everyOtherTick);
		setTankCollide(tank);
		setRockCollide(rock);
		setTreeCollide(tree);
		setFireMissile(missile);
	}
	
	
	@Override
	public void update(int elapsedTime) {
		move(elapsedTime);
		ticks++;
		takeAction();
	}
	
	private void takeAction() {
		if (ticks % (1000/Game.getTickSpeed()*30) == 0) {
			switchStrategy();
		}
		else
		{
			invokeStrategy();
		}
	}


	private void switchStrategy() {
		if (getStrategy() == everyTick) {
			setStrategy(everyOtherTick);
		}
		else {
			setStrategy(everyTick);
		}
	}


	public EnemyTankStrategy getStrategy() {
		return currStrategy;
	}

	@Override
	public void setStrategy(IStrategy s) {
		currStrategy = (EnemyTankStrategy)s;
		ticks = 0;
	}

	@Override
	public void invokeStrategy() {
		currStrategy.apply();
	}
	
	public int getTicks() {
		return ticks;
	}
}
