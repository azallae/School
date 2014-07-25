package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class TurnRightCommand extends AbstractAction {

	private static TurnRightCommand trCmd;
	private GameWorld target;
	
	private TurnRightCommand(GameWorld t) {
		super("Turn Right");
		target = t;
	}
	
	public static TurnRightCommand getCommand(GameWorld t) {
		if (trCmd == null ) {
			trCmd = new TurnRightCommand(t);
		}
		return trCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.turnRight();
	}
}
