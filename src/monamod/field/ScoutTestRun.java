package field;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;
import engine.P2Dint;


public class ScoutTestRun {




	public void testRun() {
		ScoutTester st = new ScoutTester();
		Scouter sc = new Scouter(st.getZeroList());
		st.zeroPrint();
		//st.panelPrint(st.panel);

		List<P2Dint> pis = new ArrayList<P2Dint>();
		pis.add(st.sttPi);
		pis.add(st.golPi);
		st.panelChange(pis, 2, st.panel);
		//st.panelPrint(st.panel);
		
		sc.slfPd = st.sttPi.copy();
		sc.tgtPd = st.golPi.copy();
		sc.furidasi();
			System.out.println( sc.gstPd.add(1,0) );
			sc.gstPd.printPi();
		
		//十字リストの作成はどうか？
		sc.zeroCrossAdd(sc.crosListS, sc.gstPd);
		st.panelChange(sc.crosListS, 1, st.panel);
		//st.panelPrint(st.panel);

		sc.zeroCrossAdd(sc.crosListG, sc.tgtPd);
		st.panelChange(sc.crosListG, 1, st.panel);
		//st.panelPrint(st.panel);

		st.testPrint("test", st.testList);

		//マッピングはできるか？
		sc.ftcntMapping(sc.gstPd);
		st.manelChange(sc.ftcntMap, st.manel);
		st.panelPrint(st.manel);

		//乱数の生成と格納

		sc.ippoRansu();
		sc.ippoSusumu();
			sc.gstPd.printPi();
			System.out.println("FTCNT  " + sc.ftcntSlf);
			System.out.println("RANSU  " + sc.ransu);

		st.manelChange(sc.ftcntMap, st.manel);
		st.panelPrint(st.manel);


		sc.connect();
		st.testPrint("tran", sc.tranList);
		st.testPrint("walk", sc.walkList);


/*
		while(st.stopCnt < 4) {
			st.ransu = sc.ippoRansu(st.stopRansu, st.ransu);
			st.stopRansu[st.stopCnt] = st.ransu;
			
			P2Dint kari = st.gstPi.add( sc.ippoSusumu(st.ransu) );
			if( !st.zeroList.contains( kari ) ) {
				st.gstPi = kari;
					System.out.println("TRUE");
			} else {
				//st.stopCnt++;
					System.out.println("FALSE");
			}
				st.stopCnt++;
		
			if(st.gstPi.equals(st.golPi)) { break; }
		}

		//幽体を進める
		st.gstPi = st.gstPi.add(0,4);
		st.gstPi.printPi();
		System.out.println(st.gstPi.equals(st.golPi) +" "+ "");

		st.stopPrint();
		System.out.println(st.stopRansu +" "+ st.ransu);

		st.testMap.put(st.gstPi.copy(), 10);
		System.out.println( st.testMap.get(st.gstPi) );
*/
	}


}