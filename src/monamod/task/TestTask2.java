package task;

import java.io.*;
import java.util.*;

import javafx.util.*;

//オリジナルパック
import engine.QuickUtil;
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import state.Work;

public class TestTask2 implements TaskFace {

	protected String name;
	protected Unit un;
	protected Work work;
	private int cnt = 0;

	//コンストラクタ
	public TestTask2() {}
	public TestTask2(Unit un, Work work) {
		this.un = un;
		this.work = work;
		this.name = "TSK2";
	}

	@Override
	public void action() {
		un.addHp(10);
		System.out.println("TEST TASK 2");
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