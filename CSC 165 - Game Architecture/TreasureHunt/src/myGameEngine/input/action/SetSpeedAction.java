package myGameEngine.input.action;

import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class SetSpeedAction extends AbstractInputAction {
	private boolean running; 
	
	public boolean isRunning() { 
		return running; 
	} 
	 
	public void performAction(float time, Event event) {
		 running = !running;
	} 
}
