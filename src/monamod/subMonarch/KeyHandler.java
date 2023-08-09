package subMonarch;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


import event.Keys;
import event.HandlerFace;
import monarch.GameControler;

public class KeyHandler implements HandlerFace<KeyEvent> {

	Keys keys;
	GameControler game;

	public KeyHandler(GameControler game) {
		this.keys = new Keys();
		this.game = game;
	}

	public void handle(KeyEvent e) {
		keys.keyP(e);
		playStop();
	}
	
		private void playStop() {
			game.changePlay(keys.isP, keys.isQ);
			game.butaiSetting(keys.isV);
			game.debug(keys.isZ);
		}


	public void handle2(KeyEvent e) {
		keys.keyR(e);
	}
	public void handle3(KeyEvent e) {}//null
	public void handle4(KeyEvent e) {}//null
}