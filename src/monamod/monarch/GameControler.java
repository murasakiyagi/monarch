package monarch;

import java.io.*;
import java.util.*;

import javafx.scene.layout.Pane;

import engine.QuickUtil;
import engine.Panes;
import engine.PanesSupport;
import field.FieldManager;
import field.FieldMasu;
import unit.UnitManager;
import unit.Unit;

/*has a
KeyHandler
MouseHandler
ScrollHandler
*/

public class GameControler {

	boolean play;
	boolean reset;

	ChiefManager cf;
	FieldMasu fl;
	FieldManager fm;
	Pane p;
	Unit un;
	Unit unitA;
	UnitManager mana;

	PanesSupport sup;
	HeadUpDisplay hud;

	public GameControler(ChiefManager cf) {
		this.cf = cf;
		this.fm = cf.getFm();
		this.fl = fm.getFl();
		this.p = new Pane();
		p.setMouseTransparent(false);
		play = false;
		reset = true;//肝

		mana = cf.getUm();

			hud = new HeadUpDisplay();
			hud.init();
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
		if(isQ) { play = false; }
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
				p.getChildren().add(hud.getPane());
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
	public ChiefManager getCf() { return cf; }
	public Pane getPane() { return p; }
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