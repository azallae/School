package a4.Commands.MenuCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class UndoCommand extends AbstractAction {
	private static UndoCommand undoCmd;
	
	private UndoCommand() {
		super("Undo");
	}
	
	public static UndoCommand getCommand() {
		if (undoCmd == null) {
			undoCmd = new UndoCommand();
		}
		return undoCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Undo action invoked from " + e.getActionCommand() + " " + e.getSource().getClass());
	}
}
