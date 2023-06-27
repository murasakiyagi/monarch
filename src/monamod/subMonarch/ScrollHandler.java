package monarch;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

import event.HandlerFace;


public class ScrollHandler implements HandlerFace<ScrollEvent> {


	public ScrollHandler(GameControler game) {

	}

	public void handle(ScrollEvent e) {
		System.out.println( "SCROLL HAND   ");
		//System.out.println( e );
	}

	public void handle2(ScrollEvent e) {}//null
	public void handle3(ScrollEvent e) {}//null
	public void handle4(ScrollEvent e) {}//null
}