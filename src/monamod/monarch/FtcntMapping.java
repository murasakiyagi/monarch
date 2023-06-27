package monarch;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;


public class FtcntMapping {

	List<Point2D> zeroList;
	List<Point2D> walkList = new ArrayList<Point2D>();
	Map<Point2D, Integer> ftcntMap = new HashMap<Point2D, Integer>();
	Map<Point2D, Boolean> boolMap = new HashMap<Point2D, Boolean>();
	Point2D fstPd;
	Point2D gstPd;
	int rowCnt, colCnt;
	int ftCnt;

	public FtcntMapping(int rowCnt, int colCnt, List<Point2D> zeroList) {
		this.rowCnt = rowCnt;
		this.colCnt = colCnt;
		this.zeroList = zeroList;
		groundZero();
	}


	public void groundZero() {//初期化初期位置
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
		
			System.out.println("FSTPD "+fstPd);

	}


		private boolean filless() {//walkListのところに1000(初期値)があればもう一度
			int kari;
			boolean boo = false;
			for(Point2D walk : walkList) {
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

			for(Point2D walk : walkList) {
				bool = false;
				kari1 = ftcntMap.get(walk);

				for(int i=-1; i <= 1; i=i+2) {

					kari2 = ftcntMap.get( walk.add(i,0) );
							//System.out.println("" + walk.add(i,0) +" "+ kari2 );
					if( Math.abs(kari1 - kari2) == 1 ) {
						bool = true;
					}
					
					kari2 = ftcntMap.get( walk.add(0,i) );
							//System.out.println("" + walk.add(i,0) +" "+ kari2 );
					if( Math.abs(kari1 - kari2) == 1 ) {
						bool = true;
					}
					
				}
				
				boolMap.put(walk, bool);
			
			}
			
			//boolMapにfalseがある内はもう一度
			return boolMap.values().contains(false);
			
		}


	public Map<Point2D, Integer> createMap() {
		int brkCnt = 0;
		while( filless() && oneInterval() ) {
			mapping(ftcntMap, gstPd, ftCnt);
			gstPd = nextIppo();
			ftCnt = ftcntMap.get(gstPd);

				//System.out.println("" + gstPd +" "+ ftCnt );

			if(brkCnt++ >= 10000) {
				System.out.println("10000");
				break;
			}
		}
				System.out.println("brkCnt "+brkCnt);
		return ftcntMap;
	}

	private Point2D nextIppo() {
		Point2D pd = randomIppo();
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


	private int masukyori(Point2D slfPd, Point2D tgtPd) {//マスとマスの距離を測る。壁は無視される
		int ro1 = (int)slfPd.getX();		int co1 = (int)slfPd.getY();
		int ro2 = (int)tgtPd.getX();		int co2 = (int)tgtPd.getY();;
		return Math.abs(ro1 - ro2) + Math.abs(co1 - co2);
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
	
	//出来上がりをターミナル表示
	public void panelPrint() {
		int[][] panel = new int[rowCnt][colCnt];
		ScoutTester st = new ScoutTester();
		st.manelChangePd(ftcntMap, panel);
		st.panelPrint(panel);
	}

}