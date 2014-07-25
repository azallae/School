package a4.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import a4.Commands.HelpCommand;
import a4.Commands.PlayPauseCommand;
import a4.Commands.QuitCommand;
import a4.Commands.ReverseCommand;
import a4.GameWorld;

@SuppressWarnings("serial")
public class CommandPanel extends JPanel {
	public CommandPanel(GameWorld target) {
		this.setPreferredSize(new Dimension(180, this.getHeight()));
		
		JButton help = new JButton();
		JButton quit = new JButton();
		JButton mode = new JButton();
		JButton reverse = new JButton();
						
		help.setAction(HelpCommand.getCommand());
		quit.setAction(QuitCommand.getCommand());
		mode.setAction(PlayPauseCommand.getCommand(target));
		reverse.setAction(ReverseCommand.getCommand(target));
		
		help.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
		quit.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
		mode.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
		reverse.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"none");
		
		reverse.setEnabled(false);
		
		this.add(mode);
		this.add(reverse);
		this.add(help);
		this.add(quit);	
		
		
		this.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Commands: "));
		this.setLayout(new GridLayout(this.getComponentCount(), 1));			
	}
}
