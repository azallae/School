package a4.Commands.MenuCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class NewCommand extends AbstractAction {
	private static NewCommand newCmd;
	
	private NewCommand() {
		super("New");
	}
	
	public static NewCommand getCommand() {
		if (newCmd == null) {
			newCmd = new NewCommand();
		}
		return newCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("New action invoked from " + e.getActionCommand() + " " + e.getSource().getClass());
	}
}

