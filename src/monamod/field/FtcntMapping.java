package field;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

import engine.QuickUtil;
import engine.P2Dcustom;

public class FtcntMapping {

	List<Point2D> zeroList;
// 	List<Point2D> walkList = new ArrayList<Point2D>();
	Set<Point2D> walkSet;
	Map<Point2D, Integer> ftcntMap = new HashMap<Point2D, Integer>();
	Map<Point2D, Boolean> boolMap = new HashMap<Point2D, Boolean>();
	Point2D fstPd;
	Point2D gstPd;
	int rowCnt, colCnt;
	int ftCnt;


	public FtcntMapping() {}

	public FtcntMapping(FieldMasu fl) {
		initialize(fl.getRow(), fl.getCol(), fl.getZeroList(), fl.getWalkSet());
	}

	public FtcntMapping(int rowCnt, int colCnt, List<Point2D> zeroList, Set<Point2D> walkSet) {
		initialize(rowCnt, colCnt, zeroList, walkSet);
	}
	
		public void initialize(FieldMasu fl) {
			initialize(fl.getRow(), fl.getCol(), fl.getZeroList(), fl.getWalkSet());
		}
		
		public void initialize(int rowCnt, int colCnt, List<Point2D> zeroList, Set<Point2D> walkSet) {
			this.rowCnt = rowCnt;
			this.colCnt = colCnt;
			this.zeroList = zeroList;
			this.walkSet = walkSet;
		}
		

	public void groundZero(Point2D fstPd) {//初期化初期位置
		ftcntMap.clear();
		this.fstPd = fstPd;
		for(Point2D pd : walkSet) {
			ftcntMap.put(pd, 1000);
			if(fstPd == null) { fstPd = pd; }
		}
		gstPd = fstPd;
		ftCnt = 1;
		ftcntMap.put(fstPd, ftCnt);
	}
	
		public void groundZero2() {//初期化初期位置
			ftcntMap.clear();
			fstPd = null;
			for(int i=0; i < rowCnt; i++) {
				for(int j=0; j < colCnt; j++) {
					Point2D pd = new Point2D(i,j);
					ftcntMap.put(pd, 1000);
					if( !zeroList.contains(pd) ) {
						if(fstPd == null) { fstPd = pd; }
					}
				}
			}
			gstPd = fstPd;
			ftCnt = 1;
			ftcntMap.put(fstPd, ftCnt);
		}


	public Map<Point2D, Integer> createMap(Point2D fstPd) {
		groundZero(fstPd);
		mapping();
// 			two();
		return ftcntMap;
	}

		List<Point2D> riplList = new ArrayList<Point2D>();//波紋
		List<Point2D> remvList = new ArrayList<Point2D>();//波紋消し
		public void mapping() {
// 					print("MAPPING 3");
			Point2D kariPd;
			ftCnt = 1;
			riplList.clear();
			riplList.add( fstPd.add(0,0) );//size==1
// 					print("MAPPING 1", riplList.size());
			int brkCnt = 0;
			while( filless() ) {//審査
				ftCnt++;
				
				int size = riplList.size();
				for(int i = 0; i < size; i++) {
					kariPd = riplList.get(i);
					shihou(kariPd);
					remvList.add(kariPd);
				}
				riplList.removeAll(remvList);
				remvList.clear();
				if(brkCnt++ >= 100000) {
					print("10000 BREAK");
					break;
				}
			}
// 					print("MAPPING 3", brkCnt);
		}
	
			private void shihou(Point2D pd) {
				Point2D kari;
				for(int i = 0; i < 4; i++) {
					kari = pd.add( P2Dcustom.neswNum(i, 1) );//上下左右に１マス
					walkCont(kari, pd);
				}
			}
	
				private void walkCont(Point2D pd, Point2D pd2) {
					if( walkSet.contains(pd) ) {
						if(ftcntMap.get(pd) == 1000) {
							ftcntMap.put(pd, ftCnt);
							riplList.add(pd);
							if(ftCnt == 2) {
// 								print("--2--",pd);
// 								print("-----",pd2);
							}
							
						} else {
// 							ftcntMap.putIfAbsent(pd, ftCnt);//1000が入っているので意味なし
						}
					}
				}

	private void two() {
		for(Point2D pd : ftcntMap.keySet() ) {
			if(ftcntMap.get(pd) == 2) {
				print("---TWO---", pd);
			}
		}
	}


// ==================================
// 使ってない

	private Point2D nextIppo() {
		Point2D pd = randomIppo2();
		if(!zeroList.contains( pd )) { return pd; }
		return gstPd;
	}

		private Point2D randomIppo() {
			int rdm = (int)(Math.random()*4);//0~3
			if(rdm == 0) { return gstPd.add(1,0); }
			if(rdm == 1) { return gstPd.add(0,1); }
			if(rdm == 2) { return gstPd.add(-1,0); }
			if(rdm == 3) { return gstPd.add(0,-1); }
			return gstPd;
		}

		private Point2D randomIppo2() {
			int rdm = (int)(Math.random()*4);//0~3
			return gstPd.add( P2Dcustom.neswNum(rdm, 1) );
		}
		


		private boolean filless() {//walkListのところに1000(初期値)があればもう一度
			int kari;
			boolean boo = false;
			for(Point2D walk : walkSet) {
				kari = ftcntMap.get(walk);
				if(kari == 1000) {
					boo = true;
					break;
				}
			}
			return boo;
		}
		
		private boolean oneInterval() {//隣り合うマスのftcntは１違い。
			boolean bool;
			int kari1, kari2;

			for(Point2D walk : walkSet) {
				bool = false;
				kari1 = ftcntMap.get(walk);

// 				四方の一つでもfalseがあればダメ
				for(int i=-1; i <= 1; i=i+2) {

					kari2 = ftcntMap.get( walk.add(i,0) );
					if( Math.abs(kari1 - kari2) == 1 ) {
						bool = true;
					} else {
						bool = false;
						break;
					}
					
					kari2 = ftcntMap.get( walk.add(0,i) );
					if( Math.abs(kari1 - kari2) == 1 ) {
						bool = true;
					} else {
						bool = false;
						break;
					}

				}
				
				boolMap.put(walk, bool);
			
			}
			
			//boolMapにfalseがある内はもう一度
			return boolMap.values().contains(false);
// 			return boolMap.containsValue(false);
			
		}

// =============================

	private int masukyori(Point2D slfPd, Point2D tgtPd) {//マスとマスの距離を測る。壁は無視される
		int ro1 = (int)slfPd.getX();		int co1 = (int)slfPd.getY();
		int ro2 = (int)tgtPd.getX();		int co2 = (int)tgtPd.getY();;
		return Math.abs(ro1 - ro2) + Math.abs(co1 - co2);
	}

// 	要見直し。
	public int ftKyori(Point2D slfPd, Point2D tgtPd) {
		int kari = ftcntMap.get(slfPd) - ftcntMap.get(tgtPd);
		return Math.abs(kari);
	}





	public void mapping2(Map<Point2D, Integer> map, Point2D pd, int ftCnt) {
		//順番、rowマイナス方向、colマイナス方向、rowプラス、colプラス
		for(int i=-1; i <= 1; i=i+2) {
			loopPut(map, pd, ftCnt, i, 0);
			loopPut(map, pd, ftCnt, 0, i);
		}
	}

		private void loopPut(Map<Point2D, Integer> map, Point2D pd, int ftCnt, int x, int y) {
			int mk;
			Point2D cngPd = pd.add(0,0);//注意

			//cngPdはwhile内で０マスに当たるまで移動する。pdは不変
			while( !zeroList.contains(cngPd) ) {
				mk = masukyori( cngPd, pd );//任意の座標と幽体の距離
				ftcntPutAbsent( map, cngPd, (ftCnt + mk) );//ftcntMapに幽体の座標と歩数をプット
				cngPd = cngPd.add(x, y);//x,yは追加分
			}
		}
		
			//
			private void ftcntPutAbsent(Map<Point2D, Integer> map, Point2D pd, int cnt) {//唯一のしか入れないけれど
				map.putIfAbsent( pd, cnt );//putIfAbsent()はkeyと値を持っていない場合に追加する
				if( map.get( pd ) >= cnt ) {//キー(p2d)のバリューより今回の数値以下なら。以下にするのは可能性を広げるため
					map.put( pd, cnt );
				}
			}
	
// ====================================
// 	ゲッター
	public Map<Point2D, Integer> getFtcntMap() { return ftcntMap; }



			private void jikken() {
				List<Point2D> kariList = new ArrayList<Point2D>();
				List<Point2D> kesuList = new ArrayList<Point2D>();
				
				
				Point2D kari;
				ftCnt = 1;
				kariList.add( fstPd.add(0,0) );
				
				for(int j = 1; j < 5; j++) {
					
					int size = kariList.size();
					for(int i = 0; i < size; i++) {
						kari = kariList.get(i);
						kariList.add(kari.add(j,j));
						kesuList.add(kari);
					}
					
					print("JIKKEN 1", kariList);
					print("JIKKEN 2", kesuList);
					kariList.removeAll(kesuList);
					print("JIKKEN 3", kariList);
					kesuList.clear();
				}
			}



	
	//出来上がりをターミナル表示
	public void panelPrint(Map<Point2D, Integer> ftcntMap) {
		int[][] panel = new int[rowCnt][colCnt];
		ScoutTester st = new ScoutTester();
		st.manelChangePd(ftcntMap, panel);
		st.panelPrint(panel);
	}
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}


}