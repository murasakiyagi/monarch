package monarch;

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

/*has a
GameControler

*/

//Unitの情報管理クラス

public class UnitManager {

	static Pane paneIe = new Pane();
	static Pane paneMan = new Pane();
	static Pane pane = new Pane(paneIe, paneMan);

	static List<Unit> unitList;//ユニット全部
	static List<Node> viewList;//ユニット全部
	List<Unit> dedmanList = new ArrayList<Unit>();
	static List<ImageView> manList = new ArrayList<ImageView>();
	static List<ImageView> ieList = new ArrayList<ImageView>();
	
	Map<Unit, Integer> unitMap = new HashMap<Unit, Integer>();


	//オリジナルクラス
	//Unit.classはここで生成。このクラスがメインとUnitとの仲介
	static FieldMasu fl;
	private ContextGrasp contextGrasp;
	private UnitCreater uc;


	//コンストラクタ
	UnitManager(FieldMasu fl) {
		this.fl = fl;
		unitList = new ArrayList<Unit>();
		viewList = new ArrayList<Node>();
		//this.unitA = new HumanUnit(2,1,100,1,6,fl);
		//this.unitB = new HumanUnit(2,1,100,2,7,fl);
		contextGrasp = new ContextGrasp(unitMap);
			uc = new UnitCreater(this);
	}




	//アクション。メインに乗せる
	public void action() {
		checkContext();
		issueUrgency();
		unitAction();
		//taijou();
	}

		private void unitAction() {
			for(Unit un : unitList) {
				un.action();
				if(un.hitP <= 0) { dedmanList.add(un); }
			}
		}
	
		private void taijou() {//個々の死は個々に任せる。ここではあくまで集合から外すだけ
			unitList.removeAll(dedmanList);
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
					if(unitMap.get(un) == 2) { un.warning(); }
					if(unitMap.get(un) == 1) { un.caution(); }
					unitMap.put(un, 0);
				}
			}



	//セッター-------------------------------------
	//初期設定
	public void setting() {
		zenkesi();
		Unit unitA = uc.humanCreate(100,1,6, fl);
			unitA.setName("HERO");
		Unit unitB = uc.humanCreate(200,2,8, fl);
			unitB.setName("TEKI");
	}

	public void setting2() {
		zenkesi();
		
		Unit unitA = new HumanUnit(100,1,6, fl, this);
		UnitView viewA = new UnitView(unitA);
				unitA.setName("HERO");
			unitA.initialize();
		Unit unitB = new HumanUnit(200,2,8, fl, this);
		UnitView viewB = new UnitView(unitB);
				unitB.setName("TEKI");
			unitB.initialize();

			print(unitB.getHp());

	}

	private void starting(int i, int j) {
		
	}



	private void addAbsent(List<Unit> arr, Unit newUn) {//ダブり防止。マップとかよりListがいい
		if( !arr.contains(newUn) ) {
			arr.add(newUn);
		}
	}


	//全消し
	public void zenkesi() {
		unitList.clear();
		viewList.clear();
		unitMap.clear();
	}
	
	
	
	
	//サブジェクト的な仕事。Unitにはオブザーバー的に立場になってもらう----
	public void registerUnit(Unit un) {
		unitList.add(un);
		unitMap.put(un, 0);
	}
	
	public void removeUnit(Unit un) {
		unitList.remove(un);
		unitMap.remove(un);
	}

	public void registerView(UnitView view) {
		viewList.add(view.view);
		viewList.add(view.hpTx);
		paneMan.getChildren().setAll(viewList);
	}
	
	public void removeView(UnitView view) {
		viewList.remove(view.view);
		viewList.remove(view.hpTx);
		paneMan.getChildren().removeAll();
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


//Unit配置
		public Point2D randomPd(Point2D slfPd) {//適当に座標を選ぶ。現在地とかぶる可能性あり
			int x = 0; 
			int y = 0;
			int i = 0;
			Point2D pd = new Point2D(0, 0);
			while( space(slfPd, pd.add(x, y)) ) {
				x = randomInt(fl.flRow);
				y = randomInt(fl.flCol);
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
				return (fl.zeroList.contains(tgtPd) || slfPd.equals(tgtPd));
			}







	//---------------------------------------------

	private double rnd(double d) {
		return Math.round(d*100)/100;
	}





	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	


/*
	private void print(Object obj, Object obj2, Object obj3) {
		System.out.println(print0() + obj +"  "+ obj2 +"  "+ obj3);
	}
	private void print(Object obj, Object obj2) {
		System.out.println(print0() + obj +"  "+ obj2);
	}
	private void print(Object obj) {//Overrode
		System.out.println(print0() + obj);
	}
	private String print0() {
		return "  !" + getClass().getSimpleName() + "!  ";
	}
*/
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