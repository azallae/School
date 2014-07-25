package a4.Selection;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public interface ISelectable {
	public void setSelected(boolean yesNO);
	public boolean isSelected();
	public boolean contains(Point2D p);
	public void draw(Graphics2D g2d);
	public void drawHighlighted(Graphics2D g2d);
	public void drawNormal(Graphics2D g2d);
}
