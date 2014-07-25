package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class SlowDownCommand extends AbstractAction {

	private static SlowDownCommand sdCmd;
	private GameWorld target;
	
	private SlowDownCommand(GameWorld t) {
		super("Slow Down");
		target = t;
	}
	
	public static SlowDownCommand getCommand(GameWorld t) {
		if (sdCmd == null) {
			sdCmd = new SlowDownCommand(t);
		}
		return sdCmd; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.decSpeed();
	}
}
