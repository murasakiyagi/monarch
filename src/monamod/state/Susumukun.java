package state;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

//オリジナルパック
import unit.Unit;//
import unit.Houkou;
import monarch.Scouter;
import monarch.ScouterD;
import engine.P2Dcustom;//Point2Dcustom


//CBのポジション管理クラス
public class Susumukun extends Statekun {

	//lpLimはゲームスピードに関わる重要な要素
	//LoopCount.classを作って格クラスで使っていたが、グローバル変数回避のためやめた
	int divide;//分割。(row+1)-(row)という一マス間を分割！！する！！数値
	int deno = 20;//分母。移動速度
	double masuDiv;//分割された

	int progCnt = 0;//progressCount。進捗。walkListDをremoveさせずにカウントで動かす
	int keepCnt = 0;

	int row, col;
	double rowD, colD;
	List<Point2D> walkList = new ArrayList<Point2D>();
	List<Point2D> walkListD = new ArrayList<Point2D>();
	
	Scouter scout;


	//コンストラクタ
	public Susumukun(Unit un, Work work) {
		super(un, work);
		//StateEnum.setUnit(un);
		this.scout = work.scout;
		name = "SSM";
	}

//メソッド------------------
	@Override
	public void run() {
		arukimawaru3();
	}

	@Override
	public void reset() {
		walkList.clear();
		walkListD.clear();
		keepCnt = progCnt;
		progCnt = 0;
	}

			private void jikken() {
				//jikken
				if(work.testBool) {
					//if(un.whoName("TEKI")) { print("9 ", un.getPd(), un.getPdD(), rounder(1.5, 2)); }
					print("9 ", walkList.size(), walkListD.size(), un.getTgtPd());
				}
			}


	private void arukimawaru3() {
		if( walkDNZ() ) {
			if( un.eqlPd( un.getTgtPd() ) ) {
				goaled(false);
			} else {
				createWalkList(un.getTgtPd());
			}
		} else {
			susumukunD();
		}
	}


	private void createWalkList(Point2D tgtpd) {
		walkList = scout.guide(un, tgtpd);
// 			printArr(un.getName(), walkList);
		walkListD = ScouterD.division(walkList, deno, un.getRow(), un.getCol());
	}


			//現状、walkListおよびDがclearされるのは到着時のみ、同時に消える
			private boolean walkNZ() {
				return (walkList == null) || (walkList.size() == 0);
			}
			private boolean walkDNZ() {
				return (walkListD == null) || (walkListD.size() == 0);
			}
	
	
	public void susumukunD() {
		
		if(work.stopStt && (progCnt % deno == 0) ) {
			goaled(false);//目的地に着いたら
		} else 
		
		//これでもいい -> if(walkListD.size()-1 == progCnt) {
//		if( un.getTgtPd().equals( un.getPdD() ) ) {//tgtPdに到着
		if(prePd(un.getTgtPd(), un.getPdD())) {
			goaled(true);//目的地に着いたら
		} else if(walkListD.size() > progCnt) {//進行中
			inching(1);
		}
	}
	
	
		public void inching(int cnt) {//実際に動かす
			un.setPdD( walkListD.get(progCnt) );
			un.setPdI( walkListD.get(progCnt) );
			un.notifyObserver();
			progCnt += cnt;
				houkou();
// 				if(un.whoName("HERO")) { print(un.getPd(), un.getPdD(), rounder(1.5, 2)); }
		}

			Houkou houkou = new Houkou();
			private void houkou() {
				try {
					if(progCnt % 20 == 0) {
						un.setHoukou(
							houkou.houkou( walkListD.get(progCnt + 1), walkListD.get(progCnt) ) 
						);
// 								print(un.getHoukou());

					}
				} catch(Exception ex) {
					print("HOUKOU EXCEPTION");
				}
			}


		@Override
		protected void goaled(boolean boo) {//目的地についた後
			//un.setPdI(un.getTgtPd());
			un.setPdI(un.getPdD());
			walkList.clear();
			walkListD.clear();
			progCnt = 0;
			work.nextProcess(boo);
		}

			private boolean prePd(Point2D tgtPd, Point2D nowPd) {//tgtPd一歩手前
				boolean kari = false;
				if( tgtPd.add(1, 0).equals(nowPd) ) { kari = true; };
				if( tgtPd.add(-1, 0).equals(nowPd) ) { kari = true; };
				if( tgtPd.add(0, 1).equals(nowPd) ) { kari = true; };
				if( tgtPd.add(0, -1).equals(nowPd) ) { kari = true; };
				return kari;
			}


	private void ajust() {
		if(!un.eqlPd(un.getPdD())) {
			if( walkDNZ() ) {
				walkListD = ScouterD.inchStep(un.getPdD(), un.getPd(), deno - keepCnt);
				progCnt = keepCnt;
				print("AJUST ", un.getPdD(), un.getPd(), walkListD);
			} else {
				print(" !walkDNZ ");
				//inching(1);
			}
		} else {
			keepCnt = 0;
			goaled(false);
		}
	}


	
	private void printArr(String str, List<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}

	private int rounder(double d, int keta) {//keta = 10^x
		double pw = Math.pow(10, keta);
		return (int)Math.round(d);
	}
	


}
