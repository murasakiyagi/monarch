package task;

import java.io.*;
import java.util.*;

import javafx.util.*;

//オリジナルパック
import engine.QuickUtil;
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import state.Work;

public class GrowthTask implements TaskFace {

	protected String name;
	protected Unit un;
	protected Work work;
	private int cnt = 0;

	//コンストラクタ
	public GrowthTask() {}
	public GrowthTask(Unit un, Work work) {
		this.un = un;
		this.work = work;
		this.name = "GROWTH";
	}

	@Override
	public void action() {
		grow();
// 		System.out.println("GROWTH TASK");
	}

	@Override
	public void setUnit(Unit un) {
		this.un = un;
	}
	
	private void grow() {
		if(un.getHp() < 150) {
			un.addHp(5);
		} else {
			un.create();
			un.addHp(-100);
		}
	}
	
	
	//------------------------------------------
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) {
		qu.print(objs);
	}

}