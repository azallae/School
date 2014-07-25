package a4.Strategy;

import a4.Movable.Vehicle.EnemyTank;
import a4.Game;
import a4.GameWorld;

public class EveryTickStrategy extends EnemyTankStrategy {
		
	public EveryTickStrategy(GameWorld gw, EnemyTank t) {
		super(gw, t);
	}
	
	@Override
	public void apply() {
		EnemyTank enemyTank = getTarget();
		if((enemyTank.getTicks() % (1000/Game.getTickSpeed())) == 0) {
			if (enemyTank.fireMissile()) {
				getClient().addNewMissile(enemyTank);
			}
		}
	}
}
