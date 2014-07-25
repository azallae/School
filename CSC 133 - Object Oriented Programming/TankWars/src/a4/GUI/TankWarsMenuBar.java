package a4.GUI;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import a4.Commands.*;
import a4.Commands.MenuCmds.*;
import a4.GameWorld;

@SuppressWarnings("serial")
public class TankWarsMenuBar extends JMenuBar {
	public TankWarsMenuBar(GameWorld gw) {	
		// create file menu and file menu items
		JMenu fileMenu = new JMenu("File");
			JMenuItem newMenuItem = new JMenuItem();
			fileMenu.add(newMenuItem);
			JMenuItem saveMenuItem = new JMenuItem();
			fileMenu.add(saveMenuItem);
			JMenuItem undoMenuItem = new JMenuItem();
			fileMenu.add(undoMenuItem);
			JCheckBox soundCheckBox = new JCheckBox("Sound", true);
			fileMenu.add(soundCheckBox);
			JMenuItem aboutMenuItem = new JMenuItem();
			fileMenu.add(aboutMenuItem);
			JMenuItem quitMenuItem = new JMenuItem();
			fileMenu.add(quitMenuItem);
		
		// add menus to menu bar
		this.add(fileMenu);	
		
		// set actions for file menu items
		newMenuItem.setAction(NewCommand.getCommand());
		saveMenuItem.setAction(SaveCommand.getCommand());
		undoMenuItem.setAction(UndoCommand.getCommand());
		soundCheckBox.setAction(SoundToggleCmd.getCommand(gw));
		aboutMenuItem.setAction(AboutCommand.getCommand());
		quitMenuItem.setAction(QuitCommand.getCommand());		
	}
}
