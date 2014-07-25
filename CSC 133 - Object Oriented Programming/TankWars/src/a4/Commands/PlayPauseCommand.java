package a4.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class PlayPauseCommand extends AbstractAction {
	private static PlayPauseCommand ppCmd;
	private GameWorld target;
	
	private PlayPauseCommand(GameWorld gw) {
		super("Pause");
		target = gw;
	}
	
	public static PlayPauseCommand getCommand(GameWorld gw) {
		if (ppCmd == null) {
			ppCmd = new PlayPauseCommand(gw);
		}
		return ppCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		target.switchMode(e);
	}
}
