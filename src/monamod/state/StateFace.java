package state;

import java.io.*;
import java.util.*;

import monarch.Unit;
import monarch.HumanUnit;

public interface StateFace {
	
	public void run();
	public void stop();
	//public void goaled();
	public void reset();
	public void setUnit(Unit un);
	public default String className() {//どれ。クラス名を返す
		return this.getClass().getSimpleName();
	}

}