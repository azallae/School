package myGameEngine;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyRotationController extends Controller {
	private Vector3D dir; 
	private double rotRate;;
	
	public MyRotationController(Vector3D d, double r) {
		dir = d;
		rotRate = r;
	}
	
	@Override
	public void update(double time) {
		Matrix3D newRot = new Matrix3D();
		newRot.rotate(rotRate, dir);
		
		for(SceneNode sn : controlledNodes) {
			Matrix3D curRot = sn.getLocalRotation();
			curRot.concatenate(newRot);
			sn.setLocalRotation(curRot);
		}
	}

}
