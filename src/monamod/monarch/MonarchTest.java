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


public class MonarchTest extends Application {

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
	//boolean btn = Mcon.btn;
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


		SetOnMona som = new SetOnMona(scene, game);
		
		
			//jikken==================
			ScoutTestRun strun = new ScoutTestRun();
			//strun.testRun();

			jikken();
			print("10^0 ",2^2);//^と~は比較演算子で累乗演算子では無いです
			print(Math.pow(3,2), Math.pow(2,3));
			print(Math.round(1.5), Math.round(1.4));
			//jikken.end===============



		//ゲームループの起動。必須
		new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				gameLoop();
			}
		}.start();

	}//start,end=================================================================
	
		//jikken.method()
		private void jikken() {

		}
		

	//ゲームループ。必要-----------------------
	private void gameLoop() {
		if(Mcon.btn) {
			game.gaming();
		}
	}//gameloop,end------------------------

	//gameloopIN〜〜〜〜〜〜〜〜〜〜〜〜〜〜
		private void action() {

		}
	//〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜〜




	
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
