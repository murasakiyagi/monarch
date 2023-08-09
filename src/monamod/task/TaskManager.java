package task;

import java.io.*;
import java.util.*;

import unit.Unit;
import state.Work;


public class TaskManager {

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


}