package monarch;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import engine.Panes;
import engine.PanesSupport;


public class PanesSupMona extends PanesSupport {

// 	Panes ps;

	public PanesSupMona(Panes ps) {
		super(ps);
		ps.btsSetting(2, 5, 2);

	}
	
	
	protected void btnPush(ActionEvent e, int num) {
		print(e.toString());
		print(e.getSource());
		print(num);
		if(num == 0) {

		}
	}



// 	スーパークラス

// 	public void setOnAction(String box, int num) {
// 		Button btn = ps.getBtn(box, num);
// 		btn.setOnAction(e -> btnPush(e));
// 	}

// 	print(Object...);

}