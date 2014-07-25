package games.treasurehunt2014;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import myGameEngine.*;
import myGameEngine.input.action.*;
import games.treasurehunt2014.objects.*;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.camera.JOGLCamera;
import sage.display.IDisplaySystem;
import sage.event.EventManager;
import sage.event.IEventManager;
import sage.input.IInputManager;
import sage.input.InputManager;
import sage.input.action.IAction;
import sage.renderer.IRenderer;
import sage.scene.Group;
import sage.scene.HUDString;
import sage.scene.SceneNode;
import sage.scene.shape.Cylinder;
import sage.scene.shape.Line;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Sphere;

public class TreasureHunt extends BaseGame {
	IRenderer renderer;
	IDisplaySystem display;
	ICamera camera1, camera2; 
	Camera3Pcontroller cc1, cc2;
	IInputManager im; 
	IEventManager eventMgr; 
	String gpName, kbName;
	SceneNode p1, p2;
	Rectangle ground;
	TreasureBox aTBox;
	Coin[] coins;
	Pearl[] pearls;
	Group atom;
	private HUDString scoreString1, scoreString2, player1ID, player2ID;
	
	int numCrashes = 0; 
	int score1 = 0;
	int score2 = 0;
	int time = 0;
	
	protected void initSystem() { 
		//call a local method to create a DisplaySystem object 
		IDisplaySystem display = createDisplaySystem(); 
		setDisplaySystem(display); 
		//create an Input Manager 
		IInputManager inputManager = new InputManager(); 
		setInputManager(inputManager); 
		//create an (empty) gameworld 
		ArrayList<SceneNode> gameWorld = new ArrayList<SceneNode>(); 
		setGameWorld(gameWorld); 
	}
	
	protected void initGame() { 
		eventMgr = EventManager.getInstance();
		display = getDisplaySystem();
		renderer = display.getRenderer();
		im = getInputManager(); 
		gpName = im.getFirstGamepadName();
		kbName = im.getKeyboardName(); 
		
		initGameObjects();
		initAxes();
		initActions();	
		
		display.setTitle("Treasure Hunt 2014");
}
	
	private void initAxes() {
		Point3D origin = new Point3D(0,0,0); 
		Point3D xEnd = new Point3D(100,0,0); 
		Point3D yEnd = new Point3D(0,100,0); 
		Point3D zEnd = new Point3D(0,0,100); 
		
		Line xAxis = new Line(origin, xEnd, Color.red, 2); 
		Line yAxis = new Line(origin, yEnd, Color.green, 2); 
		Line zAxis = new Line(origin, zEnd, Color.blue, 2); 
		
		addGameWorldObject(xAxis); 
		addGameWorldObject(yAxis); 
		addGameWorldObject(zAxis);
	}
	
	private void initActions() {
		//player 1 actions
		IAction moveFB1 = new MoveFB(p1);
		IAction moveLR1 = new MoveLR(p1);
		im.associateAction(gpName, net.java.games.input.Component.Identifier.Axis.Y, moveFB1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(gpName, net.java.games.input.Component.Identifier.Axis.X, moveLR1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//player 2 actions
		IAction moveFwd2 = new MoveFwd(p2);
		IAction moveBack2 = new MoveBack(p2);
		IAction moveLeft2 = new MoveLeft(p2);
		IAction moveRight2 = new MoveRight(p2);
		IAction quitGame = new QuitGame(this);
		im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.W, moveFwd2, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.S, moveBack2, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.A, moveLeft2, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.D, moveRight2, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.ESCAPE, quitGame, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	}

	private void initGameObjects() { 
		createPlayers();
		createPlayerHUDs();
		
		cc1 = new Camera3Pcontroller(camera1, p1, im, gpName);
		cc2 = new Camera3Pcontroller(camera2, p2, im, kbName);
		
		ground = new Rectangle();
		aTBox = new TreasureBox();
		coins = new Coin[10];
		pearls = new Pearl[10];
				
		Matrix3D groundM = ground.getLocalRotation(); 
		groundM.rotate(90, new Vector3D(1,0,0)); 		
		ground.setLocalRotation(groundM); 		
		groundM = ground.getLocalScale(); 		
		groundM.scale(1000,1000,1); 		
		ground.setLocalScale(groundM); 
		ground.setColor(Color.DARK_GRAY);
		addGameWorldObject(ground); 
		
		Matrix3D tBoxM = aTBox.getLocalTranslation(); 
		tBoxM.translate(0,0.1,0); 
		aTBox.setLocalTranslation(tBoxM); 
		addGameWorldObject(aTBox); 
		
		Random r = new Random();
		for (int i = 0; i < coins.length; i++) {
			coins[i] = new Coin(r.nextInt(3));
			if (coins[i].getValue() > 0) {
				Matrix3D coinM = coins[i].getLocalTranslation(); 
				coinM.translate(r.nextInt(200)-100, 0+coins[i].getRadius(), r.nextInt(200)-100); 
				coins[i].setLocalTranslation(coinM); 
				addGameWorldObject(coins[i]); 
				coins[i].updateWorldBound();
			}
			else {
				coins[i] = null;
			}
		}
		
		for (int i = 0; i < pearls.length; i++) {
			pearls[i] = new Pearl(r.nextInt(3));
			if (pearls[i].getValue() > 0) {
				Matrix3D pearlM = pearls[i].getLocalTranslation(); 
				pearlM.translate(r.nextInt(200)-100, 0+pearls[i].getRadius(), r.nextInt(200)-100); 
				pearls[i].setLocalTranslation(pearlM); 
				addGameWorldObject(pearls[i]); 
				pearls[i].updateWorldBound();
			}
			else {
				pearls[i] = null;
			}
		}
		
		Sphere nucleus = new Sphere(1.5f, 50, 50, Color.ORANGE);
		Cylinder arc1 = new Cylinder(0.2f,4,40,1);
		Cylinder arc2 = new Cylinder(0.2f,4,40,1);
		Cylinder arc3 = new Cylinder(0.2f,4,40,1);
		arc1.setColor(Color.CYAN);
		arc2.setColor(Color.YELLOW);
		arc3.setColor(Color.PINK);
		
		atom = new Group();
		Group arcs = new Group();
						
		atom.addChild(nucleus);
		atom.addChild(arcs);
		arcs.addChild(arc1);
		arcs.addChild(arc2);
		arcs.addChild(arc3);
		
		atom.setIsTransformSpaceParent(true); 
		arcs.setIsTransformSpaceParent(true); 				
		
		MyRotationController arc1Rotate = new MyRotationController(new Vector3D(0.5,2,1.2), 1.2);
		MyRotationController arc2Rotate = new MyRotationController(new Vector3D(1,1.5,0.7), 1.8);
		MyRotationController arc3Rotate = new MyRotationController(new Vector3D(1.4,1.7,0.9), 0.8);
		
		MyTransController mtc = new MyTransController(); 
		mtc.addControlledNode(atom); 
		atom.addController(mtc);
		
		arc1Rotate.addControlledNode(arc1);
		arc1.addController(arc1Rotate);
		arc2Rotate.addControlledNode(arc2);
		arc2.addController(arc2Rotate);
		arc3Rotate.addControlledNode(arc3);
		arc3.addController(arc3Rotate);
		
		atom.translate(r.nextInt(200)-100, 4, r.nextInt(200)-100);
		arc1.rotate(45, new Vector3D(1,0,0));
		arc2.rotate(-45, new Vector3D(1,0,0));
						
		addGameWorldObject(atom);
		
		super.update(0);
		eventMgr.addListener(aTBox, CrashEvent.class); 
	} 
	
	private void createPlayers() {
		p1 = new Sphere(2, 20, 25, Color.MAGENTA);
		Matrix3D p1M = p1.getLocalTranslation(); 
		p1.translate(0,(float)((Sphere)p1).getRadius(),50); 
		p1.setLocalTranslation(p1M); 
		addGameWorldObject(p1); 
		camera1 = new JOGLCamera(renderer); 
		camera1.setPerspectiveFrustum(60, 2, 1, 1000); 
		camera1.setViewport(0.0,  1.0,  0.55,  1.0);
				
		p2 = new Sphere(2, 20, 25, Color.CYAN);
		Matrix3D p2M = p2.getLocalTranslation(); 
		p2.translate(50,(float)((Sphere)p1).getRadius(),0); 
		p2.setLocalTranslation(p2M); 
		addGameWorldObject(p2); 
		camera2 = new JOGLCamera(renderer); 
		camera2.setPerspectiveFrustum(60, 2, 1, 1000); 
		camera2.setViewport(0.0,  1.0,  0.0,  0.45);
	}

	private void createPlayerHUDs() { 
		player1ID = new HUDString("Player1"); 
		player1ID.setName("Player1ID"); 
		player1ID.setLocation(0.01, 0.10); 
		player1ID.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO); 
		player1ID.setColor(Color.red); 
		player1ID.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER); 
		scoreString1 = new HUDString ("Score = " + score1); 
		scoreString1.setLocation(0.01, 0.06); 
		camera1.addToHUD(player1ID); 
		camera1.addToHUD(scoreString1);
		
		player2ID = new HUDString("Player2"); 
		player2ID.setName("Player2ID"); 
		player2ID.setLocation(0.01, 0.10); 
		player2ID.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO); 
		player2ID.setColor(Color.yellow); 
		player2ID.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER); 
		scoreString2 = new HUDString ("Score = " + score2); 
		scoreString2.setColor(Color.yellow);
		scoreString2.setLocation(0.01, 0.06);
		camera2.addToHUD(player2ID); 
		camera2.addToHUD(scoreString2); 
	}

	 protected void render() { 
		 renderer.setCamera(camera1); 
		 super.render(); 
		 renderer.setCamera(camera2); 
		 super.render(); 
	 } 

	
	public void update(float elapsedTimeMS) {
		cc1.update(elapsedTimeMS);
		cc2.update(elapsedTimeMS);		
		
		for (int i = 0; i < coins.length; i++) {
			if(coins[i] != null) {
				coins[i].update(elapsedTimeMS);
				if (coins[i].getWorldBound().intersects(p1.getWorldBound())) {
					score1 += coins[i].getValue();
					removeGameWorldObject(coins[i]);
					coins[i] = null;
					scoreString1.setText("Score = " + score1);
					numCrashes++; 
					CrashEvent newCrash = new CrashEvent(numCrashes); 
					eventMgr.triggerEvent(newCrash); 
				} 
				else if (coins[i].getWorldBound().intersects(p2.getWorldBound())) {
					score2 += coins[i].getValue();
					removeGameWorldObject(coins[i]);
					coins[i] = null;
					scoreString2.setText("Score = " + score2);
					numCrashes++; 
					CrashEvent newCrash = new CrashEvent(numCrashes); 
					eventMgr.triggerEvent(newCrash); 
				} 
			}
		}
		
		for (int i = 0; i < pearls.length; i++) {
			if(pearls[i] != null) {
				if (pearls[i].getWorldBound().intersects(p1.getWorldBound())) {
					score1 += pearls[i].getValue();
					removeGameWorldObject(pearls[i]);
					pearls[i] = null;
					scoreString1.setText("Score = " + score1);
					numCrashes++; 
					CrashEvent newCrash = new CrashEvent(numCrashes); 
					eventMgr.triggerEvent(newCrash);
				} 
				else if (pearls[i].getWorldBound().intersects(p2.getWorldBound())) {
					score2 += pearls[i].getValue();
					removeGameWorldObject(pearls[i]);
					pearls[i] = null;
					scoreString2.setText("Score = " + score2);
					numCrashes++; 
					CrashEvent newCrash = new CrashEvent(numCrashes); 
					eventMgr.triggerEvent(newCrash);
				} 
			}
		}
		
		if (!atom.isShowBound()) {
			if (atom.getWorldBound().contains(cc1.getTargetPosition())) {
				atom.setShowBound(true);
				removeGameWorldObject(atom);
				score1 += 10;
				scoreString1.setText("Score = " + score1);
				numCrashes++; 
				CrashEvent newCrash = new CrashEvent(numCrashes); 
				eventMgr.triggerEvent(newCrash);
			}
			else if (atom.getWorldBound().contains(cc2.getTargetPosition())) {
				atom.setShowBound(true);
				removeGameWorldObject(atom);
				score2 += 10;
				scoreString2.setText("Score = " + score2);
				numCrashes++; 
				CrashEvent newCrash = new CrashEvent(numCrashes); 
				eventMgr.triggerEvent(newCrash);
			}
		}
		
		
		scoreString1.setText("Score = " + score1); 
		scoreString2.setText("Score = " + score2); 
		time += elapsedTimeMS; 
		DecimalFormat df = new DecimalFormat("0.0"); 
		player1ID.setText("Time = " + df.format(time/1000)); 
		player2ID.setText("Time = " + df.format(time/1000));

		super.update(elapsedTimeMS); 
	}
	
	private IDisplaySystem createDisplaySystem() { 
		IDisplaySystem display = new MyDisplaySystem(700, 300, 24, 20, true, "sage.renderer.jogl.JOGLRenderer"); 
		System.out.print("\nWaiting for display creation..."); 
		int count = 0; 
		
		// wait until display creation completes or a timeout occurs 
		while (!display.isCreated()) { 
			try 
			{ Thread.sleep(10); } 
			catch (InterruptedException e) 
			{ throw new RuntimeException("Display creation interrupted"); } 
		
			count++; 
			System.out.print("+"); 
			if (count % 80 == 0) { System.out.println(); } 
		
			if (count > 2000) // 20 seconds (approx.) 
			{ throw new RuntimeException("Unable to create display"); } 
		} 
		System.out.println(); 
		return display ; 
	} 
		
	protected void shutdown() { 
		display.close(); 
		//...other shutdown methods here as necessary... 
	}
}
