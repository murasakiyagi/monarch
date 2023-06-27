package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import monarch.Unit;//


public class Staykun extends Statekun {
	int cnt = 0;

	//コンストラクタ
	public Staykun() {}
	public Staykun(Unit un, Work work) {
		//this.un = un;
		super(un, work);
		this.name = "STY";
	}

//メソッド------------------
	@Override
	public void run() {
		stay();
	}

	private void stay() {
		cnt++;
		if(cnt >= 17) {
				print(cnt, un.getHp());
			work.nextProcess(false);
			cnt = 0;
		}
		un.addHp(1);
	}



	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}