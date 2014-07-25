package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class TurnLeftCommand extends AbstractAction {

	private static TurnLeftCommand tlCmd;
	private GameWorld target;
	
	private TurnLeftCommand(GameWorld t) {
		super("Turn Left");
		target = t;
	}
	
	public static TurnLeftCommand getCommand(GameWorld t) {
		if (tlCmd == null ) {
			tlCmd = new TurnLeftCommand(t);
		}
		return tlCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.turnLeft();
	}

}
