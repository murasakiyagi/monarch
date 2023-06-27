package monarch;

import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.input.KeyEvent;

import event.Keys;
import event.KeyReaction;


public class KeyReactionMona extends KeyReaction {
	//オリジナル
	//Keys keys;
	//Unit un;
	
	public KeyReactionMona(Keys keys) {
		super(keys);
		//this.keys = keys;
	}


	public void handle(Event e) {
		keys.keyP(e);
	}


	public boolean playAction(boolean play) {
		if(keys.isP) { play = true; }
		if(keys.isQ) { play = false; }
		return play;//if,elseの場合は元の状態を返す
		//メインにて -> { play = keyRe.playAction(play); }
	}
	
	
	public void unitAction(Unit un) {//jikken
		if(keys.isUp) { un.masuIdouD(-0.05, 0); }
		if(keys.isDown) { un.masuIdouD(0.05, 0); }
		if(keys.isLeft) { un.masuIdouD(0, -0.05); }
		if(keys.isRight) { un.masuIdouD(0, 0.05); }
	}
	
	

}