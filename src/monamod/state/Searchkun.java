package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import task.TaskFace;

//行き先とタスクを決める

public class Searchkun extends Statekun {
	//Unit un;

	//コンストラクタ
	public Searchkun() {}
	public Searchkun(Unit un, Work work) {
		//this.un = un;
		super(un, work);
		name = "SCH";
	}

//メソッド------------------
	@Override
	public void run() {
		search();
	}

	private void search() {//
		work.setBtlUn(null);
		un.setTgtPd( un.randomPd() );
		setTask("BILD");
		goaled(true);
			//if(un.whoName("HERO")) { print(un.getPd(), un.getTgtPd()); }
			//print("8 ", un.getName(), un.getPd(), un.getPdD());
	}

	private void setTask(String task) {
		work.setTask(task);
	}


	//------------------------------------------

	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}