package games.treasurehunt2014.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import sage.event.IEventListener;
import sage.event.IGameEvent;
import sage.scene.TriMesh;

public class TreasureBox extends TriMesh implements IEventListener { 
	FloatBuffer colorBuffer;	
	private static float[] vrts = new float[] {
		-1,0,2,//0
		2,0,-1,//1
		1,0,-2,//2
		-2,0,1,//3
		-2,2,1,//4
		1,2,-2,//5
		2,2,-1,//6
		-1,2,2,//7
		1,2,-2,//8
		2,2,-1,//9
		-2,2,1,//10
		-1,2,2,//11
		-1,2.5f,1.5f,//12
		1.5f,2.5f,-1,//13
		1,2.5f,-1.5f,//14
		-1.5f,2.5f,1f,//15
	}; 
	private static float[] cl = new float[] {
		1,0.5f,0,1,//0
		1,0.5f,0,1,//1
		1,0.5f,0,1,//2
		1,0.5f,0,1,//3
		1,1,0,1,//4
		1,1,0,1,//5
		1,1,0,1,//6
		1,1,0,1,//7
		0.8f,0.7f,0.3f,1,//8
		0.8f,0.7f,0.3f,1,//9
		0.8f,0.7f,0.3f,1,//10
		0.8f,0.7f,0.3f,1,//11
		1,0.5f,0,1,//12
		1,0.5f,0,1,//13
		1,0.5f,0,1,//14
		1,0.5f,0,1,//15
	};
	private static int[] triangles = new int[] {
		0,1,2,//1
		0,2,3,//2
		2,3,5,//3
		3,4,5,//4
		0,3,10,//5
		0,10,11,//6
		1,2,9,//7
		2,8,9,//8
		0,1,7,//9
		1,6,7,//10
		12,13,14,//11
		12,14,15,//12
		7,12,15,//13
		4,7,15,//14
		6,13,14,//15
		5,6,14,//16
		11,12,13,//17
		9,11,13,//18
		10,14,15,//19
		8,10,14//20
	}; 

	public TreasureBox() { 
		FloatBuffer vertBuf =  com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrts); 
		colorBuffer =  com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl); 
		IntBuffer triangleBuf = com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles); 
		this.setVertexBuffer(vertBuf); 
		this.setColorBuffer(colorBuffer); 
		this.setIndexBuffer(triangleBuf); 
	}
	 
	public boolean handleEvent(IGameEvent event) { 
		//if the event has programmer-defined information in it,
		//it must be cast to the programmer-defined event type. 
		scale(1.5f, 1.5f, 1.5f); 
		updateLocalBound();
		return true; 
	} 
}