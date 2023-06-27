package engine;

import java.io.*;
import java.util.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.Parent;
import javafx.scene.text.Text;

//Background要素
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;



public class Panes {

	Pane p = new Pane();//center
	SubScene subsn;
	HBox hb;//水平。ペインの一種
	VBox vbR;//垂直
	VBox vbL;//垂直
	TextArea ta = new TextArea("TEXT AREA");
	
	public BorderPane bp = new BorderPane();//sceneの上
	
	
	
	//topコンテンツ
	Button btH = new Button("H BTN");

	//bottom

	//right
	Button btVR = new Button("V BTN");
	static ArrayList<Text> textList = new ArrayList<Text>();

	
	//left
	Button btVL = new Button("V BTN");


	//コンストラクタ＝＝＝＝＝＝＝＝
	public Panes() {};
	public Panes(Parent center, int cenW, int cenH) {
		hb = new HBox(btH);
		vbR = new VBox(btVR);
		vbL = new VBox(btVL);
		bp.setMargin(ta, new Insets(12,30,12,12));//隙間
		setBackFill(hb, newBg(
			bgFill(Color.AQUA, 1, 1),
			bgFill(Color.RED, 10, 2)
		));
		subsn = new SubScene(center, cenW, cenH);
		bp = new BorderPane(subsn, hb, vbR, ta, vbL);//中上右下左
		setTextList(3);

	};
	//===================
	
	public BorderPane getBp() {
		return bp;
	}
	
	public SubScene getSubsn() {
		return subsn;
	}



	//バックグランド
	private void setBackFill(Region reg, Background bg) {
		//Insets inset = new Insets(1,2,3,4);//隙間
		reg.setBackground( bg );
	}
	
	
		private Background newBg(BackgroundFill... fills) {
			return (new Background( fills ));
		}
		
		
			public BackgroundFill bgFill(Color color, double co, double in) {//Fillsの引数
				CornerRadii radii = new CornerRadii(co);//四隅の半径
				Insets inset = new Insets(in);//隙間
				BackgroundFill bgFill = new BackgroundFill(color, radii, inset);
				
				return bgFill;
			}
	
	
	//Text
	public void setText(int i, String str) {
		textList.get(i).setText(str);
	}
	
		private void setTextList(int cnt) {
			for(int i=0; i < cnt; i++) {
				textList.add(new Text("TEXT" + i));
				vbR.getChildren().add(textList.get(i));
			}
		}
	
	

	public void textAreaCustom(double w, double h) {
		ta.setMaxSize(w, h);
	}
	
	public void topCustom(BackgroundFill... fills) {
		setBackFill( (Region)bp.getTop(), newBg(fills) );
	}

	public void bottomCustom(BackgroundFill... fills) {//ボトムにはテキストエリアを置くため、使えない可能性
		setBackFill( (Region)bp.getBottom(), newBg(fills) );
	}

	public void rightCustom(BackgroundFill... fills) {
		setBackFill( (Region)bp.getRight(), newBg(fills) );
	}

	public void leftCustom(BackgroundFill... fills) {
		setBackFill( (Region)bp.getLeft(), newBg(fills) );
	}

		/*レイアウト　上から----------


							hb <- svev.bts[0] / svev.bts[1]
							vb <- bts[2] / lb

							subsne[3] <- anima.p

								p[2] <- 
							subsne[2] <- p[2]

								p[1] <- 
							subsne[1] <- p[1]

								p[0] <- sld
							subsne[0] <- p[0]

						g <- subsne[0][1][2][3]

					bp <- hb(Top) / vb(L) / g(C) / bts[3](R) / ta(B)
				scene
			stage

								コントローラ（）
							ペイン(複数一つのサブシーンに一意)
						サブシーン(一つのグループに複数可)
					グループ(一意。ペインの一種)
				シーン(一意)
			ステージ(一意)

		---------------------------*/


}