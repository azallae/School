package a4.Commands.MenuCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class SaveCommand extends AbstractAction {
	private static SaveCommand saveCmd;
	
	private SaveCommand() {
		super("Save");
	}
	
	public static SaveCommand getCommand() {
		if (saveCmd == null) {
			saveCmd = new SaveCommand();
		}
		return saveCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Save action invoked from " + e.getActionCommand() + " " + e.getSource().getClass());
	}
}
