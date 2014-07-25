package a4.Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.Movable.Vehicle.Tank;
import a4.GameObject;
import a4.GameWorld;
import a4.Collection.IIterator;

@SuppressWarnings("serial")
public class ReverseCommand extends AbstractAction {
	private static ReverseCommand rCmd;
	private GameWorld target;
	
	private ReverseCommand(GameWorld gw) {
		super("Reverse");
		target = gw;
		if (!gw.isPaused()) {
			setEnabled(false);
		}
	}
	
	public static ReverseCommand getCommand(GameWorld gw) {
		if (rCmd == null) {
			rCmd = new ReverseCommand(gw);
		}
		return rCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		IIterator<GameObject> iter = target.getObjsIterator();
		while (iter.hasNext()) {
			GameObject gObj = iter.getNext();
			if (gObj instanceof Tank) {
				Tank t = (Tank) gObj;
				if (t.isSelected())
					t.setDirection(t.getDirection() + 180);
			}
		}
	}
}
