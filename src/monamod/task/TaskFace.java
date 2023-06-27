package task;

import java.io.*;
import java.util.*;

import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定

public interface TaskFace {

	public void action();
/*	public void run();
	public void stop();
	//public void goaled();
	public void reset();
*/	public void setUnit(Unit un);

}