package state;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;


import monarch.Unit;
import task.TaskFace;
import task.TaskManager;
import monarch.Scouter;
import monarch.Mcon;

public class Work {//実質UnitControler

	Unit un;
	Unit btlUn;
	StateManager stMn;
	StateFace state;
	TaskManager tskMana;
	TaskFace task;
	
	Scouter scout;
	
	boolean stopStt = false;
	
		//jikken
		public boolean testBool = false;
		public int testCnt = 0;
	
	
	int process = 0;
	Map<Integer, String> prosteMap = new HashMap<Integer, String>();

	public Work(Unit un) {
		this.un = un;
		this.scout = new Scouter();//stMnより先に生成
		this.stMn = new StateManager(un, this);
		this.tskMana = new TaskManager(un, this);
		settingMap();
		setState("SCH");
		stBf = state;
	}


	public void working() {
		//cout,warnがここにある
		stackStack();
		processor();
		state.run();//nextProcess()はここ
	}
	
	
		private void settingMap() {
			prosteMap.put(0, "SCH");
			prosteMap.put(2, "SSM");
			prosteMap.put(4, "TSK");
			prosteMap.put(10, "AWY");
			prosteMap.put(12, "STY");
			prosteMap.put(14, "BTL");
			//prosteMap.put(, "");
		}
	
	
	int cnt = 0;
	private void processor() {//工程管理者
		if(prosteMap.get(process) != null) {//prosteMapに設定されている数値
			setState(prosteMap.get(process));
			process++; //ここで奇数になり、各State.run()で一段落するとnextProcessが呼び出されカウントが上がる
		}

		if(process == 1) {//State=="SCH"であるが
			if(stMn.isKeep()) {//元のStateとProcessに戻す。SCH.run()は実行されない。
					//print("7  ", un.getName(), stMn.callName(state), process);
				String kari = stMn.callName(stMn.reState());
				setState(kari);
				process = mapGetKey(kari);
				stopStt = false;
					//print("7  ", un.getName(), stMn.callName(state), process);
			} else {
			}
		}

	}
	
	
	public void nextProcess(boolean next) {
		if(next) { process += 1; }
		if(!next) { process = 0; 
			//if(un.whoName("HERO")) { print("5  nextProcess  ", stMn.keepName(), stMn.callName(state)); }
			//print("5  nextProcess  ", stMn.keepName(), stMn.callName(state));
		}
	}
	
	private Integer mapGetKey(String str) {//MapのvalueではなくKeyを求める
		for(Integer key : prosteMap.keySet() ) {
			if( prosteMap.get(key).equals(str) ) {
				return key;
			}
		}
		return 0;
	}

	private void setTgtPd() {
		if(un.eqlPd(null)) {
			un.setTgtPd(un.randomPd());
		}
	}

	public void setState(String name) {
		state = stMn.callState(name);
	}

	public void setTask(String name) {
		task = tskMana.callTask(name);
	}


	public void taskAction() {//Taskkun use
		task.action();
	}


//スタック監視
	int watchCnt = 0;
	int stackCnt = 0;
	Point2D pdBf = new Point2D(0,0);
	double hpBf = 100;
	StateFace stBf;
	private void stackStack() {
		watchCnt++;
		if(watchCnt > 10) {
			watchCnt = 0;
			stackWatch();
		}
	}
	
	private void stackWatch() {
		boolean pdEql = pdBf.equals( un.getPdD() );
		boolean hpEql = hpBf == un.getHp();
		boolean stEql = stBf.equals(state);
		if( pdEql && hpEql && stEql) {
			stackCnt++;
			
			if(stackCnt > 10) {
					testBool = true;
					//print("STACK");
			}
			if(stackCnt > 20) {
				stMn.keepState(null);
				process = 0;
					print("STACK");
			}
		} else {
			pdBf = un.getPdD();
			hpBf = un.getHp();
			stBf = state;
			stackCnt = 0;
		}
	}



//危険
//tgtPd付近でcautionが起きると復帰時に不具合 -> un.setPdI()にroundをかけているからか。setPdIの修正はせず遠回りでやる
	public void warning() { caution(14); }
	public void caution() { //caution(10); }
}
		private void caution(int prc) {
			if(!stMn.isKeep()) { stMn.keepState(state); //}
				//if(un.whoName("HERO")) { print("1  ", stMn.isKeep(), stMn.callName(state), prc); }
				//print("1  ", stMn.isKeep(), stMn.callName(state), prc);
				print("1 ", stMn.isKeep(), un.getTgtPd(), un.getPdD(), un.getPd(), un.getName());
			}
			//state.reset();//末端class毎に違う
			stopStt = true;
			process = prc;
		}


//敵
	public void setBtlUn(Unit btlUn) {
		this.btlUn = btlUn;
	}
	
	public Point2D getBtlPd() {
		return btlUn.getPd();
	}

	public double getBtlHp() {
		return btlUn.getHp();
	}

	//--------------------------
	protected void print(Object... objs) {
		System.out.print(print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print("  " + objs[i]);
		}
		System.out.println();
	}
	protected String print0() {
		return "  !" + getClass().getSimpleName() + "!  ";
	}
	
	
/*
		if(process == 0) {
			setState("SCH");
			process++;
			System.out.println("STATE SCH");
				System.out.println(state);
		}

		if(process == 2) {
			setState("SSM");
			process++;
			System.out.println("STATE SSM");
				System.out.println(state);
		}
		if(process == 4) {
			setState("TSK");
			process++;
			System.out.println("STATE TSK  " + cnt++);
		}
		if(process == 10) {
			setState("AWY");
			process++;
		}
		if(process == 14) {
			setState("BTL");
			process++;
		}
*/

}