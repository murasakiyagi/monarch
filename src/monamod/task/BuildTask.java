package task;

import java.io.*;
import java.util.*;

import javafx.util.*;

//オリジナルパック
import engine.QuickUtil;
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import state.Work;

public class BuildTask implements TaskFace {

	protected String name;
	protected Unit un;
	protected Work work;
	private int cnt = 0;

	//コンストラクタ
	public BuildTask() {}
	public BuildTask(Unit un, Work work) {
		this.un = un;
		this.work = work;
		this.name = "BUILD";
	}

	@Override
	public void action() {
// 		un.create();
		System.out.println("BUILD TASK");
	}

	@Override
	public void setUnit(Unit un) {
		this.un = un;
	}
	//------------------------------------------
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) {
		qu.print(objs);
	}

}