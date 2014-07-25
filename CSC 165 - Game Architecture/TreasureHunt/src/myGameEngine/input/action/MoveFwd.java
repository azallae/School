package myGameEngine.input.action;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.input.action.IAction;
import sage.scene.SceneNode;

public class MoveFwd implements IAction {
	private SceneNode p; 
	private float speed = 0.1f;
	
	public MoveFwd(SceneNode p) { 
		this.p = p; 
	} 
 
	public void performAction(float time, net.java.games.input.Event e) {
		Matrix3D rot = p.getLocalRotation(); 
		Vector3D dir = new Vector3D(0,0,1); 
		dir = dir.mult(rot); 
		dir.scale((double)(speed * time)); 
		p.translate((float)dir.getX(),(float)dir.getY(),(float)dir.getZ());
	}
}
