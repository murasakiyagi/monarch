package unit;

import java.io.*;
import java.util.*;


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

//オリジナル
import engine.QuickUtil;
import pict.Img;
import state.StateFace;
import state.Work;
import task.TaskFace;
import monarch.Monaserver;
import monarch.FieldMasu;


public abstract class Unit implements Cloneable {

	static public Pane pane;

	//---------------------------------------------
	//パラメータ
	protected int objNum;//humanは１、houseは２
	protected String name;
	protected int imgNum;
	protected int row, col;	//マス座標であって、xy座標ではない
	protected double rowD, colD;//doubleのD
	protected double hitP;//体力
	protected int team;	//チーム。今は2チーム。heroは１、tekiは２
	protected char houkou;
	//---------------------------------------------

	//目的地
	protected Point2D tgtPd;


	//その他
	Monaserver observer;//通常List化するが、今回は一対一


	//オリジナルクラス
	public FieldMasu fl;
	protected UnitManager mana;
	protected UnitView view;
	
	protected StateFace state;
	protected Work work;
	protected TaskFace task;


	//=============================================
	//コンストラクタ
	public Unit() {}
	public Unit(double hitP, int team, int imgNum, int objNum, UnitManager mana) {
		this.hitP = hitP;
		this.team = team;
		this.imgNum = imgNum;
		this.objNum = objNum;
		work = new Work(this);
		this.mana = mana;
		mana.registerUnit(this);//インスタンスと同時にマネージメント契約
	}

	//=============================================
	//アクション

	public void action() {
		work.working();
	}
	//-----------------------------------


// 		アブストラクト--------
		abstract boolean arrival();//作業現場。到達点
		abstract public void create();
// 		-------------------


// 		サブジェクトメソッド実装
// 		UnitViewコンストラクタで使用
		public void registerObserver(UnitView view) {//オブザーバーリストに追加
			this.view = view;
		}
		
// 		変更通知
		public void notifyObserver() { view.update(); }
		public void notifyWork() { work.update(); }
// 		--------------------
// 	最初のUnit
	public void origInit() {
		notifyWork();
	}

	public void initialize() {//初期化
		//registerObserver(obs);
		syokiiti();//ランダム
			tgtPd = randomPd();
		notifyObserver();
	}

		public void taijou() {
			mana.removeUnit(this);
			mana.removeView(view);
		}


	public void syokiiti() {//ゲームスタート時ランダムポジ
		//同じ場所にUnitが重ならないように
		Point2D syokiPd = randomPd();
		row = (int)syokiPd.getX();
		col = (int)syokiPd.getY();
		rowD = row;
		colD = col;
	}
	
		public Point2D randomPd() {
			return mana.randomPd(getPd());
		}



//セットポジション
	public void setPdP(int r, int c) {//パーフェクトのP
		setPdI(r, c);
		setPdD(r, c);
	}
	public void setPdP(Point2D pd) {//パーフェクトのP
		setPdI(pd);
		setPdD(pd);
	}
	
	public void setPdI(int r, int c) {
		this.row = r;
		this.col = c;
	}
	
		public void setPdI(Point2D pd) {
			row = (int)Math.round(pd.getX());
			col = (int)Math.round(pd.getY());
		}

			//viewに関係するのはrowD,colDだけ
			public void setPdD(double r, double c) {
				rowD = r;
				colD = c;
				notifyObserver();
			}

				public void setPdD(Point2D pd) {
					rowD = pd.getX();
					colD = pd.getY();
					notifyObserver();
				}

					public void setTgtPd(Point2D pd) {
						tgtPd = pd;
					}

//ゲットポジション
	public Point2D getPd() { return new Point2D(row, col); }
	public Point2D getPdD() { return new Point2D(rowD, colD); }
	public Point2D getTgtPd() { return tgtPd; }
			
	public int getRow() { return row; }
	public int getCol() { return col; }
		
	public boolean eqlPd(Point2D pd) { return getPd().equals(pd); }
	
	
// 方向
	public void setHoukou(char h) { houkou = h; }
	public char getHoukou() { return houkou; }



//HP
	public void addHp(double h) {
		hitP += h;
		notifyObserver();
// 		if(hitP <= 0) { notifyWork(); }
	}
	public double getHp() { return hitP; }

//Img
	public void setImgNum(int num) { imgNum = num; }
	public int getImgNum() { return imgNum; }
	public UnitView getView() { return this.view; }
// 	public void setImg(int newPathNum) {//画像の変更
// 		//setImage(img.getImg(img.allPath[ newPathNum ]));
// 	}

//名前
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public boolean whoName(String who) {
		boolean kari = false;
		if(name != null) { kari = this.name.equals(who); }
		return kari;
	}

	public int getObjNum() { return objNum; }
	public int getTeam() { return team; }

//work
	public void setState(StateFace state) { this.state = state; }
	public Work getWork() { return work; }

	public void setBtlUn(Unit btlUn) { work.setBtlUn(btlUn); }

	public void warning() {
		if(hitP > 10) { work.warning(); }
	}

	public void caution() {
		if(hitP > 10) { work.caution(); }
	}

	public void join() { work.join(); }


// =====================

	//=============================================
	//継承すると子のthisが使い辛い
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	
/*
	private void print(Object... objs) {
		System.out.print(print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print("  " + objs[i]);
		}
		System.out.println();
	}
	private String print0() {
		return "  !" + getClass().getSimpleName() + "!  ";
	}
*/
	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}


}//class,end



