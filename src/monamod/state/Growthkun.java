package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定

public class Growthkun extends Statekun {
	Unit un;
	int cnt = 0;

	//コンストラクタ
	public Growthkun() {}
	public Growthkun(Unit un) {
		this.un = un;
	}

//メソッド------------------
	@Override
	public void run() {
		stay();
	}

	private void stay() {
		un.addHp(10);
	}



	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}