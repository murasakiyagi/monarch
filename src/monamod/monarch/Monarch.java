package monarch;

import java.io.*;
import java.util.*;
//import java.awt.Font;


import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.util.*;
import javafx.beans.value.*;
import javafx.beans.property.*;
import javafx.beans.binding.*;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.*;
import javafx.scene.control.skin.*;

//オリジナル
import pict.Img;
import engine.P2Dcustom;//Point2Dcustom
import engine.P2Dint;//Point2Dcustom
import monarch.Unit;


public class Monarch extends Application {

	static Pane p = new Pane();
	Scene scene;


	//オリジナルクラス-------------------
	//一部はbutaiSetting()にある

		Img img = new Img();
		
		//マスセット
		FieldMasu fl = new FieldMasu(img.getHakoUrls(), 350, 50, 64.0); //引数（箱の種類数, 起点XY, 箱のサイズ）

		GameControler game = new GameControler(fl, p);

	//-----------------------------------

	//実験用


	//=======================================================
	//メインメソッド

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {

		scene = new Scene(p, 700, 500);
		stage.setScene(scene);
		stage.show();


			img.getUrl();

			Point2D pd1 = new Point2D(1,1);
			Point2D pd2 = new Point2D(2,2);
			Point2D pd3 = new Point2D(1,2);
			print("DISTANCE 1  ", pd1.distance(pd2));//マイナスは付かない
			print("DISTANCE 2  ", pd3.distance(pd1));
			String str = "a";
			str += "b";
			print(str);



		//butaiSetting();
		//unitSetting();
		

		SetOnMona som = new SetOnMona(scene, game);
		
			//jikken==================
			ScoutTestRun strun = new ScoutTestRun();
			//strun.testRun();
			//jikken.end===============

		//ゲームループの起動。必須
		new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				gameLoop();
			}
		}.start();

	}//start,end=================================================================
	
		

	//ゲームループ。必要
	private void gameLoop() {
		game.gaming();
	}//gameloop,end

	//gameloopIN〜〜〜〜〜〜〜〜〜〜〜〜〜〜
		private void action() {

		}
	//〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜




	private void butaiSetting() {

		//マスセット
		fl.action();
		fl.panelPrint();
		p.getChildren().add(fl.p);

	}




	
	//------------------------------------------
	private void print(Object... objs) {
		System.out.print(print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print(objs[i]);
		}
		System.out.println();
	}
	private String print0() {
		return "  !" + getClass().getSimpleName() + "!  ";
	}

	String reType(Object obj) {//型名を調べる
		return obj.getClass().getSimpleName();
	}
	
	private void printArray(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}
	
	

	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	private double rounder(double d, int keta) {//keta = 10^x
		return (double)Math.round(d*keta)/keta;
	}




}//class,end




/*
//メモ



*/
