package event;

import java.io.*;
import java.util.*;

import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.*;

import event.Keys;



public abstract class SceneSetOn implements SetOnFace {

	Scene scene;//メインクラス等より
	Keys keys;//サブクラスにて生成
	HandlerFace<KeyEvent> keyHand;//サブクラスにて生成
	HandlerFace<MouseEvent> mouseHand;
	HandlerFace<ScrollEvent> scrollHand;

	
	public SceneSetOn(
		Scene scene,
		Keys keys,
		HandlerFace<KeyEvent> keyHand,
		HandlerFace<MouseEvent> mouseHand, 
		HandlerFace<ScrollEvent> scrollHand
	) {
		this.scene = scene;
		this.keyHand = keyHand;
		this.mouseHand = mouseHand;
		this.scrollHand = scrollHand;
		this.keys = keys;
		setOn();
	}


	public void setOn() {
		scene.setOnKeyPressed(e -> keyHand.handle(e));
		scene.setOnKeyReleased(e -> keyHand.handle2(e));
		scene.setOnMousePressed(e -> mouseHand.handle(e));
		scene.setOnMouseReleased(e -> mouseHand.handle2(e));
		scene.setOnMouseClicked(e -> mouseHand.handle3(e));
		scene.setOnMouseDragged(e -> mouseHand.handle4(e));
		scene.setOnScroll(e -> scrollHand.handle(e));
	}

	
	
	public void action(ActionEvent e) {

	}

	public void keyP(KeyEvent e) {
		System.out.println("KEYTEST  PRESS");
	}

	public void keyR(KeyEvent e) {
		System.out.println("KEYTEST  RELEASE");
	}

	public void mouseD(MouseEvent e) {
		System.out.println("KEYTEST  RELEASE");
	}
	
	public void mouseC(MouseEvent e) {
		System.out.println("KEYTEST  RELEASE");
	}
	
	public void mouseP(MouseEvent e) {
		System.out.println("KEYTEST  RELEASE");
	}
	
	public void mouseR(MouseEvent e) {
		System.out.println("KEYTEST  RELEASE");
	}	
	
	public void scroll(ScrollEvent e) {
		System.out.println("KEYTEST  RELEASE");
	}


}




