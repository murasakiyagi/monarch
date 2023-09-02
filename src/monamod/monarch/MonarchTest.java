package monarch;

import java.io.*;
import java.util.*;
import java.util.function.*;
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

	import javafx.beans.value.ChangeListener;
	import javafx.beans.property.ReadOnlyDoubleProperty;
	import javafx.beans.property.DoubleProperty;


//オリジナル
import engine.QuickUtil;
import engine.Panes;
import engine.PanesSupport;
import engine.P2Dcustom;//Point2Dcustom
import engine.P2Dint;//Point2Dcustom
import pict.Img;
import unit.Unit;
import field.FieldManager;


public class MonarchTest extends Application {

	Scene scene;

	//オリジナルクラス-------------------
	//一部はbutaiSetting()にある
		SetOnMona som;
		ChiefManager chief = new ChiefManager(10, 50);
		GameControler game = new GameControler(chief);
		Panes ps = new Panes(game.getPane(), 500, 300);
		PanesSupport sup = new PanesSupMona(ps);
		Camera camera = new PerspectiveCamera();
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

		scene = new Scene(ps.getBp(), 700, 500);
// 		scene.setCamera(camera);
			ps.setSubCamera(camera);
		stage.setScene(scene);
		stage.show();

		
		som = new SetOnMona(scene, game, camera);
		
		
			//jikken==================
			ScoutTestRun strun = new ScoutTestRun();
			//strun.testRun();

			jikken();
			print("10^0 ",2^2);//^と~は比較演算子で累乗演算子では無いです
			print(Math.pow(3,2), Math.pow(2,3));
			print(Math.round(1.5), Math.round(1.4));
			crePro2();
			crePro();
// 			リードオンリーのため、バインドできません
// 			scene.widthProperty().bind(ps.getSubsn().widthProperty());
			scene.widthProperty().addListener(new ChangeLisn());
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
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
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


// 新しくdoublePropertyなどを作りたい場合
		private void crePro2() {
			print("crePro");
		}

		private void crePro() {
			double d = 0;
			DoubleProperty dp = new DoubleProperty();
			dp.setValue(d);
			ReadOnlyDoubleProperty rodp = (ReadOnlyDoubleProperty)dp.readOnlyDoubleProperty();
			print("crePro", dp, rodp);
		}

class ChangeLisn implements ChangeListener<Number> {
	@Override
	public void changed(
		ObservableValue<? extends Number> observable,
		Number oldValue,
		Number newValue
	) {

		Supplier<Double> s1 = ps.getSubsn()::getWidth;//戻り値
			print("S1", s1.get());
		double sd = s1.get();
		print("ChangeLisn OLD", oldValue, "NEW", newValue);
		ps.getSubsn().setWidth(sd + ((double)newValue - (double)oldValue));
		print("ChangeLisn OLD", s1.get());
	}
	

}


}//class,end



/*
//メモ



*/
