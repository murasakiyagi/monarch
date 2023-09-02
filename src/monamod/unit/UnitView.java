package unit;

import java.io.*;
import java.util.*;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.geometry.Rectangle2D;


//オリジナル
import engine.QuickUtil;
import pict.Img;
import field.FieldMasu;
import field.FieldView;
import field.FieldManager;
import monarch.Monaserver;
import monarch.MonaDisplay;

//monarchで使うイメージビューを統括

public class UnitView extends ImageView implements Monaserver, MonaDisplay {

	//ほぼ定数
	private double hakoW;
	private double ajustW;
	private double ajustH;

	private Unit un;

	private ImageView view;
	private Text hpTx;	//イメージに付随させる

	//画像と画像分割
	protected Rectangle2D[][] viewPos;//new Rectangle2D(0,0,32,32);

	//オリジナル
	private Img img = new Img();	//単体イメージ(viewportを使わない)
	private FieldManager fm;
	private FieldView fv;
	private UnitManager mana;

	//コンストラクタ=============
	public UnitView(Unit un) {
		this.un = un;
		un.setImgNum( teamImg(un) );//調整候補
// 		view = createView( un.getImgNum() );
		createView( un.getImgNum() );
		hpTx = new Text("" + un.getHp());

		
		un.registerObserver(this);//登録
		this.mana = un.mana;
		mana.registerView(this);

		//最小知識の原則
		fm = mana.getFm();
		fv = fm.getFv();
		this.hakoW = fv.getHakoW();

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


// 	private ImageView createView(int imgNum) {
// // 		setImage( img.getImage(imgNum) );
// // 		return this;
// 		return new ImageView( img.getImage(imgNum) );
// 	}
	private void createView(int imgNum) {
		setImage( img.getImage(imgNum) );
	}



	

	public void imgChange(int imgNum) {
		this.setImage( img.getImage(imgNum) );
	}
	

	public void hpChange(double hp) {
		int ihp = (int)hp;//処理落ち対策
		hpTx.setText("" + ihp);
	}
	
	private int teamImg(Unit un) {
// 		int team, int objNum, double hp
		if(un.getObjNum() == 1) {//家か人か？
// 			print("HUMAN IMG " ,un.getObjNum());
			if(un.getTeam() == 1) { return 6;
			} else { return 8; }
// 			return (un.getTeam() == 1) ? 6 : 8;
		} else {
// 			print("HOUSE IMG " ,un.getObjNum());
			return (un.getTeam() == 1) ? 9 : 10;
		}
	}
	

	//マスの座標値を描画系座標値に変換。row -> x。coord : 座標
	public void coordChange(double rowD, double colD) {//hakoWは一マスの横幅
// 		this.setX(fl.getKitenX() - ( rowD * hakoW/2 ) + ( colD * hakoW/2 ) + ajustW);
// 		this.setY(fl.getKitenY() + ( colD * hakoW/4 ) + ( rowD * hakoW/4 ) + ajustH);
		this.setX(fv.getKitenX() - ( rowD * hakoW/2 ) + ( colD * hakoW/2 ) + fv.getAjustW());
		this.setY(fv.getKitenY() + ( colD * hakoW/4 ) + ( rowD * hakoW/4 ) + fv.getAjustH());
		hpTx.setX( this.getX() );
		hpTx.setY( this.getY() );
	}
	
	@Override
	public void display() {
		//mainPane.getChildren().addAll(paneList);
	}


	public int getObjNum() { return un.getObjNum(); }
	public ImageView getView() { return view; }
// 	public UnitView getView() { return this; }
	public Text getTx() { return hpTx; }
	public Unit getUnit() { return un; }
	public void showData() {
		print(this.getId(), un.getRow(), un.getCol(), Math.round(un.getHp()) );
		print( un.getTgtPd(), un.getBtlUn());
	}

	
	QuickUtil qu = new QuickUtil(this);
	public void print(Object... objs) {
		qu.print(objs);
	}
	
/*
	private void paneAdd() {
		if(un.objNum == 1) { paneMan.getChildren().addAll(this, hpTx); }
		if(un.objNum == 2) { paneIe.getChildren().addAll(this, hpTx); }
	}//Paneに入れる。ゲームに参加

	private void paneRemove() {
		if(un.objNum == 1) { paneMan.getChildren().removeAll(this, hpTx); }
		if(un.objNum == 2) { paneIe.getChildren().removeAll(this, hpTx); }
	}

	private void paneZenkesi() {
		paneMan.getChildren().clear();
		paneIe.getChildren().clear();
	}
*/

	


}