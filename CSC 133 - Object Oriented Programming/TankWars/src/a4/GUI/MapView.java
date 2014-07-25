package a4.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.*;

import a4.Landscape.LandscapeItem;
import a4.Movable.Projectile.Projectile;
import a4.Movable.Vehicle.Tank;
import a4.Observer.IObservable;
import a4.Observer.IObserver;
import a4.Proxy.GameWorldProxy;
import a4.Game;
import a4.GameObject;
import a4.Collection.IIterator;

/**
 * A View showing the map of the world.
 * @author Cody Lanier
 *
 */
@SuppressWarnings("serial")
public class MapView extends JPanel implements IObserver, MouseListener, MouseMotionListener, MouseWheelListener {	
	private GameWorldProxy gwp;
	private AffineTransform worldToND, ndToScreen, theVTM, inverseVTM;
	private double mouseX, mouseY;
	
	public MapView(IObservable theModel) {
		theModel.addObserver(this);
		gwp = (GameWorldProxy)theModel;	
		initialize();
	}
	
	public MapView() {
		initialize();
	}
	
	private void initialize() {
		this.setLayout(new GridBagLayout());
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setBackground(Color.WHITE);
	}
	
	@Override
	public void update(IObservable o, Object arg) {	
		gwp = (GameWorldProxy)o;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		worldToND = buildWorldToND(Game.getWorldWidth(), Game.getWorldHeight(), Game.getWorldLeft(), Game.getWorldBottom());
		ndToScreen = buildNDToScreen(this.getWidth(), this.getHeight());
		theVTM = (AffineTransform) ndToScreen.clone(); 
		theVTM.concatenate(worldToND);
		g2d.transform(theVTM);
		
		
		IIterator<GameObject> gocIt = gwp.getObjsIterator();
		while (gocIt.hasNext()) {
			GameObject gObj = gocIt.getNext();
			if (gObj instanceof LandscapeItem)
			{
				gObj.draw(g2d);
			}
		}
		
		gocIt = gwp.getObjsIterator();
		while (gocIt.hasNext()) {
			GameObject gObj = gocIt.getNext();
			if (gObj instanceof Projectile && !(gObj instanceof LandscapeItem))
			{
				gObj.draw(g2d);
			}
		}
		
		gocIt = gwp.getObjsIterator();
		while (gocIt.hasNext()) {
			GameObject gObj = gocIt.getNext();
			if (!(gObj instanceof Projectile) && !(gObj instanceof LandscapeItem))
			{
				gObj.draw(g2d);
			}
		}
		
		g2d.setTransform(saveAT);
	}

	private AffineTransform buildWorldToND(double windowWidth, double windowHeight, double windowLeft, double windowBottom) {
		AffineTransform tempAT = new AffineTransform();
		tempAT.scale(1/windowWidth, 1/windowHeight);
		tempAT.translate(-windowLeft, -windowBottom);
		return tempAT;
	}
	
	private AffineTransform buildNDToScreen(double panelWidth, double panelHeight) {
		AffineTransform tempAT = new AffineTransform();
		tempAT.translate(0, panelHeight);
		tempAT.scale(panelWidth, -panelHeight);
		return tempAT;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (gwp.isPaused()) {
			Point2D mouseScreenLoc = e.getPoint();
			try {
				inverseVTM = theVTM.createInverse();
			} catch (NoninvertibleTransformException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Point2D mouseWorldLoc = inverseVTM.transform(mouseScreenLoc, null);
			
			IIterator<GameObject> gocIt = gwp.getObjsIterator();
			while (gocIt.hasNext()) {			
				GameObject gObj = gocIt.getNext();
				
				if ( gObj instanceof Tank ) {
					Tank t = (Tank) gObj;
					
					if (t.contains(mouseWorldLoc)) {
						t.setSelected(true);
					}
					else if (e.isControlDown()) {
						//do nothing
					}					
					else {
						t.setSelected(false);
					}
				}			
			}
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getPreciseWheelRotation() < 0) {
			zoomIn(e.getPoint());
		}
		else {
			zoomOut(e.getPoint());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		double deltaX = mouseX - e.getX();
		double deltaY = e.getY() - mouseY;
		
		Game.setWorldLeft(Game.getWorldLeft() + deltaX); 
		Game.setWorldRight(Game.getWorldRight() + deltaX); 
		Game.setWorldTop(Game.getWorldTop() + deltaY); 
		Game.setWorldBottom(Game.getWorldBottom() + deltaY); 
		
		mouseX = e.getX();
		mouseY = e.getY();
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void zoomIn(Point point) {
		double h = Game.getWorldHeight(); 
		double w = Game.getWorldWidth(); 
				
		Game.setWorldLeft(Game.getWorldLeft() + w*0.05); 
		Game.setWorldRight(Game.getWorldRight() - w*0.05); 
		Game.setWorldTop(Game.getWorldTop() - h*0.05); 
		Game.setWorldBottom(Game.getWorldBottom() + h*0.05);
				
		this.repaint();
	}
	
	public void zoomOut(Point point) {
		double h = Game.getWorldTop() - Game.getWorldBottom(); 
		double w = Game.getWorldRight() - Game.getWorldLeft(); 
			
		Game.setWorldLeft(Game.getWorldLeft() - w*0.05); 
		Game.setWorldRight(Game.getWorldRight() + w*0.05); 
		Game.setWorldTop(Game.getWorldTop() + h*0.05); 
		Game.setWorldBottom(Game.getWorldBottom() - h*0.05); 
				
		this.repaint();
	}
}
