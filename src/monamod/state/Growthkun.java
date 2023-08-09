package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定

// 育みクラス。House専用
public class Growthkun extends Statekun {
	int cnt = 0;

	//コンストラクタ
	public Growthkun() {}
	public Growthkun(Unit un, Work work) {
		super(un, work);
		this.name = "GRW";
	}

//メソッド------------------
	@Override
	public void run() {
		grow();
	}

	private void grow() {
		if(un.getHp() < 150) {
			un.addHp(0.2);
		} else {
			un.create();
			un.addHp(-100);
		}
	}


	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}