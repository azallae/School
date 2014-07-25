package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class FirePlayerSpikedGrenadeCmd extends AbstractAction {

	private static FirePlayerSpikedGrenadeCmd fpsgCmd;
	private GameWorld target;
	
	private FirePlayerSpikedGrenadeCmd(GameWorld t) {
		super("Fire Player Spiked Grenade");
		target = t;
	}
	
	public static FirePlayerSpikedGrenadeCmd getCommand(GameWorld t) {
		if (fpsgCmd == null) {
			fpsgCmd = new FirePlayerSpikedGrenadeCmd(t);
		}
		return fpsgCmd; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.firePlayerSpikedGrenade();
	}
}
