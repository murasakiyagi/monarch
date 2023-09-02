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
import javafx.scene.Camera;

//Background要素
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;



public class Panes {

	PanesSupport sup;
	Pane pane = new Pane();//center
	SubScene subsn;
	HBox hb;//水平。ペインの一種
	VBox vbR;//垂直
	VBox vbL;//垂直
	
	List<PanesSupportFace> supList = new ArrayList<PanesSupportFace>();
	List<Parent> centerLayer = new ArrayList<Parent>();
	
	public BorderPane bp = new BorderPane();//sceneの上
	
	
	
	//topコンテンツ
	Button[] btHs;

	//bottom
	TextArea ta = new TextArea("TEXT AREA");

	//right
	Button[] btVRs;
	static ArrayList<Text> textList = new ArrayList<Text>();

	
	//left
	Button[] btVLs;


	//コンストラクタ＝＝＝＝＝＝＝＝
	public Panes() {};
	public Panes(Parent center, int cenW, int cenH) {
		hb = new HBox();
		vbR = new VBox();
		vbL = new VBox();
// 		btsSetting(2,3,1);
		bp.setMargin(ta, new Insets(12,30,12,12));//隙間
		setBackFill(hb, newBg(
			bgFill(Color.AQUA, 1, 1),
			bgFill(Color.RED, 10, 2)
		));
		subsn = new SubScene(center, cenW, cenH);
		centerLayer.add(center);
		bp = new BorderPane(subsn, hb, vbR, ta, vbL);//中上右下左
		setTextList(3);
		bp.setMouseTransparent(false);
	};
	//===================
	


// 	各ボックス
	public void addNode(String str, Node node) {
			print("ADDNODE", hb.getChildren().size());
		if(str.equals("hb")) { 
			print("str.equals", node);
			hb.getChildren().add(node); 
		}
		if(str.equals("vbR")) { vbR.getChildren().add(node); }
		if(str.equals("vbL")) { vbL.getChildren().add(node); }
	}



// 	ボタン
	public void btsSetting(int h, int l, int r) {
		btHs = new Button[h];
		btVLs = new Button[l];
		btVRs = new Button[r];
		
		btInit(btHs, "hb");
		btInit(btVLs, "vbL");
		btInit(btVRs, "vbR");
	}

		private void btInit(Button[] bts, String box) {
			for(int i =0; i < bts.length; i++) {
				bts[i] = new Button(box + ":" + i);
				addNode(box, bts[i]);
			}
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
	
		public void setTextList(int cnt) {
			textList.clear();
			for(int i=0; i < cnt; i++) {
				Text tx = new Text("Text:" + i);
				tx.setId("Text:" + i);
				textList.add(tx);
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
	








// 	レイヤ
	public void addLayer(Parent p) { centerLayer.add(p); }
	public void removeLayer(Parent p) { centerLayer.remove(p); }
	public void transmit(Parent p, boolean boo) { p.setMouseTransparent(boo); }
	public void transmit(int num, boolean boo) {
		Parent p = centerLayer.get(num);
		p.setMouseTransparent(boo);
	}
	public void transmitedPrint() {
		for(Parent p : centerLayer) {
			if( p.isMouseTransparent() ) {
				print("TRANSMIT");
			} else {
				print("VISIBLE");
			}
		}
	}


// 	カメラ
	public void setSubCamera(Camera camera) {
		subsn.setCamera(camera);
	}


// ゲッター
	public BorderPane getBp() { return bp; }
	public SubScene getSubsn() { return subsn; }
	public Button getBtn(String box, int num) {
		try {
			if(box.equals("hb")) { return btHs[num]; }
			if(box.equals("vbL")) { return btVLs[num]; }
			if(box.equals("vbR")) { return btVRs[num]; }
			 else { return null; }
		} catch(Exception ex) {
			return null;
		}
	}


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}



		/*レイアウト　上から----------


							hb <- svev.bts[0] / svev.bts[1]
							vb <- bts[2] / lb

							subsne <- center==外部Parent

						g <- subsne

					bp <- hb(Top) / vb(L) / g(C) / bts[3](R) / ta(B) //このクラスの最下層。これを外部のsceneに載せる
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