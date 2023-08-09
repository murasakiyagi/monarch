package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定

public class Testkun2 extends Statekun {
	Unit un;

	//コンストラクタ
	public Testkun2() {}
	public Testkun2(Unit un) {
		this.un = un;
	}

//メソッド------------------
	@Override
	public void run() {
		test();
	}

	private void test() {
		//print("  TEST2  ");
	}



	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}