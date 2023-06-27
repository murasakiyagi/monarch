package monarch;

import java.io.*;
import java.util.*;

import javafx.scene.layout.Pane;

/*has a
KeyHandler
MouseHandler
ScrollHandler
*/

public class GameControler {

	boolean play;
	boolean reset;

	FieldMasu fl;
	Pane p;
	Unit un;
	Unit unitA;
	UnitManager mana;
	

	public GameControler(FieldMasu fl, Pane p) {
		this.fl = fl;
		this.p = p;
		
		play = false;
		reset = true;

		mana = new UnitManager(fl);
	}


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
		//この機能をクラス化するか？
		if(isV) {
			play = false;
			reset = false;
			fl.action();
			fl.panelPrint();
			p.getChildren().clear();
			p.getChildren().add(fl.p);
			unitSetting();
		}
		
	}


	private void unitSetting() {
		mana.setting();
		p.getChildren().add(mana.pane);

/*		unitA = new HumanUnit(100,1,6, fl, mana);
		UnitView viewA = new UnitView(unitA);
			unitA.initialize();
*/
	}


		//実験
		public void reStt() {
			Mcon.on();
		}
		public void stop() {
			Mcon.off();
		}



}