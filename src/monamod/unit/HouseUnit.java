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


public class HouseUnit extends Unit {

	protected char houkou;
	private boolean haveJob = false;


	//=============================================
	//コンストラクタ
	public HouseUnit(double hitP, int team, int imgNum, UnitManager mana) {
		super(hitP, team, imgNum, 2, mana);
	}


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
			Unit un = uc.humanCreate(100, this.team, this.imgNum);
			un.setPdP(this.row, this.col);
// 				print("THIS PD ",this.row, this.col);
// 				print("UN PD ", un.getPd());
// 			発生場所の調整がUnitになるので、デザイン的に懸念
		}










	//=================================-

	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}

}//class,end



