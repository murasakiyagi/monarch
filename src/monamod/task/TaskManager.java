package task;

import java.io.*;
import java.util.*;

import engine.QuickUtil;
import monarch.ManagerFace;
import unit.Unit;
import state.Work;


public class TaskManager implements ManagerFace {

	Unit un;
	Work work;

	Map<String,TaskFace> taskMap = new HashMap<String,TaskFace>();

	public TaskManager(Unit un, Work work) {
		taskMap.put("TST1", new TestTask(un, work) );
		taskMap.put("TST2", new TestTask2(un, work) );
		taskMap.put("BILD", new BuildTask(un, work) );
	}


	public TaskFace callTask(String name) {
		return taskMap.get(name);
	}


	@Override
	public void register(ManagerFace mf) {};
	public void remove(ManagerFace mf) {};
	public void update() {};
	public void notifyMana() {};
	
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) {
		qu.print(objs);
	}

}