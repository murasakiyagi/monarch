package monarch;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;

import monarch.Unit;

//状況把握
public class ContextGrasp {

	Unit un;
	Unit un2;
	StringBuilder numBy;//２進数値
	int numTen;//１０進数値
	boolean buttle, away, keep;
	Map<Unit, Integer> unitMap;
	
	Scouter scout;


	public ContextGrasp(Map<Unit, Integer> unitMap) {
		this.unitMap = unitMap;
		this.scout = new Scouter();//FieldMasuに持たせたい。
	}


	public void run(Unit un, Unit un2) {
		if( !un.equals(un2) ) {
			setUnit(un, un2);
			int kari = checkContext();
			if( kari == 2 ) {//warningは互いが戦闘体制
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
					//System.out.println(un.getHp() +" "+ un2.getHp() );
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
		numAddOne( isNear(3.1) );//近いか
		numAddOne( isNear(1.1) );//隣か
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
		if(numBy.equals("111111")) { kari = 2; }
		if(numBy.equals("111110")) { kari = 1; }
		return kari;
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
	
	
	int sctCnt;//walkList.size()
	private boolean isNear(double kyori) {
		List<Point2D> list = new ArrayList<Point2D>();
		list = scout.guide(un.getPd(), un2.getPd());
		sctCnt = list.size();
		if(sctCnt <= kyori) {
			return true;
		} else {
			return false;
		}
	}
	
	

	boolean isBlock() {//進行方向の妨げ？
		return false;
	}
	


/*	public void checkContext(Unit un, Unit un2) {
		if( !isHuman(un) ) { return; }//
		if( !isNear(un, un2) ) { return; }
		if( !isEnemy(un, un2) ) { return; }
		if( !isHuman(un2) ) {
			return;
		} else {
			un.setState(un.btlkn);
		}
		//if( isStrong(un, un2) ) {  }
	}
*/

}
