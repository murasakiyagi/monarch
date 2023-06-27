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


import engine.QuickUtil;


//イベントから情報を得る。Hander.handle(Event){info}
public class Information {

	PickResult picre;
	Node node;
	Point2D pd;

	public void mouseSelect(MouseEvent me) {
		picre = me.getPickResult();
			print(picre);
	}


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) { qu.print(objs); }

}