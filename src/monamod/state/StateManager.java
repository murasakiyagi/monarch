package state;

import java.io.*;
import java.util.*;


import monarch.Unit;


public class StateManager {

	private Unit un;
	private Map<String,StateFace> stateMap = new HashMap<String,StateFace>();
	private StateFace keep;
	private boolean isKeep = false;

	public StateManager(Unit un, Work work) {
		this.un = un;
		stateMap.put("TSK", new Taskkun(un, work) );
		stateMap.put("SCH", new Searchkun(un, work) );
		stateMap.put("SSM", new Susumukun(un, work) );
		stateMap.put("AWY", new Awaykun(un, work) );
		stateMap.put("BTL", new Battlekun(un, work) );
		stateMap.put("STY", new Staykun(un, work) );
	}


	public StateFace callState(String name) {
		return stateMap.get(name);
	}


	public String callName(StateFace stt) {
		String kari = null;
		for(String key : stateMap.keySet() ) {
			if( stateMap.get(key).equals(stt) ) {
				kari = key;
			}
		}
		return kari;
	}
	
	public void keepState(StateFace stt) {
		this.keep = stt;
		if(stt != null) { isKeep = true; }
		if(stt == null) { isKeep = false; }
			//print("  KEEP STT  ", isKeep, stt, "-----------");
	}

	public StateFace reState() {//緊急対応直前のStateに戻す
		isKeep = false;
		StateFace kari = keep;
		keep = null;
			//print("  RESTT  ", isKeep, keep, kari,"-----------");
		return kari;
	}
	
	public boolean isKeep() {
		return isKeep;
	}

	public String keepName() {
		return callName(keep);
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
	
}