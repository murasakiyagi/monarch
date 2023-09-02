package field;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;


import engine.QuickUtil;
import monarch.ManagerFace;

/* has a
MonarchTest
UnitManager
GameControler
*/ 

// Fieldの状態を管理
public class FieldManager implements ManagerFace {

	ManagerFace chief;
	FieldMasu fl;
	FieldView fv;
	FtcntMapping ft;
	Paneler panel;

// 	調整管理用
	static Set<Point2D> resvSet = new HashSet<Point2D>();

	public FieldManager(int x, int y, ManagerFace chief) {
		this.fl = new FieldMasu();;
// 		this.fl = new FieldMasu(y, hakosize);;
		fv = new FieldView(fl, 10, 50);
		this.ft = fl.getFt();
	}
	
	public FieldMasu getFl() { return fl; }
	public FieldView getFv() { return fv; }
	public FtcntMapping getFt() { return ft; }
	public Pane getPane() { return fv.getPane(); }
	public Map<Point2D, Integer> getFtcntMap() { return ft.getFtcntMap(); }


	public void flAction() {
		fl.action();//モデル作成
		fl.panelPrint();
		fv.masuNarabe();
		this.ft = fl.getFt();
			if(ft == null) { print("FT NULL"); }
	}
	
	public List<Point2D> zeroList() {
		return fl.zeroList;
	}


	public boolean reserve(Point2D pd) {
// 		if(!resvSet.contains(pd) || fl.walkSet.contains(pd)) {
		if(fl.walkSet.contains(pd)) {
			return resvSet.add(pd);
		} else {
			return false;
		}
	}
	
	public void removeResv(Point2D pd) {
		resvSet.remove(pd);
	}
	
	
	public Point2D randomSpace(Point2D here) {//現在地と被らないように
		reserve(here);
		int x = 0; 
		int y = 0;
		int i = 0;
		Point2D pd = new Point2D(0, 0);
		while( !reserve(pd.add(x, y)) ) {
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

	public void rsvClear() {
		resvSet.clear();
	}


	public void register(ManagerFace mf) {};
	public void remove(ManagerFace mf) {};
	public void update() {};
	public void notifyMana() {};


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}

}


 