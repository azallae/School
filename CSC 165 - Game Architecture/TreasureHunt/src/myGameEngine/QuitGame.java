package myGameEngine;
import sage.input.action.AbstractInputAction; 
import sage.app.AbstractGame; 
import net.java.games.input.Event; 
 
public class QuitGame extends AbstractInputAction 
{ 
 private AbstractGame game; 
 
 public QuitGame(AbstractGame g) // constructor 
 { this.game = g; } 
 
 // Sets the "game over" flag in the game associated with this 
 // IAction to true. The time and event parameters are ignored. 
 
 public void performAction(float time, Event event) 
 { game.setGameOver(true); } 
} 
