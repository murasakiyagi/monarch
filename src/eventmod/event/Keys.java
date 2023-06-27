package event;

import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.input.KeyEvent;


//対応キーを管理
//簡潔が命のクラス

public class Keys {
	public static boolean 	isQ, isW, isP, 
							isZ, isX, isC, isV, isB, isN, isM, 
							isUp, isDown, isLeft, isRight;


	public Keys() {}


	public void keyP(KeyEvent e) {
		switch(e.getCode()) {
			case UP:
				isUp = true;
				break;
			case DOWN:
				isDown = true;
				break;
			case LEFT:
				isLeft = true;
				break;
			case RIGHT:
				isRight = true;
				break;
			case Z:
				isZ = true;
				break;
			case X:
				isX = true;
				break;
			case C:
				isC = true;
				break;
			case V:
				isV = true;
				break;
			case B:
				isB = true;
				break;
			case N:
				isN = true;
				break;
			case M:
				isM = true;
				break;
			case Q:
				isQ = true;
				break;
			case W:
				isW = true;
				break;
			case P:
				isP = true;
				break;
			default:
				break;
		}
	}
	
	public void keyR(KeyEvent e) {
		switch(e.getCode()) {
			case UP:
				isUp = false;
				break;
			case DOWN:
				isDown = false;
				break;
			case LEFT:
				isLeft = false;
				break;
			case RIGHT:
				isRight = false;
				break;
			case Z:
				isZ = false;
				break;
			case X:
				isX = false;
				break;
			case C:
				isC = false;
				break;
			case V:
				isV = false;
				break;
			case B:
				isB = false;
				break;
			case N:
				isN = false;
				break;
			case M:
				isM = false;
				break;
			case Q:
				isQ = false;
				break;
			case W:
				isW = false;
				break;
			case P:
				isP = false;
				break;
			default:
				break;
		}
	}


}