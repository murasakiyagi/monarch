package monarch;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

import engine.QuickUtil;
import engine.P2Dcustom;

public class FtcntMapping {

	List<Point2D> zeroList;
	List<Point2D> walkList = new ArrayList<Point2D>();
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
			groundZero();
		}
		

	public void groundZero() {//初期化初期位置
		ftcntMap.clear();
		fstPd = null;
		for(int i=0; i < rowCnt; i++) {
			for(int j=0; j < colCnt; j++) {
				Point2D pd = new Point2D(i,j);
				ftcntMap.put(pd, 1000);
				if( !zeroList.contains(pd) ) {
					walkList.add(pd);
					if(fstPd == null) { fstPd = pd; }
				}
				
			}
		}
		gstPd = fstPd;
		ftCnt = 1;
		ftcntMap.put(fstPd, ftCnt);
		
			print("FSTPD ", fstPd);

// 			jikken();
	}





	public Map<Point2D, Integer> createMap() {
		int brkCnt = 0;
		while( filless() || oneInterval() ) {
			mapping(ftcntMap, gstPd, ftCnt);
			gstPd = nextIppo();
			ftCnt = ftcntMap.get(gstPd);

// 			mapping2();

			if(brkCnt++ >= 10000) {
				print("10000 BREAK");
				break;
			}
		}
				print("brkCnt ", brkCnt);
		return ftcntMap;
	}

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


		private boolean filless() {//walkListのところに1000(初期値)があればもう一度
			int kari;
			boolean boo = false;
// 			for(Point2D walk : walkList) {
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

// 			for(Point2D walk : walkList) {
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



	private int masukyori(Point2D slfPd, Point2D tgtPd) {//マスとマスの距離を測る。壁は無視される
		int ro1 = (int)slfPd.getX();		int co1 = (int)slfPd.getY();
		int ro2 = (int)tgtPd.getX();		int co2 = (int)tgtPd.getY();;
		return Math.abs(ro1 - ro2) + Math.abs(co1 - co2);
	}

	public int ftKyori(Point2D slfPd, Point2D tgtPd) {
		int kari = ftcntMap.get(slfPd) - ftcntMap.get(tgtPd);
		return Math.abs(kari);
	}


	public Map<Point2D, Integer> createMap2() {
		mapping2();
		return ftcntMap;
	}

// 	P2Dcustom.neswNum(1, 1)
	List<Point2D> riplList = new ArrayList<Point2D>();//波紋
	List<Point2D> remvList = new ArrayList<Point2D>();//波紋
	public void mapping2() {
// 				print("MAPPING 3");
		Point2D kari;
		ftCnt = 1;
		riplList.add( fstPd.add(0,0) );
		int brkCnt = 0;
// 				print("MAPPING 4");
		while( filless() ) {
			ftCnt++;
			
			int size = riplList.size();
// 				print("MAPPING 5", riplList);
			for(int i = 0; i < size; i++) {
				kari = riplList.get(i);
				shihou(kari);
				remvList.add(kari);
			}
// 				print("MAPPING 6", riplList);
// 				print("MAPPING 7", remvList);
			riplList.removeAll(remvList);
			remvList.clear();
// 				print("MAPPING 8", riplList);
				print("MAPPING 9", riplList.size(), remvList.size() );
			if(brkCnt++ >= 100000) {
				print("10000 BREAK");
				break;
			}
		}
	}

		private void shihou(Point2D pd) {
			Point2D kari;
			for(int i = 0; i < 4; i++) {
				kari = pd.add( P2Dcustom.neswNum(i, 1) );
				walkCon(kari);
			}
		}

		private void walkCon(Point2D pd) {
			if( walkSet.contains(pd) ) {
				if(ftcntMap.get(pd) == 1000) {
					ftcntMap.put(pd, ftCnt);
					riplList.add(pd);
				} else {
					ftcntMap.putIfAbsent(pd, ftCnt);//1000が入っているので意味なし
				}
			}
		}



	public void mapping(Map<Point2D, Integer> map, Point2D pd, int ftCnt) {
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


	
	//出来上がりをターミナル表示
	public void panelPrint() {
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