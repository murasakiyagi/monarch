package monarch;

import java.io.*;
import java.util.*;


import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.*;


import event.SceneSetOn;
import event.Keys;

public class SetOnMona extends SceneSetOn {

	
	
	
	public SetOnMona(Scene scene, GameControler game) {
		super(
			scene, 
			new Keys(),
			new KeyHandler(game),
			new MouseHandler(game),
			new ScrollHandler(game)
		);
		super.setOn();
	}




}




