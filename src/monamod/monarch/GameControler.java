package monarch;

import java.io.*;
import java.util.*;

import javafx.scene.layout.Pane;

import unit.UnitManager;
import unit.Unit;
import engine.QuickUtil;

/*has a
KeyHandler
MouseHandler
ScrollHandler
*/

public class GameControler {

	boolean play;
	boolean reset;

	FieldMasu fl;
	FieldManager fm;
	Pane p;
	Unit un;
	Unit unitA;
	UnitManager mana;
	

	public GameControler(FieldManager fm, Pane p) {
		this.fm = fm;
		this.fl = fm.getFl();
		this.p = p;
		
		play = false;
		reset = true;//肝

		mana = new UnitManager(fm);
	}


// 始めに'P'でplay==true, セッティング、reset==false, 
// 	mana.action()１回実行、待機

	public void gaming() {
		if( play ) {
			action();
		}
	}
	
		private void action() {
			butaiSetting( reset );
			mana.action();
			//unitA.action();
		}

	//Handlerに使われる
	public void changePlay(boolean isP, boolean isQ) {
		if(isP) { play = true; }
		if(isQ) { play = false;
// 		 mana.debug();
		}
	}

	public void butaiSetting(boolean isV) {//
		//この機能をクラス化するかどうしよう
		if(isV) {
			play = false;
			reset = false;
			fm.flAction();
			p.getChildren().clear();
			p.getChildren().add(fm.getPane());
			unitSetting();
		}
		
	}


	private void unitSetting() {
		mana.setting();
		p.getChildren().add(mana.getPane());
	}



	public void debug(boolean isZ) {
		if(isZ) {
			if( mana.debug ) {
				mana.debug = false;
			} else {
				mana.debug = true;
			}
		}
	}

// 	======================
	public UnitManager getMana() { return mana; }


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}



		//実験
		public void reStt() {
			Mcon.on();
		}
		public void stop() {
			Mcon.off();
		}



}