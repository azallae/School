
package a4.Commands.MenuCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

import a4.GameWorld;

@SuppressWarnings("serial")
public class SoundToggleCmd extends AbstractAction {
	private static SoundToggleCmd stCmd;
	private GameWorld target;
	
	private SoundToggleCmd(GameWorld gw) {
		super("Sound");
		target = gw;
	}
	
	public static SoundToggleCmd getCommand(GameWorld gw) {
		if (stCmd == null) {
			stCmd = new SoundToggleCmd(gw);
		}
		return stCmd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox jcb = (JCheckBox)e.getSource();
			target.setSound(jcb.isSelected());
			
			if (jcb.isSelected()) {
				target.mute(false);
			}
			else {
				target.mute(true);
			}
		}		
	}
}
