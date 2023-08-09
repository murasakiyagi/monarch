package monarch;

import java.io.*;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

import engine.QuickUtil;
import pict.Img;


public class FieldView {

	FieldMasu fl;
	Pane p;
	ImageView view;
	Image[] hakoImg;
	Img img;
// 	String[] hakoPath;	//hakoImgの画像のファイルネーム、パス
	//rowとcolumnの関係。row=y, column=x
	static int flRow;// = field.length;	//行数。横並びが何個あるか
	static int flCol;// = field[ field.length-1 ].length;	//列数。縦並びが何個あるか
	static int kitenX;//マス群の描画起点。本当はdouble。XはFieldSizeによってはSceneからはみ出すのでauteField()内で式を組んでいる。だからコンストラクタで引数は必要ない
	static int kisoX;
	static int kitenY;//Yは任意
	static double hakoW, hakoH; //箱の大きさ
	static double ajustW, ajustH;//微調整。箱の大きさとUnitの大きさ


	public FieldView(FieldMasu fl, int kitenX, int kitenY) {
		this.fl = fl;
		p = new Pane();
		img = new Img();
		hakoImg = img.getHakoImages();
		hakoire();
		this.kisoX = kitenX;
		this.kitenY = kitenY;
	}


// 	public int row() { return (int)view.getY(); }
// 	public int col() { return (int)view.getX(); }



	//描画準備ーーーーーーーーーーーーーーーー
	private void regulation() {
		p.getChildren().clear();
		kitenX = (int)( fl.getRow() * hakoW/2 + kisoX );//描画位置の調整
		this.kitenY = kitenY;
// 		this.kitenY = fl.getKitenY();
		ajustW = hakoW / 4;
		ajustH = hakoW / 8;
	}
	

	private void hakoire() {//action
		//hakoPath[i]の内容（パス）はメインで一々設定する。
			//例　fl.hakoPath[0] = "../img/karahako.png";

		hakoW = hakoImg[0].getWidth();//箱のサイズは全部同じだからhakoImg[0]
		hakoH = hakoImg[0].getHeight();
			hakoW = 64.0;
	}


	//このメソッドはこちらでいい
	private void coordChangeV(ImageView v, int i, int j) {//i row, j col
		//少しずつずらしてならべる
		v.setX( setX(i, j) );
		v.setY( setY(i, j) );
	}
	private void coordChangeTx(Text v, int i, int j) {
		//少しずつずらしてならべる
		v.setX( setX(i, j) );
		v.setY( setY(i, j) );
	}
	private void coordChange(Node v, int i, int j) {//i row, j col
		v.setTranslateX( setX(i, j) );
		v.setTranslateY( setY(i, j) );
	}
	
		private double setX(int i, int j) {
			return kitenX - ( i * hakoW/2 )+( j *hakoW/2 );
		}
		private double setY(int i, int j) {
			return kitenY + ( j * hakoW/4 )+( i *hakoW/4 );
		}
		
		private Point2D reCoord(double x, double y) {
/*
			if(x == 0 && y == 0) { i+j==0 }
			if(x == 0 && y > 0) { i+j > 0; i == j }(i+j)%2==0
			if(x > 0 && y > 0) { j > i } j-i==1 -> x=w/2 : j-i==2 x=w/2*2
			if(x < 0 && y > 0) { i > j } i-j==1 -> x=-w/2
			
			a = (x / w/2) == j-i  //== 1
			b = (y / w/4) == j+i  //== 5  5-1/2  j == 2+1 i==2
			c = (b - a)/2;
			d = c + a;
*/			
			double i = ( (x - kitenX) / (hakoW/2) );
			double j = ( (y - kitenY) / (hakoW/4) );
			double rex = (j - i) / 2;
			double rey = rex + i;
			return (new Point2D(rex, rey));
		}


		
	//舞台のイメージと並び方を設定する
	public void masuNarabe() {//action
	
		regulation();

		for(int i=0; i < fl.getRow(); i++) {
			for(int j=0; j < fl.getCol(); j++) {
				view = new ImageView();
				view.setId(""+0);//並び替えで使う
				view.setX(i);
				view.setY(j);
				//hakoImgの半分ずれ、jが増えるごとに半分ずれる
				coordChange(view, i, j);
					
				//行と列の番号振りだけviewには関係ない
				if(i == 0) {
					Text tx = new Text(""+ (j-1));
					tx.setId(""+0);
					coordChange(tx, i, j);
					p.getChildren().add(tx);
					//field[0][j] = 0;//上端の行
					//field[ flRow-1 ][j] = 0;//下端の行
				} else if(j == 0) {
					Text tx = new Text(""+ (i));
					tx.setId(""+0);
					coordChangeTx(tx, i, j);
					p.getChildren().add(tx);
				}


				switch(fl.getFlValue(i,j)) {
					case 0: view.setImage(hakoImg[0]); break;
					case 1: view.setImage(hakoImg[1]); break;
					case 2: view.setImage(hakoImg[2]); break;
					default: break;
				}
				p.getChildren().add(view);
			}//forj,end
		}//fori,end

	}


// ==================================
// ゲッター
	public Pane getPane() { return p; }
// 	public int getRow() { return flRow; }
// 	public int getCol() { return flCol; }
	public double getHakoW() { return hakoW; }
	public double getAjustW() { return ajustW; }
	public double getAjustH() { return ajustH; }
	public int getKitenX() { return kitenX; }
	public int getKitenY() { return kitenY; }


//=============================================


	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}
	
	
}