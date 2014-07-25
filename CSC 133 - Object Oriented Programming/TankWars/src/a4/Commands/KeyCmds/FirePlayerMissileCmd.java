package a4.Commands.KeyCmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class FirePlayerMissileCmd extends AbstractAction{

	private static FirePlayerMissileCmd fpmCmd;
	private GameWorld target;
	
	private FirePlayerMissileCmd(GameWorld t) {
		super("Fire Player Missile");
		target = t;
	}
	
	public static FirePlayerMissileCmd getCommand(GameWorld t) {
		if (fpmCmd == null) {
			fpmCmd = new FirePlayerMissileCmd(t);
		}
		return fpmCmd; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			target.firePlayerMissile();
	}
}
