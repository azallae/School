package a4.Strategy;

import a4.Movable.Vehicle.EnemyTank;
import a4.GameWorld;

public abstract class EnemyTankStrategy implements IStrategy {

	private GameWorld client;
	private EnemyTank target;
	
	public EnemyTankStrategy(GameWorld gw, EnemyTank t) {
		client = gw;
		target = t;
	}
	
	public GameWorld getClient() {
		return client;
	}

	public EnemyTank getTarget() {
		return target;
	}
}
