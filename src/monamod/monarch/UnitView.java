package monarch;

import java.io.*;
import java.util.*;


import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.geometry.Rectangle2D;


//オリジナル
import pict.Img;



//monarchで使うイメージビューを統括

public class UnitView extends ImageView implements Monaserver, MonaDisplay {



	//ほぼ定数
	double hakoW;
	double ajustW;
	double ajustH;

	Unit un;

	ImageView view;
	Text hpTx;	//イメージに付随させる

	//画像と画像分割
	protected Rectangle2D[][] viewPos;//new Rectangle2D(0,0,32,32);

	//オリジナル
	Img img = new Img();	//単体イメージ(viewportを使わない)
	FieldMasu fl;
	UnitManager manager;

	//コンストラクタ=============
	public UnitView(Unit un) {
		this.un = un;
		view = createView( un.imgNum );
		hpTx = new Text("" + un.getHp());

		//flについては後々
		fl = un.fl;
		this.hakoW = fl.hakoW;
		this.ajustW = fl.ajustW;
		this.ajustH = fl.ajustH;
		
		un.registerObserver(this);//登録
		this.manager = un.manager;
		manager.registerView(this);
		initialize();
	}
	//=========================

	public void initialize() {
		//paneZenkesi();
		coordChange(un.rowD, un.colD);
		//paneAdd();
	}

	//オブザーバーメソッド実装
	public void update() {
		hpChange(un.hitP);
		coordChange(un.rowD, un.colD);
		imgChange(un.imgNum);
	}

	
	private ImageView createView(int imgNum) {
		return new ImageView( img.getImage(imgNum) );
	}
	

	public void imgChange(int imgNum) {
		view.setImage( img.getImage(imgNum) );
	}
	

	public void hpChange(double hp) {
		hpTx.setText("" + (int)hp);
	}
	

	//マスの座標値を描画系座標値に変換。row -> x。coord : 座標
	public void coordChange(double rowD, double colD) {//hakoWは一マスの横幅
		view.setX(fl.kitenX - ( rowD * hakoW/2 ) + ( colD * hakoW/2 ) + ajustW);
		view.setY(fl.kitenY + ( colD * hakoW/4 ) + ( rowD * hakoW/4 ) + ajustH);
		hpTx.setX( view.getX() );
		hpTx.setY( view.getY() );
	}
	
	
	public void display() {
		//mainPane.getChildren().addAll(paneList);
	}


/*
	private void paneAdd() {
		if(un.objNum == 1) { paneMan.getChildren().addAll(view, hpTx); }
		if(un.objNum == 2) { paneIe.getChildren().addAll(view, hpTx); }
	}//Paneに入れる。ゲームに参加

	private void paneRemove() {
		if(un.objNum == 1) { paneMan.getChildren().removeAll(view, hpTx); }
		if(un.objNum == 2) { paneIe.getChildren().removeAll(view, hpTx); }
	}

	private void paneZenkesi() {
		paneMan.getChildren().clear();
		paneIe.getChildren().clear();
	}
*/

	


}