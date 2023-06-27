package monarch;

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
import pict.Img;
import state.StateFace;
import state.Work;
import task.TaskFace;


public abstract class Unit implements Cloneable {

	static public Pane pane;

	//---------------------------------------------
	//パラメータ
	protected int objNum = 1;//humanは１、houseは２
	protected String name;
	protected int imgNum;
	protected int row, col;	//マス座標であって、xy座標ではない
	protected double rowD, colD;//doubleのD
	protected double hitP;//体力
	protected int team;	//チーム。今は2チーム。heroは１、tekiは２

	protected int contNum;
	//---------------------------------------------

	//目的地
	protected Point2D tgtPd;


	//その他
	Monaserver observer;//通常List化するが、今回は一対一


	//オリジナルクラス
	public FieldMasu fl;
	protected UnitManager manager;
	protected UnitView view;
	
	protected StateFace state;
	protected Work work;
	protected TaskFace task;


	//=============================================
	//コンストラクタ
	public Unit() {}
	public Unit(double hitP, int team, int imgNum, FieldMasu fl, UnitManager manager) {
		this.hitP = hitP;
		this.fl = fl;//これはviewしか使わないので調整する
		this.team = team;
		this.imgNum = imgNum;
		work = new Work(this);
		
		this.manager = manager;
		manager.registerUnit(this);//インスタンスと同時にマネージメント契約
	}

	//=============================================
	//アクション

	public void action() {
		//state.run();
		work.working();
	}
	//-----------------------------------



		//アブストラクト--------
		abstract boolean arrival();//作業現場。到達点
		abstract public void create();
		//-------------------


		//サブジェクトメソッド実装----
		public void registerObserver(UnitView view) {//オブザーバーリストに追加
			this.view = view;
		}
		
		
		public void notifyObserver() {//変更通知
			view.update();
		}
		//--------------------

	public void initialize() {//初期化
		//registerObserver(obs);
		syokiiti();//ランダム
			tgtPd = randomPd();
		notifyObserver();
	}


	public void syokiiti() {//ゲームスタート時ランダムポジ
		//同じ場所にUnitが重ならないように
		Point2D syokiPd = randomPd();
		row = (int)syokiPd.getX();
		col = (int)syokiPd.getY();
		rowD = row;
		colD = col;
			print(row, col);
	}
	
		public Point2D randomPd() {
			return manager.randomPd(getPd());
		}



//セットポジション
	public void setPdI(int r, int c) {
		this.row = r;
		this.col = c;
	}
	
		public void setPdI(Point2D pd) {
			row = (int)Math.round(pd.getX());
			col = (int)Math.round(pd.getY());
		}

			public void setPdD(double r, double c) {
				rowD = r;
				colD = c;
				notifyObserver();
			}

				public void setPdD(Point2D pd) {
					rowD = pd.getX();
					colD = pd.getY();
				}

					public void setTgtPd(Point2D pd) {
						tgtPd = pd;
					}

//ゲットポジション
	public Point2D getPd() {
		return new Point2D(row, col);
	}

		public Point2D getPdD() {
			return new Point2D(rowD, colD);
		}
	
			public Point2D getTgtPd() {
				return tgtPd;
			}
			
	public int getRow() {
		return row;
	}
	
		public int getCol() {
			return col;
		}
		
	public boolean eqlPd(Point2D pd) {
		return getPd().equals(pd);
	}

//HP
	public void addHp(double h) {
		hitP += h;
	}
	public double getHp() {
		return hitP;
	}

//Img
	public void setImg(int newPathNum) {//画像の変更
		//setImage(img.getImg(img.allPath[ newPathNum ]));
	}

//名前
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public boolean whoName(String who) {
		boolean kari = false;
		if(name != null) { kari = this.name.equals(who); }
		return kari;
	}


//work
	public void setState(StateFace state) {
		this.state = state;
	}
	
	public Work getWork() {
		return work;
	}


	public void warning() {
		if(hitP > 10) { work.warning(); }
	}
	
	public void caution() {
		if(hitP > 10) { work.caution(); }
	}

	public void setBtlUn(Unit btlUn) {
		work.setBtlUn(btlUn);
	}

	public int teamNum() {
		return team;
	}



	//=============================================
	//継承すると子のthisが使い辛い
	public void print(Object... objs) {
		manager.qu.print(objs);
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



