package monarch;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import event.HandlerFace;
import event.Information;


public class MouseHandler implements HandlerFace<MouseEvent> {

	//座標
	double sx, sy, fx, fy;//start, final
	GameControler game;
	Information info;
	
	
	public MouseHandler(GameControler game) {
		this.game = game;
		info = new Information();
	}

	public void handle(MouseEvent e) {//press
		reStt();
		//System.out.println( e );
		info.mouseSelect(e);
	}

		private void reStt() {
			game.reStt();
		}

	public void handle2(MouseEvent e) {}//release
	public void handle3(MouseEvent e) {}//click
	public void handle4(MouseEvent e) {}//dragg
}