package unit;

import java.io.*;
import java.util.*;


import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.util.*;

//オリジナル
import pict.Img;
import monarch.FieldMasu;


public class HumanUnit extends Unit {

	protected char houkou;
	private boolean haveJob = false;


	//=============================================
	//コンストラクタ
	public HumanUnit(double hitP, int team, int imgNum, UnitManager mana) {
		super(hitP, team, imgNum, 1, mana);
	}
// 		継承の複雑さの一例としてこのまま置いておく
// 			UnitのコンストラクタでWorkの生成をしている。
// 			Workコンストラクタで（Work.un = Unit.un;）としている。
// 			その後に objNum = 1 をしても、Unit.un.objNum == 1 だが Work.un.objNum == 0 である
// 			superコンストラクタ引数に int objNum を追加した


	//アクション
	@Override
	public void action() {//ループ上で変化を監視
		super.action();
	}
	//-----------------------------------

/*	@Override
	public void initialize(double hitP) {//初期化
		super.initialize(hitP);
	}
*/
		boolean arrival() {//作業現場(tgtPd)
			return tgtPd.equals(getPd());
		}

		public void create() {
			UnitCreater uc = new UnitCreater(this.mana);
			Unit un = uc.houseCreate(50, this.team, this.imgNum);
			un.setPdP(tgtPd);
// 			発生場所の調整がUnitになるので、デザイン的に懸念
		}










	//=================================-
/*	private void print(Object... objs) {
		System.out.print(print0());
		for(int i=0; i < objs.length; i++) {
			System.out.print("  " + objs[i]);
		}
		System.out.println();
	}
	private String print0() {
		return "  !" + getClass().getSimpleName() + "!  ";
	}
*/
	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}

}//class,end



