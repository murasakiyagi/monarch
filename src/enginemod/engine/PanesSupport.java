package engine;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;


public abstract class PanesSupport {

	protected Panes ps;

	public PanesSupport(Panes ps) {
		this.ps = ps;
	}
	
	
// 	box : vbH, vbL, vbR
	public void setAction(String box, int num) {
		Button btn = ps.getBtn(box, num);
		btn.setOnAction(e -> btnPush(e, num));
	}
	
	abstract protected void btnPush(ActionEvent e, int num);

	
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}


}