package monarch;

import java.io.*;
import java.util.*;




public class LoopCount {

	static private final int minval = Integer.MIN_VALUE;
	static private final int maxval = Integer.MAX_VALUE;
	static public int lpCnt = minval;//mainにてループカウントをする
	static public int overLpCnt = minval;//lpCntがmaxvalに至った場合
	//static public int passCnt;//lpCnt-minval
	int lpDel = minval;//増加させる分。lpDel = lpLim - lpCnt
	int lpLim = minval;//その後
	int startCnt = minval;//lpCntほじ
	boolean isCount = false;
	public boolean isLap = false;//lapWatch()で変更

	//コンストラクタ
	public LoopCount() {}
	public LoopCount(int lpDel) {
		this.lpDel = lpDel;
	}

	public void action() {
		if(lpCnt < maxval - 1000) {
			lpCnt++;
		} else {
			overLpCnt++;
			lpCnt = minval;
		}
	}


		private boolean overLim() {//in,stopWatch()

			if(lpLim == minval) {
				lpLim = lpCnt + lpDel;
			}
			
			//オーバーしたらtrue
			if(lpCnt >= lpLim) {//
				lpLim = minval;
				return true;
			} else {
				return false;
			}
		}
	
	//stopWatchはstartCntを取らない
	public boolean stopWatch() {//コンストラクタで受けている場合
		return overLim();
	}

	public boolean stopWatch(int later) {
		setDel(later);
		return overLim();
	}
	
	//-----------
	public void setDel(int delta) {
		lpDel = delta;
	}
	
	public int getCnt() {//overLpCntについては後々考える
		return lpCnt - minval;
	}
	//-----------

	public void startWatch() {//passageと組み
		startCnt = lpCnt;
		isCount = true;
	}

	public void endWatch() {//startと組み
		startCnt = minval;
		isCount = false;
	}

	public int passCnt() {//経過時間
		return lpCnt - startCnt;
	}
	
	public int hoji;
	public void lapWatch(int later) {//設定時間になるとstartCntを更新
		if( !isCount ) { startWatch(); }//これがないと常にstartCnt=lpCnt
			//print("HOJIHOJI 1", hoji = passCnt());
		if( boolWatch(later) ) {
			isLap = true;
			startWatch();
		} else {
			isLap = false;
		}
		//stopWatchならOK、boolWatchはダメ
			//print("HOJIHOJI 2", hoji);
		//重要。boolWatch(int >= later)でstartWatch()が始まるといいうことは、
		//later=10なら、このlapWatch()の処理が終わった時のpassCnt()の戻り値は9までになる。
		//startCntはこの中で更新されるのに対し、lpLimは次のstopWatchまで更新がない。
	}
	
	public boolean boolWatch(int later) {
		if(passCnt()+1 >= later) {
			return true;
		} else { return false; }
	}
	
	
	//-------------------
	private void print(Object obj, Object obj2, Object obj3) {
		System.out.println("  LOOPCOUNT  " + obj +"  "+ obj2 +"  "+ obj3);
	}

	private void print(Object obj, Object obj2) {
		System.out.println("  LOOPCOUNT  " + obj +"  "+ obj2);
	}

	private void print(Object obj) {//Overrode
		System.out.println("  LOOPCOUNT  " + obj);
	}


}