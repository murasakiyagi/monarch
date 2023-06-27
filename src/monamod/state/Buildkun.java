package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import monarch.HumanUnit;

public class Buildkun extends Statekun {
	Unit un;

	//コンストラクタ
	public Buildkun() {}
	public Buildkun(Unit un) {
		this.un = un;
	}

//メソッド------------------
	@Override
	public void run() {
		build();
		damage(1);//建設するにも痛みは伴う
	}

	private void damage(int dmg) {
		un.addHp(-dmg);
	}

	private void build() {
		
	}


	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}