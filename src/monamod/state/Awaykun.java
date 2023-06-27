package state;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;


//オリジナルパック
import monarch.Unit;//--source-pathではなく--class-pathで/senryakuを指定
import monarch.Scouter;
import monarch.ScouterD;
import monarch.FieldMasu;


public class Awaykun extends Statekun {

	private List<Point2D> awayList = new ArrayList<Point2D>();
	private List<Point2D> zeroList;
	Map<Point2D, Integer> ftcntMap;
	Scouter scout;
	Point2D slfPd;
	Unit btlUn;
	Point2D btlPd;
	FieldMasu fl;
	int slfFtcnt, btlFtcnt;
	int progCnt = 0;//progressCount。進捗。
	Point2D awyPd;


	//コンストラクタ
	public Awaykun(Unit un, Work work) {
		//this.un = un;
		super(un, work);
		this.scout = work.scout;
			//this.btlUn = work.btlUn;//現時点でwork.btlUnはnull。つまり参照するメモリ空間は無い。今後work.btlUnが参照を持ってもthis側はnullのまま。
		this.zeroList = scout.getZeroList();
		this.fl = un.fl;
		this.ftcntMap = fl.ftcntMap;
		name = "AWY";
	}

//メソッド------------------
	@Override
	public void run() {
		//away();
		away();
	}

	@Override
	public void reset() {
		
	}

	private void away() {
		try{
			if(awayList.size() == 0) {
				btlPd = work.getBtlPd();
				slfPd = un.getPd();
				nextAway();
					if(un.teamNum() == 1) {
						//printArr("  AWAYLIST", awayList);
					}
			} else {
				susumukunD();
			}
		} catch(NullPointerException e) {
			System.out.println("EXCEPTION  ");
			
		}
	}


//susumukun-----------
	public void susumukunD() {

		if( awayList.size()-1 == progCnt ) {//tgtPdに到着
			un.setPdD( awayList.get(progCnt) );
			un.setPdI( awayList.get(progCnt) );
			un.notifyObserver();
			awayList.clear();
			progCnt = 0;
			goaled(true);//目的地に着いたら
		} else if(awayList.size() > progCnt) {//進行中
			inching(1);
		}
	}
	
	
		private void inching(int cnt) {//実際に動かす
			un.setPdD( awayList.get(progCnt) );
			un.setPdI( awayList.get(progCnt) );
			un.notifyObserver();
			progCnt += cnt;
		}

		@Override
		protected void goaled(boolean boo) {//目的地についた後
			work.nextProcess(boo);
				//if(un.teamNum() == 1) { print("AWAY  GOAL", awyPd); }
		}
//susumukun--------------


	private void nextAway() {//次に逃げる場所
		//現状ごく簡単な作りだ。あらゆる条件で変化しなければならない
		boolean ok = false;
	
		slfFtcnt = ftcntMap.get(slfPd);
		btlFtcnt = ftcntMap.get(btlPd);
		int subFtcnt = btlFtcnt - slfFtcnt;//＋ーを確認,subtract
		int sig = (int)Math.signum(subFtcnt);
			//次のessenceの引数が次のマスを表している。危険と反対に行く
		for(Point2D pd : essence(slfFtcnt + sig * -1)) {//essence(val)のvalはslfFtcnt+-1
			if( masukyori(pd) == 1 ) {//essenceで得られるpdは必ずslfの隣とは限らないので
				ScouterD.littleAdd(awayList, slfPd, pd, 20);
					awyPd = pd;
					if(un.teamNum() == 1) {
						System.out.println("TEAM  "+ un.teamNum() +" slfPd  "+ slfPd +" pd "+ pd );
						//System.out.println("MASUKYORI  " + masukyori(pd));
					}
				ok = true;
				return;
			}
		}
		if(!ok) { goaled(true); }
	}




	private List<Point2D> essence(Integer val) {//Mapの中の或るValueに対するKeyを取得する。この時Keyは複数になる可能性がある。
		List<Point2D> list = new ArrayList<Point2D>();
		for(Point2D key : ftcntMap.keySet()) {
			if( ftcntMap.get(key) == val ) {
				list.add(key);
			}
		}
					if(un.teamNum() == 1) {
						//System.out.println("TEAM  "+ un.teamNum() +" val "+ val +" slfftcnt "+slfFtcnt);
						//printArr("  KEY LIST  ", list);
					}
		return list;
	}


	private double masukyori(Point2D pd) {
		return scout.masukyori(slfPd, pd);
	}



		//susumukunからコピー
/*		public void inching(int cnt) {//実際に動かす
			un.setPdD( walkListD.get(progCnt) );
			un.setPdI( walkListD.get(progCnt) );
			un.notifyObserver();
			progCnt += cnt;
		}
*/




	//---------------------
	private int x(Point2D pd) {
		return (int)pd.getX();
	}

	private int y(Point2D pd) {
		return (int)pd.getY();
	}
	//------------------------------------------


	private void printArr(String str, List<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}