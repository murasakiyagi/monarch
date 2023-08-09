package task;

import java.io.*;
import java.util.*;

import javafx.util.*;

//オリジナルパック
import engine.QuickUtil;
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import state.Work;

public class TestTask implements TaskFace {

	protected String name;
	protected Unit un;
	protected Work work;
	private int cnt = 0;

	//コンストラクタ
	public TestTask() {}
	public TestTask(Unit un, Work work) {
		this.un = un;
		this.work = work;
		this.name = "TSK";
	}

	@Override
	public void action() {
		System.out.println("TEST TASK");
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