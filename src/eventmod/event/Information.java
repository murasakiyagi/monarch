package event;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.Node;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;


import engine.QuickUtil;


//イベントから情報を得る。Hander.handle(Event){info}
public class Information {

	PickResult picre;
	Node node;
	Point2D pd;
	Point3D pd3;

	public void mouseSelect(MouseEvent me) {
		picre = me.getPickResult();
			print(picre);
			extra();
	}

	private void extra() {
		node = picre.getIntersectedNode();
		pd3 = picre.getIntersectedPoint();
		pd = new Point2D(pd3.getX(), pd3.getY());
	}
	
	public Node getNode() { return node; }
	public Point2D getPd() { return pd; }
	public Point3D getPd3() { return pd3; }

	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) { qu.print(objs); }

}