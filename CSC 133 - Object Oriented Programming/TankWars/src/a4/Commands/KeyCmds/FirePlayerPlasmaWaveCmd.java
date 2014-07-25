package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class FirePlayerPlasmaWaveCmd extends AbstractAction {
	private static FirePlayerPlasmaWaveCmd fppwCmd;
	private GameWorld target;
	
	private FirePlayerPlasmaWaveCmd(GameWorld t) {
		super("Fire Player Plasma Wave");
		target = t;
	}
	
	public static FirePlayerPlasmaWaveCmd getCommand(GameWorld t) {
		if (fppwCmd == null) {
			fppwCmd = new FirePlayerPlasmaWaveCmd(t);
		}
		return fppwCmd; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.firePlayerPlasmaWave();
	}
}
