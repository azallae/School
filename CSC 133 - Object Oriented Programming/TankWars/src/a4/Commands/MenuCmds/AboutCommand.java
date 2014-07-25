package a4.Commands.MenuCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AboutCommand extends AbstractAction {
	private static AboutCommand aboutCmd;
	
	private AboutCommand() {
		super("About");
	}
	
	public static AboutCommand getCommand() {
		if (aboutCmd == null) {
			aboutCmd = new AboutCommand();
		}
		return aboutCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("About requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		JOptionPane.showMessageDialog(null, "Author: Cody Lanier\nCourse: CSc 133\nVersion: 2.6", "About Tank Wars", JOptionPane.INFORMATION_MESSAGE);
	}
}
