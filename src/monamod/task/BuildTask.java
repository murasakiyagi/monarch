package task;

import java.io.*;
import java.util.*;

import javafx.util.*;

//オリジナルパック
import engine.QuickUtil;
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import state.Work;
import field.Paneler;

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
// 		boolean boo = Paneler.build();//Panelerに問い合わせ
			boolean boo = Paneler.setBuild(un.getTgtPd(), un.getTeam());
// 			print(boo, un.getTgtPd());
 			if(boo) { build(); }
	}

	@Override
	public void setUnit(Unit un) {
		this.un = un;
	}
	
	private void build() {
		if(un.getHp() > 50) {

	 		un.create();
	 		un.addHp(-50);
// 			print("CREATE ", un.getTgtPd());
		} else {
// 			print("NOT CREATE");
		}
	}
	//------------------------------------------
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) {
		qu.print(objs);
	}

}