package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class SpeedUpCommand extends AbstractAction {

	private static SpeedUpCommand suCmd;
	private GameWorld target;
	
	private SpeedUpCommand(GameWorld t) {
		super("Speed Up");
		target = t;
	}
	
	public static SpeedUpCommand getCommand(GameWorld t) {
		if (suCmd == null) {
			suCmd = new SpeedUpCommand(t);
		}
		return suCmd; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.incSpeed();
	}
}
