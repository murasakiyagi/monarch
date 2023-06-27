package monarch;

import java.io.*;
import java.util.*;
import javafx.geometry.Point2D;

import engine.P2Dcustom;//Point2Dcustom


public class ScouterD {

	int deno;//分母。移動速度

	List<Point2D> walkList = new ArrayList<Point2D>();//受け取る
	List<Point2D> walkListD = new ArrayList<Point2D>();//このクラスで生成
	
	static P2Dcustom customPd;

	public ScouterD(List<Point2D> walkList) {
		this.walkList = walkList;
		deno = 10;
	}


	//角。walkList(v)とwalkListD(k)の位置関係
	static List<Integer> cornList = new ArrayList<>();
	public static Integer getCorn(int i) {
		return cornList.get(i);
	}


	//intを入れたwalkListをlpLimで分割したdoubleのArrayListを返す-----------
	//deno:分母denomination、nume:分子numerator
	//引数denomiによって１ループで進む距離が変わる
	public static ArrayList<Point2D> division(List<Point2D> pdList, int denomi, int r, int c) {//分割。現在地のrcはいらないかも。やっぱり要る
		cornList.clear();
		ArrayList<Point2D> kariList = new ArrayList<Point2D>();
		
		if(denomi <= 0) { denomi = 1; }//エラー回避
		
		if( !pdList.contains(new Point2D(r,c)) ) {
				//このメソッドの引数に使われるListの変化は、メソッド外でも保持される
				//なのでこのメソッドの後は、false==(pdList.size()==0)である。
			pdList.add(0, new Point2D(r,c));
		}//現在地をないなら入れる。このポイントはリストに含まれない
		
		for(int i=1; i < pdList.size(); i++) {
			littleAdd(kariList, pdList.get(i-1), pdList.get(i), denomi);
				cornList.add(kariList.size());
		}
		
		return kariList;
	}
	
		//一マスを分割したリストを得る。fractの値を変えれば色々使える。
		public static void littleAdd(List<Point2D> list, Point2D herePd, Point2D therePd, int denomi) {
			customPd = P2Dcustom.cast(herePd);//キャスト
			
			for(double nume = 1; nume <= denomi; nume++) {
				double fract = nume / denomi;//変数の訳は「断片」だが、ここでの意味は1フレームで進む距離
				list.add( customPd.sigAdd(fract, fract, therePd) );//sigAdd() : hereとthereの比較で+-の符号を得、fractで量を与える。つまりベクトル
			}
		}

	
		public static List<Point2D> inchStep(Point2D herePd, Point2D therePd, int denomi) {//１マスを分解
			//hereとthereは一マス違いという前提
			List<Point2D> list = new ArrayList<Point2D>();
			double distance = herePd.distance(therePd);
			for(double nume = 1; nume <= denomi; nume++) {
				double fract = nume / denomi;
				customPd = P2Dcustom.cast(herePd);//キャスト
				list.add( customPd.sigAdd(fract, fract, therePd) );//sigAdd(double x, double y, P2D pd)
			}
			
				//System.out.println(list);
			return list;
		}
	
/*
		static private void littleAdd(List<Point2D> list, Point2D herePd, Point2D therePd, int denomi) {
			for(double nume = 1; nume <= denomi; nume++) {
				list.add( littleMove(herePd, therePd, nume, denomi) );
			}
		}
		
			static private Point2D littleMove(Point2D herePd, Point2D therePd, double nume, int denomi) {
				//(double)(nume/denomi)にしてはいけない！絶対！ int同士の割り算で返されるのはintである！！
				double fract = nume / denomi;//変数の訳は「断片」だが、ここでの意味は進む距離
				customPd = P2Dcustom.cast(herePd);//キャスト
				//sigAdd() : hereとthereの比較で+-の符号を得、fractionで量を与える。つまりベクトル
				return customPd.sigAdd(fract, fract, therePd);//sigAdd(double x, double y, P2D pd)
			}
*/
}






