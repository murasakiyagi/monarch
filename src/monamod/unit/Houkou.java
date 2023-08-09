package unit;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

import engine.QuickUtil;


public class Houkou {

	char[] sihou = {
		'e', 'w', 's', 'n'
	};

	public char houkou(int i) {
		return sihou[i];
	}

	public char houkou(Point2D here, Point2D there) {
		char kari = 's';
		double row = (y(there) - y(here));
		double col = (x(there) - x(here));
		if( row == 0 ) {
			if(col > 0) { kari = 'e'; }
			if(col < 0) { kari = 'w'; }
		}
		if(	col == 0 ) {
			if(row > 0) { kari = 's'; }
			if(row < 0) { kari = 'n'; }
		}
		return kari;
	}

	private double x(Point2D pd) {
		return pd.getX();
	}
	private double y(Point2D pd) {
		return pd.getY();
	}
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}

}