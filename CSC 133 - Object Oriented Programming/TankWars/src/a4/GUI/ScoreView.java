package a4.GUI;

import javax.swing.*;

import a4.Observer.IObservable;
import a4.Observer.IObserver;
import a4.Proxy.GameWorldProxy;
import a4.Game;

/**
 * A View showing the various game state data.
 * @author Cody Lanier
 *
 */
@SuppressWarnings("serial")
public class ScoreView extends JPanel implements IObserver {
	private final int spacer = 60;
	private JLabel et;
	private JLabel lr;
	private JLabel cs;
	private JLabel snd;
	private int ticks;
	private int minutes;
	private int seconds;
	
	public ScoreView(IObservable theModel) {
		initialize();
		theModel.addObserver(this);		
	}
	
	public ScoreView() {
		initialize();
	}
	
	/**
	 * outputs a current view
	 */
	@Override
	public void update(IObservable o, Object arg) {
		GameWorldProxy gwp = (GameWorldProxy)o;
		et.setText(getTime());
		lr.setText(Integer.toString(gwp.getLivesRemaining()));
		cs.setText(Integer.toString(gwp.getCurrentScore()));
		snd.setText(getSound(gwp));
	}
	
	private String getSound(GameWorldProxy gwp) {
		if (gwp.isSoundOn()) {
			return "ON";
		}
		return "OFF";
	}

	private void initialize() {
		ticks = 0;
		minutes = 0;
		seconds = 1;
		
		this.add(new JLabel("Elapsed Time: "));
		et = new JLabel("0");
		this.add(et);
		add(Box.createHorizontalStrut(spacer));
				
		this.add(new JLabel("Lives Remaining: "));
		lr = new JLabel("3");
		this.add(lr);
		add(Box.createHorizontalStrut(spacer));
		
		this.add(new JLabel("Current Score: "));
		cs = new JLabel("0");
		this.add(cs);
		add(Box.createHorizontalStrut(spacer));
		
		this.add(new JLabel("Sound: "));
		snd = new JLabel("ON");
		this.add(snd);
		add(Box.createHorizontalStrut(spacer));
	}
	
	private String getTime() {
		String time = "";
		String secs = "";
		
		if (ticks == 1000/Game.getTickSpeed()) {
			if (seconds == 59) {
				minutes++;
				seconds = 0;
			}
			else {
				seconds++;
			}
			ticks = 0;
		}
		else {
			ticks++;
		}
		
		if (seconds < 10) {
			secs = "0" + seconds;
		}
		else {
			secs = Integer.toString(seconds);
		}
		
		time = minutes + ":" + secs;
		return time;
	}
}
