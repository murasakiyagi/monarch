package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import task.TaskFace;

public class Taskkun extends Statekun {
	//Unit un;


	//コンストラクタ
	public Taskkun() {}
	public Taskkun(Unit un, Work work) {
		//this.un = un;
		super(un, work);
	}

//メソッド------------------
	@Override
	public void run() {
		taskAction();
	}

	private void taskAction() {
		work.taskAction();
		work.nextProcess(false);
	}

	private void setTask() {
		//print("  TEST  ");
	}



	//------------------------------------------


	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}