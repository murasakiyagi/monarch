package monarch;

import java.io.*;
import java.util.*;

import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.Camera;

import event.SceneSetOn;
import event.Keys;
import subMonarch.KeyHandler;
import subMonarch.MouseHandler;
import subMonarch.ScrollHandler;

public class SetOnMona extends SceneSetOn {
	
	public SetOnMona(Scene scene, GameControler game, Camera camera) {
		super(
			scene, 
			new Keys(),
			new KeyHandler(game),
			new MouseHandler(game, camera),
			new ScrollHandler(game, camera)
		);
// 		super.setOn();
	}



}




