package a4.Collection;

import java.util.ArrayList;

import a4.Movable.Vehicle.PlayerTank;
import a4.GameObject;

/**
 * Custom collection class using ArrayList
 * @author Cody
 *
 */
public class GameObjectCollection implements ICollection<GameObject> {
	private ArrayList<GameObject> gameObjList;
	
	public GameObjectCollection() {
		gameObjList = new ArrayList<GameObject>();
	}

	@Override	
	public void addObj(GameObject o) {
		gameObjList.add(o);
	}
		
	public void addObj(GameObject o, int index) {
		gameObjList.add(index, o);
	}

	@Override
	public IIterator<GameObject> getIterator() {
		return new GameObjectIterator();
	}
	
	@Override
	public int getSize() {
		return gameObjList.size();
	}
	
	public GameObject getObj(int index) {
		return gameObjList.get(index);
	}
	
	public void removeObj(int index) {
		gameObjList.remove(index);
	}
	
	public void removeObj(GameObject o) {
		IIterator<GameObject> itr = getIterator();
		while (itr.hasNext()) {
			GameObject gObj = itr.getNext();
			if (o.equals(gObj)) {
				itr.remove();
			}
		}
	}
	
	/**
	 * 
	 * @return the player's tank object
	 */
	public PlayerTank getPlayerTank() {
		return (PlayerTank)gameObjList.get(0);
	}
	
	/**
	 * 
	 * @author Cody
	 *
	 */
	private class GameObjectIterator implements IIterator<GameObject> {
		private int currElementIndex;
		
		public GameObjectIterator() {
			currElementIndex = -1;
		}
		
		@Override
		public boolean hasNext() {
			if (gameObjList.size() <= 0) { 
				return false; 
			}			
			if (currElementIndex == (gameObjList.size() - 1)) { 
				return false; 
			}
			
			return true;
		}

		@Override
		public GameObject getNext() {
			currElementIndex++;
			return (GameObject)gameObjList.get(currElementIndex);
		}

		@Override
		public void remove() {
			gameObjList.remove(currElementIndex);
			currElementIndex--;
		}
	}
}


