package engine;

import java.io.*;
import java.util.*;



public interface PanesSupportFace {

// 	Panes内でsupportListを作る
	public void setPanes(Panes ps);
	public void setSups();//this
	public void notifyPanes(Collection<Object> c);

}