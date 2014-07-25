package a4.Proxy;

import a4.Observer.IObservable;
import a4.Observer.IObserver;
import a4.GameObject;
import a4.GameWorld;
import a4.Collection.IIterator;

public class GameWorldProxy implements IObservable, IGameWorld {

	private GameWorld realGW;
	
	public GameWorldProxy(GameWorld gw) {
		realGW = gw;
	}
		
	/**
	 * proxy does not need observers, therefore, empty method
	 */
	@Override
	public void addObserver(IObserver obs) {
		
	}

	/**
	 * proxy does not need observers, therefore, empty method
	 */
	@Override
	public void notifyObservers() {
		
	}

	/**
	 * provides an iterator to go through the game objects
	 */
	@Override
	public IIterator<GameObject> getObjsIterator() {
		return realGW.getObjsIterator();
	}
	
	/**
	 * proxy cannot add game objects, empty method
	 */
	@Override
	public void addGameObject(GameObject o) {
		
	}

	/**
	 * proxy cannot remove game objects, empty method
	 */
	@Override
	public void removeGameObject(GameObject o) {
		
	}

	/**
	 * 
	 * @return The number of lives remaining
	 */
	@Override
	public int getLivesRemaining() {
		return realGW.getLivesRemaining();
	}
	
	/**
	 * 
	 * @return The amount of time cycles that have passed
	 */
	@Override
	public int getElapsedTime() {
		return realGW.getElapsedTime();
	}

	/**
	 * 
	 * @return The user's current score
	 */
	@Override
	public int getCurrentScore() {
		return realGW.getCurrentScore();
	}
	
	@Override
	public boolean isSoundOn() {
		return realGW.isSoundOn();
	}

	public boolean isPaused() {
		return realGW.isPaused();
	}
}
