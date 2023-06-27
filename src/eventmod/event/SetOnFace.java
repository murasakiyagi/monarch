package event;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;

public interface SetOnFace {

	public void setOn();
	
	public void action(ActionEvent e);

	public void keyP(KeyEvent e);
	
	public void keyR(KeyEvent e);

	public void mouseD(MouseEvent e);

	public void mouseC(MouseEvent e);

	public void mouseP(MouseEvent e);

	public void mouseR(MouseEvent e);

	public void scroll(ScrollEvent e);

}