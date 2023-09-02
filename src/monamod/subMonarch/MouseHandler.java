package subMonarch;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.Camera;
import javafx.scene.Scene;

import engine.QuickUtil;
import event.HandlerFace;
import event.Information;
import monarch.GameControler;
import monarch.ChiefManager;
import unit.UnitView;
import field.FieldView;

/*
MouseEventの座標について
	getX(), getScreenX(), getSceneX()
	getSceneXはSceneの相対位置で、マウスカーソルが移動していなくてもsceneの座標が変わればマウスカーソルの相対位置は変わる。
	EventはNodeかSceneのどちらかのソースに適用される。getXは適用されたソースの相対位置
	getScreenXはウィンドウの左上を起点にした絶対位置
*/
public class MouseHandler implements HandlerFace<MouseEvent> {

	//座標
	double sx, sy, fx, fy;//start, final
	double tsx, tsy;//translate
	Camera camera;
	
	GameControler game;
	Information info;
	ChiefManager cf;
	FieldView fv;
	
	boolean isEntered = false;
	
	public MouseHandler(GameControler game, Camera camera) {
		this.game = game;
		this.camera = camera;
		this.cf = game.getCf();
		this.fv = cf.getFv();
		info = new Information();
	}

	public void handle(MouseEvent e) {//press
		reStt();//じっけん
		
		start(e);
		
		if( e.isPrimaryButtonDown() ) {
			info.mouseSelect(e);
			unitInfo();
		}
		if( e.isSecondaryButtonDown() ) {
			isEntered = !isEntered;
			print("isEntered", isEntered);
		}
	}

		private void reStt() {
			game.reStt();
		}
		
		private void unitInfo() {
			Node nd = info.getNode();
			if(reType(nd).equals("ImageView")) {
				ImageView iv = (ImageView)nd;
					print("RECOORD", fv.reCoord(iv.getX(), iv.getY()));
			}
			if(reType(nd).equals("UnitView")) {
				ImageView iv = (ImageView)nd;
				UnitView uv = (UnitView)iv;
				uv.showData();
			}
		}



	public void handle2(MouseEvent e) {
// 			print("FINISH", (int)fx);
// 			print("CAMERA TL", (int)camera.getTranslateX());
	}//release



	public void handle3(MouseEvent e) {
// 		クリックの場合ボタンダウンは絶対false
	}//click

	public void handle4(MouseEvent e) {
		cameraMove(e);
	}//dragg

	public void handle5(MouseEvent e) {
// 		NodeというかもうWindowに入った時だけ発生
// 		各Nodeの境界で発生すると思った
		if(isEntered) {
			print("handle5");
		}
	}//ENTERED
	
		private void start2(MouseEvent e) {//mousePressed
			//マウスドラッグ開始位置
			sx = e.getX();//シーン上の座標
			sy = e.getY();
			print("MOUSEPRESS  ", (int)e.getX());
		}

			private void start(MouseEvent e) {//mousePressed
				sx = e.getScreenX();//シーン上の座標
				sy = e.getScreenY();
				tsx = camera.getTranslateX();
				tsy = camera.getTranslateY();
// 				print("MOUSEPRESS  ", (int)sx);
			}

		private void finish2(MouseEvent e) {
			//scene.startFullDrag();
			fx = e.getX();
			fy = e.getY();
		}

			private void finish(MouseEvent e) {
				fx = e.getScreenX();
				fy = e.getScreenY();
			}


	private void cameraMove(MouseEvent e) {
		finish(e);
		if(e.isPrimaryButtonDown()) {//プライマリ（左）
			//double settx = camera.getTranslateX() - fx + sx;
// 			camera.setTranslateX(camera.getTranslateX() - fx + sx);
// 			camera.setTranslateY(camera.getTranslateY() - fy + sy);
			camera.setTranslateX(tsx - fx + sx);
			camera.setTranslateY(tsy - fy + sy);
// 				hud.p.setTranslateX(camera.getBoundsInParent().getMinX() + 5);
// 				hud.p.setTranslateY(camera.getBoundsInParent().getMinY() + 5);
		}
	}
		


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	
	String reType(Object obj) {//型名を調べる
		return obj.getClass().getSimpleName();
	}

}