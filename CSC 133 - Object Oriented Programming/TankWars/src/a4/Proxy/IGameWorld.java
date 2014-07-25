package a4.Proxy;

import a4.GameObject;
import a4.Collection.IIterator;

public interface IGameWorld {
	public IIterator<GameObject> getObjsIterator();
	public void addGameObject(GameObject o);
	public void removeGameObject(GameObject o);
	public int getElapsedTime();
	public int getCurrentScore();
	public int getLivesRemaining();
	public boolean isSoundOn();
}
