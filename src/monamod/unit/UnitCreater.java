package unit;

import java.io.*;
import java.util.*;


//import javafx.animation.*;
//import javafx.application.*;
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


import engine.QuickUtil;
import monarch.FieldMasu;

/*has a
UnitManager

BuildTask  Managerとの距離が離れているので悪いデザイン
*/

//Unitの情報管理クラス

public class UnitCreater {


	private UnitManager mana;

	//コンストラクタ
	UnitCreater(UnitManager mana) {
		this.mana = mana;
	}



	//セッター-------------------------------------
	//初期設定
	public Unit humanCreate(int hp, int team, int imgNum) {
		Unit un = new HumanUnit(hp,team, imgNum, mana);
		UnitView view = new UnitView(un);//必須。オブザーブ関係構築
		un.initialize();//必須
		return un;
	}

	public Unit houseCreate(int hp, int team, int imgNum) {
		Unit un = new HouseUnit(hp,team, imgNum, mana);
		UnitView view = new UnitView(un);//必須。オブザーブ関係構築
		un.initialize();//必須
		return un;
	}


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	

	String reType(Object obj) {//型名を調べる
		return obj.getClass().getSimpleName();
	}

}