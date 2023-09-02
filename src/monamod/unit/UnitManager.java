package unit;

import java.io.*;
import java.util.*;


//import javafx.animation.*;
//import javafx.application.*;
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


import engine.QuickUtil;
import monarch.ManagerFace;
import field.FieldMasu;
import field.FieldManager;
import field.Paneler;

/*has a
GameControler

*/

//Unitの情報管理クラス

public class UnitManager implements ManagerFace {

	ManagerFace chief;//型をfaceにするか、実装にするか

	static Pane paneIe = new Pane();
	static Pane paneMan = new Pane();
	static Pane paneTxt = new Pane();
	static Pane pane = new Pane(paneIe, paneMan, paneTxt);

	static List<Unit> unitList;//ユニット全部
	static List<UnitView> manViewList;//humanユニット
	static List<UnitView> ieViewList;//houseユニット
	static List<Text> txtViewList;//houseユニット
	
	List<Unit> dedmanList = new ArrayList<Unit>();
	List<UnitView> dedviewList = new ArrayList<UnitView>();
	
	static List<ImageView> manList = new ArrayList<ImageView>();
	static List<ImageView> ieList = new ArrayList<ImageView>();
	
	Map<Unit, Integer> unitMap = new HashMap<Unit, Integer>();

	private long idNum = 0;

	//オリジナルクラス
	//Unit.classはここで生成。このクラスがメインとUnitとの仲介
	static FieldMasu fl;
	static FieldManager fm;
	private ContextGrasp contextGrasp;
	private UnitCreater uc;


	//コンストラクタ
	public UnitManager(FieldManager fm, ManagerFace chief) {
		this.fm = fm;
		this.fl = fm.getFl();
		unitList = new ArrayList<Unit>();
		manViewList = new ArrayList<UnitView>();
		ieViewList = new ArrayList<UnitView>();
		txtViewList = new ArrayList<Text>();
		//this.unitA = new HumanUnit(2,1,100,1,6,fl);
		//this.unitB = new HumanUnit(2,1,100,2,7,fl);
		contextGrasp = new ContextGrasp(this);
			uc = new UnitCreater(this);
			paneTransparent();
	}


	private static void paneTransparent() {
		pane.setMouseTransparent(false);
		paneIe.setMouseTransparent(false);
		paneMan.setMouseTransparent(false);
		paneTxt.setMouseTransparent(true);
	}

	//アクション。メインに乗せる
	public void action() {
		checkContext();
		issueUrgency();
		unitAction();
			taijou();
		
			fm.rsvClear();//仮置き
		//taijou();
			time();

	}

		private void unitAction() {
// 			拡張forだとaction()時に増殖するのでExcepsion発生する
// 			今ターン増殖分は何もせず次回からアクション
			int size = unitList.size();
			Unit un;
			for(int i=0; i < size; i++) {
				un = unitList.get(i);
				un.action();
// 				dedmanAdd()は使ってない
				if(un.hitP <= 0) { dedmanList.add(un); }
			}
		}
	


		private void checkContext() {
			//チェックだけなのでイテレータは使わない
			for(Unit un : unitList) {
				for(Unit un2 : unitList) {
					contextGrasp.run(un, un2);
				}
			}
		}

			static int cntiss = 0;
			private void issueUrgency() {//警告を発する
				for(Unit un : unitMap.keySet()) {
					if(unitMap.get(un) == 3) { un.join(); }
					if(unitMap.get(un) == 2) { un.warning(); }
					if(unitMap.get(un) == 1) { un.caution(); }
					unitMap.put(un, 0);
				}
			}



	//セッター-------------------------------------
	//初期設定
	public void setting() {
		zenkesi();
		Unit unitA = uc.humanCreate(200,1,6);
			unitA.setName("HERO");
			unitA.origInit();
		Unit unitB = uc.humanCreate(300,2,8);
			unitB.setName("TEKI");
		Unit houseA = uc.houseCreate(100,1,9);
		fm.rsvClear();
	}


	private void addAbsent(List<Unit> arr, Unit newUn) {//ダブり防止。マップとかよりListがいい
		if( !arr.contains(newUn) ) {
			arr.add(newUn);
		}
	}


	//全消し
	public void zenkesi() {
// 			clearCheck();
		unitList.clear();
		manViewList.clear();
		ieViewList.clear();
		txtViewList.clear();
		unitMap.clear();
		idNum = 0;
	}

// 		テスト-------------------------
		private void clearCheck() {
			print(
				unitList.size(), manViewList.size(), ieViewList.size(), unitMap.size(),
				dedmanList.size(), dedviewList.size(), pane.getChildren().size(),
				paneMan.getChildren().size(), paneIe.getChildren().size()
			);
		}
		
		public void debug() {//use a gemeControler
			for(int i = 0; i < unitList.size() / 2 ; i++) {
				unitList.get(i).addHp(-1000);
			}
		}
		
		public boolean debug = false;
		long now;
		long past;//過去
		private void time() {
			if(debug) {
				now = System.currentTimeMillis();
				print(now - past);
				past = now;
			}
		}
		
// 		-----------------------

	//サブジェクト的な仕事。Unitにはオブザーバー的に立場になってもらう----
	private void taijou() {//個々の死は個々に任せる。viewはunと連動（un,viewクラス側で処理）
		for(Unit un : dedmanList) {
			un.taijou();
				Paneler.resetUnit(un.getPd(), un.getObjNum());
		}
		dedmanList.clear();
	}
	
		private void dedmanAdd(Unit un) {//使ってない
			UnitView view = un.getView();
			dedmanList.add(un);
// 			dedviewList.add(view.getView());
// 			dedviewList.add(view.getTx());
		}

	public void registerUnit(Unit un) {
		unitList.add(un);
		unitMap.put(un, 0);
	}
	
	public void removeUnit(Unit un) {
		unitList.remove(un);
		unitMap.remove(un);
	}

	public void registerView(UnitView view) {
		view.setId("" + idNum++);
		if(view.getObjNum() == 1) {
			manViewList.add(view);
// 			manViewList.add(view.getTx());
			paneMan.getChildren().setAll(manViewList);
		}
		
		if(view.getObjNum() == 2) {
			ieViewList.add(view);
// 			ieViewList.add(view.getTx());
			paneIe.getChildren().setAll(ieViewList);
		}
		
		txtViewList.add(view.getTx());
		paneTxt.getChildren().setAll(txtViewList);
	}
	
	public void removeView(UnitView view) {
		if(view.getObjNum() == 1) {
			manViewList.remove(view);
// 			manViewList.remove(view.getTx());
			paneMan.getChildren().setAll(manViewList);
		}
		
		if(view.getObjNum() == 2) {
			ieViewList.remove(view);
// 			ieViewList.remove(view.getTx());
			paneIe.getChildren().setAll(ieViewList);
		}
		
		txtViewList.remove(view.getTx());
		paneTxt.getChildren().setAll(txtViewList);
	}


	//----------------------------------------------



	//退場関連は必ず後々問題が生じるので参考に置いておく。
	private void taijou2() {
		Iterator<Unit> itera = unitList.iterator();
		while(itera.hasNext()) {
			Unit un = itera.next();
			if(un.hitP <= 0) {
				unitList.remove(un);
			}
		}
	}


//Unit配置unit.syokiiti()
		public Point2D randomPd(Point2D here) {
			return fm.randomSpace(here);
		}
		public Point2D randomPd2(Point2D slfPd) {//適当に座標を選ぶ。現在地とかぶる可能性あり
			int x = 0; 
			int y = 0;
			int i = 0;
			Point2D pd = new Point2D(0, 0);
			while( space(slfPd, pd.add(x, y)) ) {
				x = randomInt(fl.getRow());
				y = randomInt(fl.getCol());
				if(i++ >= 1000) {
					print("SYOKIITI  ERROR");
					break;
				}
			}
			return pd.add(x, y);
		}
	
			public int randomInt(int place) {//place:桁
				return (int)(Math.random() * place);
			}
			
			private boolean space(Point2D slfPd, Point2D tgtPd) {//ゼロマスと現在地を避ける
// 				if(fm.reserve(tgtPd)) { return }
				return (fl.zeroList.contains(tgtPd) || slfPd.equals(tgtPd));
			}







	//---------------------------------------------

	private double rnd(double d) {
		return Math.round(d*100)/100;
	}

// -------------------------------
// ゲッター
	public Pane getPane() { return pane; }
	public FieldManager getFm() { return fm; }
	public Map<Unit, Integer> getUnitMap() { return unitMap; }
	public List<UnitView> getManViewList() { return manViewList; }
	public List<UnitView> getIeViewListSize() { return ieViewList; }



	@Override
	public void register(ManagerFace mf) {};
	public void remove(ManagerFace mf) {};
	public void update() {};
	public void notifyMana() {};




	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	


	String reType(Object obj) {//型名を調べる
		return obj.getClass().getSimpleName();
	}



}//class,end


/*
		unitA = new HumanUnit(100,1,6, fl);
		unitB = new HumanUnit(100,2,7, fl);
		viewA = new UnitView(unitA);
		viewB = new UnitView(unitB);
			unitA.initialize(100, viewA);
			unitB.initialize(120, viewB);
			viewA.initialize();
			viewB.initialize();

		addAbsent(unitList, unitA);
		addAbsent(unitList, unitB);
		pane.getChildren().add(viewA.pane);
*/