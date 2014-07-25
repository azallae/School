package a4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import a4.Commands.KeyCmds.*;
import a4.GUI.*;

/**
 * Stores and controls the overall game data and functions
 * and tells the GameWorld to perform actions
 * @author Cody Lanier
 * 
 */
@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener {
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
	private static double worldRight, worldTop, worldLeft, worldBottom;
	private static final int tickSpeed = 20;
	private static final int preferedUpdateSpeed = 20;
	
	/**
	 * Creates an instance of the GameWorld, registers observables, and play begins. This is the Controller.
	 */
	public Game() {
		this.setTitle("Tank Wars");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
						
		setWorldRight(this.getWidth());
		setWorldTop(this.getHeight());
		setWorldLeft(this.getLocation().x);
		setWorldBottom(this.getLocation().y);
		
		mv = new MapView();
		mv.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.add(mv, BorderLayout.CENTER);
					
		sv = new ScoreView();
		sv.setBorder(new LineBorder(Color.GRAY, 1));		
		this.add(sv, BorderLayout.NORTH);
								
		gw = new GameWorld();
		gw.addObserver(mv);
		gw.addObserver(sv);
		
		this.setJMenuBar(new TankWarsMenuBar(gw));
		this.add(new CommandPanel(gw), BorderLayout.WEST);
		this.setVisible(true);
						
		gw.populateWorld();
		bindKeys();
				
		Timer myTimer = new Timer(tickSpeed, this);
		myTimer.start();
		
		this.requestFocus();
	}

	/**
	 * Binds keyboard keys to commands
	 */
	private void bindKeys() {
		// get the keystrokes
		KeyStroke upArrow = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
		KeyStroke downArrow = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
		KeyStroke leftArrow = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
		KeyStroke rightArrow = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
		KeyStroke space = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
		KeyStroke gKey = KeyStroke.getKeyStroke(KeyEvent.VK_G, 0);
		KeyStroke pKey = KeyStroke.getKeyStroke(KeyEvent.VK_P, 0);
		
		// assign to the top level world map
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = mv.getInputMap(mapName);
		imap.put(upArrow, "speed up");
		imap.put(downArrow, "slow down");
		imap.put(leftArrow, "turn left");
		imap.put(rightArrow, "turn right");	
		imap.put(space, "fire missile");
		imap.put(gKey, "fire spiked grenade");
		imap.put(pKey, "fire plasma wave");
				
		// assign commands to mapped keys
		ActionMap amap = mv.getActionMap();
		amap.put("speed up", SpeedUpCommand.getCommand(gw));
		amap.put("slow down", SlowDownCommand.getCommand(gw));
		amap.put("turn left", TurnLeftCommand.getCommand(gw));
		amap.put("turn right", TurnRightCommand.getCommand(gw));
		amap.put("fire missile", FirePlayerMissileCmd.getCommand(gw));
		amap.put("fire spiked grenade", FirePlayerSpikedGrenadeCmd.getCommand(gw));
		amap.put("fire plasma wave", FirePlayerPlasmaWaveCmd.getCommand(gw));
	}
	
	public void actionPerformed(ActionEvent e) {
		if (!gw.isPaused())
			gw.elapseTime();
	}

	public static int getTickSpeed() {
		return tickSpeed;
	}
	
	public static int getPreferedUpdateSpeed() {
		return preferedUpdateSpeed;
	}

	public static double getWorldBottom() {
		return worldBottom;
	}

	public static void setWorldBottom(double worldBottom) {
		Game.worldBottom = worldBottom;
	}

	public static double getWorldTop() {
		return worldTop;
	}

	public static void setWorldTop(double worldTop) {
		Game.worldTop = worldTop;
	}

	public static double getWorldLeft() {
		return worldLeft;
	}

	public static void setWorldLeft(double d) {
		Game.worldLeft = d;
	}

	public static double getWorldRight() {
		return worldRight;
	}

	public static void setWorldRight(double worldRight) {
		Game.worldRight = worldRight;
	}

	public static double getWorldHeight() {
		return Game.worldTop - Game.worldBottom;
	}

	public static double getWorldWidth() {
		return Game.worldRight - Game.worldLeft;
	}
}
