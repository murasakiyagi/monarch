package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import engine.QuickUtil;
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定


public class Statekun implements StateFace {

	protected String name;
	protected Unit un;
	protected Work work;
	private int cnt = 0;

	//コンストラクタ
	public Statekun() {}
	public Statekun(Unit un, Work work) {
		this.un = un;
		this.work = work;
		this.name = "STT";
	}

//メソッド------------------
	@Override
	public void run() {}
	
	@Override
	public void stop() {}

	//@Override
	protected void goaled(boolean boo) {
		work.nextProcess(boo);
	}

	@Override
	public void reset() {}

	@Override
	public void setUnit(Unit un) {
		this.un = un;
	}


	//------------------------------------------
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) {
		qu.print(objs);
	}

/*	protected void print(Object... objs) {
		System.out.print(print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print("  " + objs[i]);
		}
		System.out.println();
	}
	protected String print0() {
		return "  !" + getClass().getSimpleName() + "!  ";
	}
*/


}