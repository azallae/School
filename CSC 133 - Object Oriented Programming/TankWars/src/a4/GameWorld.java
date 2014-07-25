package a4;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import a4.Commands.ReverseCommand;
import a4.Commands.KeyCmds.*;
import a4.Landscape.*;
import a4.Movable.Projectile.Missile;
import a4.Movable.Projectile.PlasmaWave;
import a4.Movable.Projectile.Projectile;
import a4.Movable.Projectile.SpikedGrenade;
import a4.Movable.Vehicle.EnemyTank;
import a4.Movable.Vehicle.PlayerTank;
import a4.Movable.Vehicle.Tank;
import a4.Observer.*;
import a4.Proxy.*;
import a4.Sound.Sound;
import a4.Collection.*;
import a4.Collisions.ICollider;

/**
 * Hold and manipulate world objects. This is the Model.
 * @author Cody Lanier
 *
 */
public class GameWorld implements IObservable, IGameWorld {
	Random rnd = new Random();
		
	private int livesRemaining = 3;
	private int elapsedTime = 0;
	private static int currentScore = 0;
	private boolean sound = true;
	private boolean muted;
	
	private Sound music;
	private Sound tankCollide;
	private Sound treeCollide;
	private Sound rockCollide;
	private Sound hitTank;
	private Sound missileBlowUp;
	private Sound firePlayerMissile;
	private Sound fireEnemyMissile;
	private Sound fireGrenade;
	
	private int tanks = 4;
	private int trees = 8;
	private int rocks = 6;
	private GameObjectCollection goc;
	private ArrayList<IObserver> observersList;
	
	private boolean paused;
			
	/**
	 * Begins to setup the GameWorld
	 */
	public GameWorld() {
		goc = new GameObjectCollection();
		observersList = new ArrayList<IObserver>();		
		setupSounds();		
		music.loop();
	}
	
	/**
	 * Adds an observer
	 */
	@Override
	public void addObserver(IObserver obs) {
		observersList.add(obs);
	}

	/**
	 * Notify observers of updates/changes
	 */
	@Override
	public void notifyObservers() {
		GameWorldProxy gwp = new GameWorldProxy(this);
		for(IObserver o : observersList) {
			o.update(gwp, null);
		}
	}
	
	/**
	 * Provides an iterator to go through the game objects
	 */
	@Override
	public IIterator<GameObject> getObjsIterator() {
		return getObjsCollection().getIterator();
	}

	/**
	 * Adds an object to the world
	 */
	@Override
	public void addGameObject(GameObject o) {
		getObjsCollection().addObj(o);
	}

	/**
	 * Removes an object from the world
	 */
	@Override
	public void removeGameObject(GameObject o) {
		getObjsCollection().removeObj(o);
	}
	
	/**
	 * 
	 * @return The number of lives remaining
	 */
	@Override
	public int getLivesRemaining() {
		return livesRemaining;
	}
	
	/**
	 * Decreases the player's lives by 1 and tells them how many remain
	 */
	public void decLivesRemaining() {
		if (getLivesRemaining() > 0) {
			livesRemaining--;
			
			if (getLivesRemaining() == 0) {
				System.out.println("This is your last life. Be careful out there!");
			}
			else {
				System.out.println("You have " + getLivesRemaining() + " lives remaining.");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "You are out of lives. GAME OVER.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @return The amount of time cycles that have passed
	 */
	@Override
	public int getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * Increments the elapsed time by 1 cycle
	 */
	public void incElapsedTime() {
		elapsedTime++;
	}

	/**
	 * 
	 * @return The user's current score
	 */
	@Override
	public int getCurrentScore() {
		return currentScore;
	}

	/**
	 * Adds points to the users current score
	 * @param points
	 */
	public static void incScore(int points) {
		currentScore += points;
	}
	
	@Override
	public boolean isSoundOn() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
		notifyObservers();
		System.out.println("The sound has been toggled.");
	}

	/**
	 * 
	 * @return The collection of all objects in the GameWorld
	 */
	public GameObjectCollection getObjsCollection() {
		return goc;
	}

	/**
	 * Creates the objects in the GameWorld and adds them to the object list
	 */
	public void populateWorld() {
		PlayerTank playerTank = new PlayerTank(firePlayerMissile, tankCollide, rockCollide, treeCollide, fireGrenade);
		addGameObject(playerTank);	
		
		//make tanks
		int count = tanks;
		while (count > 0) {
			EnemyTank t = new EnemyTank(this, fireEnemyMissile, tankCollide, rockCollide, treeCollide);
			addGameObject(t);	
			count--;
		}
		
		//make trees
		count = trees;
		while (count > 0) {
			Tree t = new Tree();
			addGameObject(t);
			count--;
		}
		
		//make rocks
		count = rocks;
		while (count > 0) {
			Rock r = new Rock();
			addGameObject(r);
			count--;
		}
		notifyObservers();
	}
	
	/**
	 * 
	 * @return The player's tank
	 */
	public PlayerTank getPlayerTank() {
		return getObjsCollection().getPlayerTank();
	}

	/**
	 * Makes the player's tank turn right
	 */
	public void turnRight() {
		getPlayerTank().turnRight();	
		System.out.println("Your tank is now has a heading of " + getPlayerTank().getDirection());
		notifyObservers();
	}

	/**
	 * Makes the player's tank turn left
	 */
	public void turnLeft() {
		getPlayerTank().turnLeft();	
		System.out.println("Your tank is now has a heading of " + getPlayerTank().getDirection());
		notifyObservers();
	}

	/**
	 * Makes the player's tank go faster by 1 unit
	 */
	public void incSpeed() {
		getPlayerTank().incSpeed();
		System.out.println("Your tank's speed is now " + getPlayerTank().getSpeed());
		notifyObservers();
	}

	/**
	 * Makes the player's tank go slower by 1 unit
	 */
	public void decSpeed() {
		getPlayerTank().decSpeed();
		System.out.println("Your tank's speed is now " + getPlayerTank().getSpeed());
		notifyObservers();
	}

	/**
	 * Makes the player's tank fire a missile, 
	 * if it has any remaining
	 */
	public void firePlayerMissile() {
		if (getPlayerTank().fireMissile()) {
			addNewMissile(getPlayerTank());
			notifyObservers();
		}
		else {
			System.out.println("Your tank is out of missiles!");
		}
	}

	
	
	/**
	 * Causes each movable to update itself, as necessary
	 */
	private void updateObjects() {
		updateProjectiles();
		updateVehicles();
	}
	
	private void updateProjectiles() {
		IIterator<GameObject> gocIt = getObjsIterator();
		while (gocIt.hasNext()) {
			GameObject gObj = gocIt.getNext();
			
			if (gObj instanceof Projectile) {
				Projectile p = (Projectile) gObj;
				p.update(getElapsedTime());
			}
		}
	}
	
	private void updateVehicles() {
		IIterator<GameObject> gocIt = getObjsIterator();
		while (gocIt.hasNext()) {
			GameObject gObj = gocIt.getNext();
			
			if (gObj instanceof Tank) {
				((Tank)gObj).update(getElapsedTime());
			}
		}
	}
	
	/**
	 * Moves all objects, increments the elapsedTime, and then
	 * tells the user how much total time has elapsed
	 */
	public void elapseTime() {
		incElapsedTime();
		updateObjects();
		checkCollisions();
		destroyObjects();
		notifyObservers();
	}

	private void checkCollisions() {
		IIterator<GameObject> gocOutsideIt = getObjsIterator();
		while (gocOutsideIt.hasNext()) {
			GameObject x = gocOutsideIt.getNext();
			
			if (x instanceof ICollider) {
				IIterator<GameObject> gocInsideIt = getObjsIterator();
				while (gocInsideIt.hasNext()) {
					GameObject y = gocInsideIt.getNext();
					if (x != y) {
						if (((ICollider) x).collidesWith(y)) {
							((ICollider) x).handleCollision(y);
						}
					}
				}
			}
		}
	}

	private void destroyObjects() {
		IIterator<GameObject> gocIt = getObjsIterator();
		while (gocIt.hasNext()) {
			GameObject gObj = gocIt.getNext();
			
			if (gObj instanceof Projectile) {
				Projectile p = (Projectile)gObj;
								
				if (p.isDestroyed()) {
					gocIt.remove();
				}
			}
			else if (gObj instanceof Tank) {
				Tank t = (Tank)gObj;
				
				if (t.isDestroyed()) {
					gocIt.remove();
					respawn(t);
				}
			}
		}
	}

	private void respawn(Tank t) {
		if (t instanceof PlayerTank) {
			decLivesRemaining();
			getObjsCollection().addObj(new PlayerTank(firePlayerMissile, tankCollide, rockCollide, treeCollide, fireGrenade), 0);
		}
		else if (t instanceof EnemyTank) {
			getObjsCollection().addObj(new EnemyTank(this, fireEnemyMissile, tankCollide, rockCollide, treeCollide));
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	private void setPaused(boolean p) {
		this.paused = p;
	}

	public void switchMode(ActionEvent e) {
		boolean on = true;
		boolean off = false;
		
		//play the game
		if (isPaused()) {
			setPaused(false);
			((JButton) e.getSource()).setText("Pause");
			switchCmdsOnOff(on);
			ReverseCommand.getCommand(this).setEnabled(false);
			
			unselectAllTanks();
			if (!muted)
				music.loop();
		}
		//pause the game
		else {
			setPaused(true);
			((JButton) e.getSource()).setText("Play");
			switchCmdsOnOff(off);
			ReverseCommand.getCommand(this).setEnabled(true);
			
			music.stop();
		}
	}

	private void switchCmdsOnOff(boolean onOFF) {
		FirePlayerMissileCmd.getCommand(this).setEnabled(onOFF);
		SlowDownCommand.getCommand(this).setEnabled(onOFF);
		SpeedUpCommand.getCommand(this).setEnabled(onOFF);
		TurnLeftCommand.getCommand(this).setEnabled(onOFF);
		TurnRightCommand.getCommand(this).setEnabled(onOFF);
	}
	
	private void unselectAllTanks() {
		IIterator<GameObject> iter = getObjsIterator();
		while (iter.hasNext()) {
			GameObject gObj = iter.getNext();
			if (gObj instanceof Tank) {
				((Tank) gObj).setSelected(false);
			}
		}
	}
	
	public void setupSounds() {
		String slash = File.separator;
		String dir = "." + slash + "sounds" + slash;
		String name;
		String file;
		
		name = "Background.wav";
		file = dir + name;
		music = new Sound(file);
	
		name = "TankCollide.wav";
		file = dir + name;
		tankCollide = new Sound(file);
		
		name = "TreeCollide.wav";
		file = dir + name;
		treeCollide = new Sound(file);
		
		name = "RockCollide.wav";
		file = dir + name;
		rockCollide = new Sound(file);
		
		name = "MissileCollide.wav";
		file = dir + name;
		missileBlowUp = new Sound(file);
		
		name = "TankHit.wav";
		file = dir + name;
		hitTank = new Sound(file);
		
		name = "FirePlayerMissile.wav";
		file = dir + name;
		firePlayerMissile = new Sound(file);
		
		name = "FireEnemyMissile.wav";
		file = dir + name;
		fireEnemyMissile = new Sound(file);	
		
		name = "FirePlayerMissile.wav";
		file = dir + name;
		fireGrenade = new Sound(file);
	}
	
	public void mute(boolean b) {
		if (b == false && !isPaused()) {
			music.loop();
		}
		else {
			music.stop();
		}
		
		tankCollide.setMuted(b);
		rockCollide.setMuted(b);
		treeCollide.setMuted(b);
		missileBlowUp.setMuted(b);
		hitTank.setMuted(b);
		firePlayerMissile.setMuted(b);
		fireEnemyMissile.setMuted(b);
		fireGrenade.setMuted(b);
		
		muted = b;
	}

	public void addNewMissile(Tank t) {
		Missile m = new Missile(t, missileBlowUp, hitTank);
		addGameObject(m);
	}

	public void firePlayerSpikedGrenade() {
		if (getPlayerTank().fireSpikedGrenade()) {
			addNewSpikedGrenade(getPlayerTank());
			notifyObservers();
		}
		else {
			System.out.println("Your tank is out of spiked grenades!");
		}
	}

	private void addNewSpikedGrenade(Tank t) {
		SpikedGrenade sg = new SpikedGrenade(t, missileBlowUp, hitTank);
		addGameObject(sg);
	}
	
	public void firePlayerPlasmaWave() {
		if (getPlayerTank().firePlasmaWave()) {
			addNewPlasmaWave(getPlayerTank());
			notifyObservers();
		}
		else {
			System.out.println("Your tank is out of plasma waves!");
		}
	}

	private void addNewPlasmaWave(Tank t) {
		PlasmaWave pw = new PlasmaWave(t, hitTank);
		addGameObject(pw);
	}
}
