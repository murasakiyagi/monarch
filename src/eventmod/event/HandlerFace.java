package event;

import java.io.*;
import java.util.*;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.InputEvent;

public interface HandlerFace<T extends Event> {

	public void handle(T e);//press/scrool/action
	public void handle2(T e);//release
	public void handle3(T e);//click
	public void handle4(T e);//dragg

}