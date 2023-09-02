package monarch;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

import engine.QuickUtil;
import unit.UnitManager;
import field.FieldManager;
import field.FieldMasu;
import field.FieldView;
import field.FtcntMapping;
import state.StateManager;
import task.TaskManager;

public class ChiefManager implements ManagerFace {

// 	上級マネージャー
	UnitManager um;
	FieldManager fm;
	
// 	下級。上級から情報をもらうだけ
	StateManager sm;//stateとtaskは一意ではない
	TaskManager tm;

	List<ManagerFace> manaList = new ArrayList<ManagerFace>();


	Map<Point2D, Integer> ftcntMap;
	HashSet<Point2D> walkSet;
	
	public ChiefManager(int x, int y) {
		fm = new FieldManager(x, y, this);
		um = new UnitManager(fm, this);

	}
	
	
// 	ゲッター
	public FieldManager getFm() { return fm; }
	public UnitManager getUm() { return um; }

	public FieldView getFv() { return fm.getFv(); }
	public FieldMasu getFl() { return fm.getFl(); }
	public FtcntMapping getFt() { return fm.getFt(); }


// 	オブザーバ
	@Override
	public void register(ManagerFace mf) {
		manaList.add(mf);
	}
	
	@Override
	public void remove(ManagerFace mf) {
		manaList.remove(mf);
	}
	
	@Override
	public void update() {}
	
	@Override
	public void notifyMana() {
		for(ManagerFace mana : manaList) {
			mana.update();
		}
	}


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}


}





