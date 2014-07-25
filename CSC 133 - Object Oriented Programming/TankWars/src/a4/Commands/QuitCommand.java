package a4.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class QuitCommand extends AbstractAction {
	private static QuitCommand quitCmd;
	
	private QuitCommand() {
		super("Quit");
	}

	public static QuitCommand getCommand() {
		if (quitCmd == null) {
			quitCmd = new QuitCommand();
		}
		return quitCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Quit requested from " + e.getActionCommand() + " " + e.getSource().getClass());
		int result = JOptionPane.showConfirmDialog(null,  "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			System.out.println("Thanks for playing! Good bye...");
			System.exit(0);
		}

	}
}
