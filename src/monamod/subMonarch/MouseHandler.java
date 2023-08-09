package subMonarch;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;

import engine.QuickUtil;
import event.HandlerFace;
import event.Information;
import monarch.GameControler;
import unit.UnitView;


public class MouseHandler implements HandlerFace<MouseEvent> {

	//座標
	double sx, sy, fx, fy;//start, final
	GameControler game;
	Information info;
	
	
	public MouseHandler(GameControler game) {
		this.game = game;
		info = new Information();
	}

	public void handle(MouseEvent e) {//press
		reStt();
		//System.out.println( e );
		info.mouseSelect(e);
		unitInfo();
	}

		private void reStt() {
			game.reStt();
		}
		
		private void unitInfo() {
			Node nd = info.getNode();
			if(reType(nd).equals("UnitView")) {
				ImageView iv = (ImageView)nd;
				UnitView uv = UnitView.cast(iv);
				print();

// 				print(nd.hashCode(), iv.hashCode(), uv.hashCode());
			}
		}

	public void handle2(MouseEvent e) {}//release
	public void handle3(MouseEvent e) {}//click
	public void handle4(MouseEvent e) {}//dragg
	




	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	
	String reType(Object obj) {//型名を調べる
		return obj.getClass().getSimpleName();
	}

}