package engine;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;


public class P2Dint {

	private int x, y;
	private int hash = 0;

	//コンストラクタ
	public P2Dint() {
		this.x = 0;
		this.y = 0;
	}
	public P2Dint(int x, int y) {
		this.x = x;
		this.y = y;
	}


	public P2Dint copy() {
		return (new P2Dint(this.x, this.y));
	}


	public P2Dint get() {
		return this;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
	
	public P2Dint add(int i, int j) {
		return new P2Dint( x()+i, y()+j );
	}
	
	public P2Dint add(P2Dint pi) {
		return new P2Dint( x()+pi.x(), y()+pi.y() );
	}
	
	public P2Dint remove(int i, int j) {
		return new P2Dint( x()-i, y()-j );
	}
	
	public void printPi() {
		System.out.println("P2Dint " + x() +" "+ y());
	}
	
	
	static public P2Dint cast(Point2D pd) {
/*		int rndX = (int)Math.round( pd.getX() );
		int rndY = (int)Math.round( pd.getY() );
		return (new P2Dint( rndX, rndY ));
*/
		return ( new P2Dint( (int)pd.getX(), (int)pd.getY() ) );
	}
	
	static public List<P2Dint> cast(List<Point2D> p2dList) {
		List<P2Dint> list = new ArrayList<P2Dint>();
		for(Point2D pd : p2dList) {
			P2Dint kari = cast( pd );
			list.add( kari );
		}
		return list;
	}
	
	
	static public Point2D recast(P2Dint pi) {
		return (new Point2D( pi.x(), pi.y() ));
	}
	
	static public List<Point2D> recast(List<P2Dint> intList) {
		List<Point2D> doubleList = new ArrayList<Point2D>();
		for(P2Dint pi : intList) {
			doubleList.add( recast(pi) );
		}
		return doubleList;
	}
	
	
	//ハッシュコード。Point2Dのソースそのまま
	@Override
	public int hashCode() {
		if(hash == 0) {
			long bits = 7L;
			bits = 31L * bits + Double.doubleToLongBits(x());
			bits = 31L * bits + Double.doubleToLongBits(y());
			hash = (int) (bits ^ (bits >> 32));//＞＞シフト演算子
		}
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) { return true; }
		if(obj instanceof P2Dint) {
			P2Dint other = (P2Dint)obj;
			return x() == other.x() && y() == other.y();
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "P2Dint [x = " + x() + ", y = " + y() + "]";
	}

}