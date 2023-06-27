package monarch;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;
import engine.P2Dint;

public class ScoutTester {

	//テストメンバ
	public final P2Dint sttPi = new P2Dint(2, 1);
	public final P2Dint golPi = new P2Dint(2, 5);
	public P2Dint gstPi = sttPi.copy();
	public List<P2Dint> testList = new ArrayList<P2Dint>();//fieldの0のマスだけ集める
	public Map<P2Dint, Integer> testMap = new HashMap<>();//座標と歩数を紐づけ
	
	int ftCnt = 0;
	int stopCnt = 0;//一歩進めなかったカウント
	Integer[] stopRansu = { -1, -1,- 1, -1 };//Arrays.asListするとList<?>になる。?はObject
	Integer ransu = -1;
	
	
	//必須メンバ
	public List<Point2D> zeroList = new ArrayList<Point2D>();//fieldの0のマスだけ集める

	public final int[][] field = {//int[9][10],0の数15+34=49
			{0,0,0,0,0,0,0},
			{0,1,1,1,1,1,0},
			{0,1,1,1,0,1,0},
			{0,1,1,1,1,1,0},
			{0,0,0,0,0,0,0},
	};

	public int[][] panel = new int[5][7];//fieldと同じ
	public int[][] manel = new int[5][7];//Map用
	public int flRow = field.length;	//行数。横並びが何個あるか
	public int flCol = field[ field.length-1 ].length;	//列数。縦並びが何個あるか
	public int[][] newPanel;



	//コンストラクタ
	public ScoutTester() {
		getZeroList();
	}


	public void panelChange(List<P2Dint> pis, int num, int[][] pane) {
		for(P2Dint pi : pis) {
			pane[pi.x()][pi.y()] = pane[pi.x()][pi.y()] + num;
		}
	}

	public void manelChange(Map<P2Dint, Integer> map, int[][] pane) {
		for(P2Dint pi : map.keySet()) {
			pane[pi.x()][pi.y()] = map.get(pi);
		}
	}

	public void manelChangePd(Map<Point2D, Integer> map, int[][] pane) {
		for(Point2D pd : map.keySet()) {
			pane[(int)pd.getX()][(int)pd.getY()] = map.get(pd);
		}
	}

	public void panelPrint(int[][] pane) {
		//fl.panelをわかりやすくプリント
		System.out.println("===== FL.PANEL =====");
		for(int i=0; i < pane.length; i++) {
			for(int j=0; j < pane[i].length; j++) {
				int kari = pane[i][j];
				if(kari >= 1000) { kari = 0; }
				//桁数によるずれちょうせい
				if(kari < 10 ) {//一桁
					System.out.print("  " + kari);
				} else {
					System.out.print(" " + kari);
				}
			}
			System.out.println();
		}
		System.out.println("===== FL.PANEL END =====");
	}
	
	public void zeroPrint() {
		for(Point2D pd : zeroList) {
			System.out.println( (int)pd.getX() +" "+ (int)pd.getY() );
		}
	}
	
	public void testPrint(String str, List<P2Dint> list) {
		for(P2Dint pi : list) {
			System.out.println( str +" "+ pi.x() +" "+ pi.y() );
		}
	}
	
	public void stopPrint() {
		for(int i=0; i < stopRansu.length; i++) {
			System.out.println( stopRansu[i] );
		}
	}
	
	
	public void createField(List<P2Dint> list) {
		int xMax = 0;
		int yMax = 0;
		for(P2Dint pi : list) {
			xMax = Math.max(pi.x(), xMax);
			yMax = Math.max(pi.y(), yMax);
		}
		newPanel = new int[xMax+1][yMax+1];
		panelChange(list, 2, newPanel);
		panelPrint(newPanel);
	}
	
	
	public List<Point2D> getZeroList() {//フィールドの０のマス。zeroListとzeroSize,panel[][]の属性を決める
		zeroList.clear();//このメソッドに含むか迷う

		for(int k=0; k < flRow; k++) {
			for(int j=0; j < flCol; j++) {
				if(field[k][j] == 0) {
					zeroList.add( new Point2D(k,j) );
					panel[k][j] = 0;
				} else {
					panel[k][j] = 1;
				}
			}
		}
		
		return zeroList;
	}
	
}