//cbの感知クラス
package engine;

import java.io.*;
import java.util.*;
import javafx.geometry.Point2D;


public class P2Dcustom extends Point2D {

	public P2Dcustom(double x, double y) {
		super(x,y);
	}
	public P2Dcustom(Point2D pd) {
		super(pd.getX(), pd.getY());
	}
	
	//Point2DはStringと同様、イミュータブルの様で(セッターが無い)、
	//データ隠蔽がされている様で、、、newを使わざるを得ない。
	public static P2Dcustom cast(Point2D pd) {
		return new P2Dcustom(pd);
	}
	
	

	public static int intX(Point2D pd) {
		return (int)Math.round(pd.getX());
	}
	public static int intY(Point2D pd) {
		return (int)Math.round(pd.getY());
	}
	
	public static double x(Point2D pd) {
		return pd.getX();
	}
	public static double y(Point2D pd) {
		return pd.getY();
	}
	
	//進むべき方向の計算と加算を同時に
	public Point2D sigAdd(double x, double y, Point2D pd) {
		double addX = x * signumX(pd);//xyは進む距離
		double addY = y * signumY(pd);//signumは方角
		return this.add(addX, addY);//addの戻り値はPoint2D。thisの値は変わらない
	}
	
	public Point2D signumPd(Point2D pd) {//xyは最終的に{-1,0,1}のどれか
		double x, y;
		x = signumX(pd);
		y = signumY(pd);
		return (new Point2D(x, y));
	}
	
	public double signumX(Point2D pd) {//X軸の符号を得る
		return Math.signum(pd.getX() - this.getX());
	}
	
	public double signumY(Point2D pd) {//Y軸の符号を得る
		return Math.signum(pd.getY() - this.getY());
	}
	
	public String bearing(Point2D pd) {
		String compass = "";//方角
		double x, y;
		
		x = signumX(pd);
		y = signumY(pd);
		
		if(y == 1) { compass += "s"; }
		if(y == -1) { compass += "n"; }
		if(y == 0) { compass += "0"; }
		
		if(x == 1) { compass += "e"; }
		if(x == -1) { compass += "w"; }
		if(x == 0) { compass += "0"; }

		return compass;
	}
	
	
	
	//@Overrideは戻り値が同じであること
	public void addC(double x, double y) {
		this.add(x,y);
	}
	
	
// 		戻り値をaddする
		public static Point2D neswNum(int num, int scale) {
			Point2D pd = Point2D.ZERO;
			int p = 1 * scale;
			int m = -1 * scale;
			if(num == 0) { pd = pd.add(0, m); }//n↑
			if(num == 1) { pd = pd.add(p, 0); }//e→
			if(num == 2) { pd = pd.add(0, p); }//s↓
			if(num == 3) { pd = pd.add(m, 0); }//w←
			if(num == 4) { pd = pd.add(p, m); }//ne
			if(num == 5) { pd = pd.add(p, p); }//es
			if(num == 6) { pd = pd.add(m, p); }//sw
			if(num == 7) { pd = pd.add(m, m); }//wn
			return pd;
		}
// 		7 0 4
// 		3 * 1
// 		6 2 5


	public static boolean isNext(Point2D pd, Point2D tgtPd) {
		boolean boo = false;
		for(int i = 0; i < 4; i++) {
			boo = tgtPd.equals( pd.add(neswNum(i, 1)) );
			if(boo) { break; }
		}
		return boo;
	}

	//壁無視
	public static double kyori(Point2D pd, Point2D tgtPd) {
		double dis = pd.distance(tgtPd);//ピタゴラス
		double pow = Math.pow(dis, 2);//a^2 + b^2 = c^2 = dis
		double sqrt = Math.sqrt(pow-1);//(a==1)a^2=1,
		return Math.round(sqrt + 1);
	}

		public static int kyoriInt(Point2D pd, Point2D tgtPd) {
			return (int)kyori(pd, tgtPd);
		}
	
	
		//jikken.method()
		private void jikken() {
			Point2D pda = Point2D.ZERO;
			Point2D pdb = new Point2D(1,2);
			double dis = pda.distance(pdb);//ピタゴラス
			double pow = Math.pow(dis, 2);//a^2 + b^2 = c^2 = dis
			double sqrt = Math.sqrt(pow-1);//(a==1)a^2=1, 
			print("JIKKEN ", dis, pow, sqrt+1);
		}



	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	
/*	super.classのメソッド
	注意：addなどは（pd = pd.add(x,y)）で変更する

	p2d add(double,double/p2d)//加算
	p2d subtract(double,double/p2d)//減算
	p2d midpoint(double,double/p2d)//中間点
	double getXY()
	bool equals(obj)
	double distance(double,double/p2d)//距離
	
*/
}