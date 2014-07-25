package a4.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class HelpCommand extends AbstractAction {
	private static HelpCommand helpCmd;
	
	private HelpCommand() {
		super("Help");
	}
	
	public static HelpCommand getCommand() {
		if (helpCmd == null) {
			helpCmd = new HelpCommand();
		}
		return helpCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Help requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		System.out.println("'r' (right turn) - turns your tank 5 degrees clockwise");
		System.out.println("'l' (left turn) - turns your tank 5 degrees counter-clockwise");
		System.out.println("'i' (increase speed) - increases your tank's speed by one unit");
		System.out.println("'k' (decrease speed) - slows down your tank by one unit");
		System.out.println("'f' (fire missile) - fires one of the your tank's missiles, if you have any remaining");
		System.out.println("'e' (enemy missile) - causes an enemy tank to fire a missile");
		System.out.println("'1' (missile hit) - causes a tank to be hit by a missile, which can result in tank destruction or armor reduction");
		System.out.println("'2' (missile collision) - causes two missiles to collide and destroy each other");
		System.out.println("'3' (tank collision) - causes a tank to collide with an object, blocking it from movement until the tank changes its direction");
		System.out.println("'t' (clock tick) - moves the game clock (aka Elasped Time) one tick forward");
		System.out.println("'d' (display) - displays the Current Score, Lives Remaining, and Elasped Time");
		System.out.println("'m' (map) - outputs a map of the world with object locations");
		System.out.println("'?' (help) - displays this help screen");
		System.out.println("'q' (quit) - ends the game and exits the program");
	}
}
