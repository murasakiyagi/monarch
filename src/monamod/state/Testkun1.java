package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定

public class Testkun1 extends Statekun {
	Unit un;

	//コンストラクタ
	public Testkun1() {}
	public Testkun1(Unit un) {
		this.un = un;
	}

//メソッド------------------
	@Override
	public void run() {
		test();
	}

	private void test() {
		//print("  TEST  ");
	}



	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}