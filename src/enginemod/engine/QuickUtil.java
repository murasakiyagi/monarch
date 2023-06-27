package engine;

import java.io.*;
import java.util.*;
import javafx.geometry.Point2D;

public class QuickUtil {
	
	Object that;
	
	public QuickUtil() {}
	public QuickUtil(Object that) {
		this.that = that;
	}
	
	public int xI(Point2D pd) { return (int)pd.getX(); }
	public int yI(Point2D pd) { return (int)pd.getY(); }
	public double x(Point2D pd) { return pd.getX(); }
	public double y(Point2D pd) { return pd.getY(); }
	
	
	
/*使用方法
	import engine.QuickUtil;
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	protected void print(Object... objs) { qu.print(objs); }
*/
	public void print(Object... objs) {
		if(that == null) { System.out.print("インスタンス引数にthisがおすすめ。"); }
		
		System.out.print(print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print("  " + objs[i]);
		}
		System.out.println();
	}
	public String print0() {
		if(that != null) {
			return "  !" + that.getClass().getSimpleName() + "!  ";
		} else {
			return "  !" + getClass().getSimpleName() + "!  ";
		}
	}
}