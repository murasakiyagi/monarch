package unit;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

import engine.QuickUtil;
import engine.P2Dcustom;
import field.Scouter;
import field.FieldManager;
import field.FtcntMapping;
import monarch.Monaserver;


//状況把握
public class ContextGrasp {

	Unit un;
	Unit un2;
	StringBuilder numBy;//２進数値
	int numTen;//１０進数値
	boolean buttle, away, keep;
	Map<Unit, Integer> unitMap;
	
	FieldManager fm;
	FtcntMapping ft;

	public ContextGrasp(UnitManager mana) {
		this.unitMap = mana.getUnitMap();
		this.fm = mana.getFm();
		this.ft = fm.getFt();
	}


	public void run(Unit un, Unit un2) {
		if( !un.equals(un2) ) {
			setUnit(un, un2);
			int kari = checkContext();
			if(kari == 3) {//Join
				setUrgency(un, un2, 3);
				setUrgency(un2, un, 3);
			} else if( kari == 2 ) {//warningは互いが戦闘体制
				guilt();
			} else if( kari == 1 ) {//cautionは一方的に危険視
				setUrgency(un, un2, Math.max(unitMap.get(un), 1));//unitMap.get(un)==0 ~ 2
			}
		}
	}

		private void guilt() {//罪悪感
			if((un.getHp() > 10) & (un2.getHp() > 10)) {
				setUrgency(un, un2, 2);
				setUrgency(un2, un, 2);
			}
		}


		private void setUnit(Unit un, Unit un2) {//このクラス用。利便性
			this.un = un;
			this.un2 = un2;
		}
		
		private void setUrgency(Unit un, Unit un2, int num) {
			unitMap.put(un, num);
			un.setBtlUn(un2);
		}


	public int checkContext() {
		numBy = new StringBuilder("");
		numAddOne( isHuman(un) );//自分
		numAddOne( isHuman(un2) );//相手
		numAddOne( isEnemy() );//敵か
		numAddOne( isStrong() );//強いか
		numAddOne( isNear(3.05) );//近いか
		numAddOne( isNear(1.05) );//隣か
		numAddOne( isNear(0.5) );//重なるか
		return stateNum( numBy.toString() ) ;
	}
	
		//＊注意＊String str = "x" + "y";は、
		//strと"x"と"y"の３っつのインスタンスを生成している。
		//メモリの無駄遣い。StringBuilderを使おう。
		void numAddOne(boolean isQuest) {
			if(isQuest) {
				numBy.append("1");
			} else {
				numBy.append("0");
			}
		}
		

	public int stateNum(String numBy) {
		int kari = 0;
		if(numBy.equals("1111110")) { kari = 2; }
		if(numBy.equals("1111100")) { kari = 1; }
		if(numBy.equals("1011110")) { kari = 2; }
		if(numBy.equals("1010110")) { kari = 2; }
		if(numBy.equals("1101111")) { kari = 3; }
		if(numBy.equals("1100111")) { kari = 3; }
		return kari;
	}



// 	ftcntでの距離と、壁無視の絶対距離の両方を見る
	double dist;//walkList.size()
	private boolean isNear(double kyori) {
		dist = P2Dcustom.kyori(un.getPd(), un2.getPd());//直線距離なのでだめ
// 		dist = ft.ftKyori(un.getPd(), un2.getPd());
		if(dist <= kyori) {
			return true;
		} else {
			return false;
		}
	}
	
	
	boolean isStrong() {//強い？
		return ( un2.getHp() - un.getHp() ) >= 10;
	}


	boolean isEnemy() {//敵か？
		return ( un.team != un2.team );
	}


	boolean isHuman(Unit unit) {//人か？
		return reType(unit).equals("HumanUnit");
	}

		String reType(Object obj) {//型名を調べる
			return obj.getClass().getSimpleName();
		}
	
	
	
	boolean isBlock() {//進行方向の妨げ？
		return false;
	}
	


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	

}
