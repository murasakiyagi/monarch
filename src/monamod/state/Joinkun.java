package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定

public class Joinkun extends Statekun {

	static HashMap<Unit, Double> kphpMap = new HashMap<Unit, Double>();
	static HashMap<Unit, Long> timeMap = new HashMap<Unit, Long>();

	//コンストラクタ
	public Joinkun() {}
	public Joinkun(Unit un, Work work) {
		super(un, work);
		this.name = "JOI";
	}

//メソッド------------------
	@Override
	public void run() {
		join();
	}

	@Override
	public void reset() {
		kphpMap.clear();
	}


	@Override
	protected void goaled(boolean boo) {//目的地についた後
// 			print("JOIN");
		kphpMap.remove(un);
		super.goaled(boo);
	}


// 	1ターンに呼び出される順番が不定。
// 	強者（吸収者）が先の場合、keepHpがないので直接徴収。
// 	後の場合、btlHp==0なので、keepHp>0を徴収
// 	弱者（被吸収者）はkeepHpして死ぬ準備
// 	同格の場合先に呼ばれたほうが強くなり、後の方が弱者となる
	private void join() {
		if(un.getHp() >= work.getBtlHp()) {
			double btlHp = work.getBtlHp();
// 			このユニットのJoinが先なら（btlHp > 0）のはずである
			if(btlHp > 0) { un.addHp(btlHp); }
			if(btlHp == 0) {//このユニットのJoinが後なら（btlHp == 0）のはずである
				double d = kphpMap.get(un);
				un.addHp(d);
			}
		} else {
// 			弱いならkeepHpして死ぬ
			kphpMap.put(work.getBtlUn(), un.getHp());//重要。自分ではなくbtlUnをkeyにする
			un.addHp(-un.getHp());
		}
		this.goaled(false);
	}


// 	kphpMapクリーニング。ゲームリセットすると残るので
	public void keepClear() { kphpMap.clear(); }




	//------------------------------------------
// 	super.print()
	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}


}