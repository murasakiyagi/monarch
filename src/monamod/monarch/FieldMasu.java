package monarch;

import java.io.*;
import java.util.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.Point2D;

import pict.Img;

/*has a
GameControler

*/

//マスゲームの舞台を設定するクラス
//および各クラス各メソッドで使う面倒な処理をここに一括する
/*処理の順番
	舞台の広さをランダムで決定。autoField()
	０マス、１マスを決める。autoField()
		マスの属性を示すpanel[][]インスタンスのみ。autoField()
	zeroListの生成、panel[][]に０マス、１マスを決定。zeromasu(),syokiiti()
	初期配置を決定。walkSetに追加。syokiiti()
	初期配置(fstPds[fs])から別の初期配置(fstPds[fs+1])までランダムに歩き回り、walkSetに追加していく。nextPos(),syokiiti()
	上記の処理が出来ているかチェック。syokiiti()
	歩けるマスと０マスの比率、初期配置間の連絡が不合格なら始めからやり直し。connect()

*/

public class FieldMasu {

	static Pane p = new Pane();
	static int vidcnt = 0;//pに含まれるNodeの通し番号

	//プロパティ
		//fieldは一応ここに参考に書くが、メインで作成して、コンストラクタで上書きする
	static int[][] field = {//int[9][10],0の数15+34=49
			{0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,0},
			{0,1,1,0,1,1,1,1,1,0},
			{0,1,1,2,0,1,2,1,1,0},
			{0,1,1,1,1,0,1,1,1,0},
			{0,1,1,0,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,0}
	};

	//rowとcolumnの関係。row=y, column=x
	static int flRow = field.length;	//行数。横並びが何個あるか
	static int flCol = field[ field.length-1 ].length;	//列数。縦並びが何個あるか
	static int kitenX;//マス群の描画起点。本当はdouble。XはFieldSizeによってはSceneからはみ出すのでauteField()内で式を組んでいる。だからコンストラクタで引数は必要ない
	static int kitenY;//Yは任意

	//Image と ImageViewの違いは、ファイルを読み込むクラスと、読み込んだデータを描画するクラス
	Img img;
	Image[] hakoImg;	//箱のイメージ
	String[] hakoPath;	//hakoImgの画像のファイルネーム、パス
	HakoView hakoView; //メソッド内でインスタンス
	//Node hakoView;
	static double hakoW, hakoH; //箱の大きさ
	static double ajustW, ajustH;//微調整。箱の大きさとUnitの大きさ

	int row = 0;//Field[row][col]
	int col = 0;
	public static ArrayList<Point2D> zeroList = new ArrayList<Point2D>();//fieldの0のマスだけ集める
	private int zeroSize;//zeroListの長さ
	private HashSet<Point2D> walkSet = new HashSet<Point2D>();//歩ける場所。nextPos(),syokiiti()。初期位置から別の初期位置を探し回り歩いた場所を追加
	public static Map<Point2D, Integer> ftcntMap;
	
	//パネルの状態
	public static int[][] panel = new int[flRow][flCol];//fieldの中の数値をいじるため
	//このクラスを完全に独立（オリジナルクラスを使わない）させるためPaneler.classを使わない
	//Img.classを使っているが、どうするか考え中
	//public static Paneler[][] panelers = new Paneler[flRow][flCol];//fieldの中の数値をいじるため


	//in syokiiti()
	Point2D fstPds[] = new Point2D[4];
	Point2D sndPds[] = new Point2D[fstPds.length];
	Boolean[] fstbo = new Boolean[fstPds.length];//始点がすべて通じているか。
	int fs;//fstPds[fs]
	//Point2D fstPd, sndPd, serPd, forPd;//キャラ初期位置。ここから進入できないマスは0にする


	//ラストチェック
	int masuTotal;//Fieldの全マスの数 flRow * flCol
	int noAdmit;//立ち入り禁止のマス数 masuTotal - walkSet.size()
	int flDivide;//分配。( flRow * flCol - (noAdmit + walkSet.size()) == 0 )
	boolean checkPass = false;
	
	
	//コンストラクタ=========================================

	public FieldMasu() {}; //スーパークラスを(があれば)そのままインスタンスするやつ

	public FieldMasu(String[] hakoPath, int x, int y, double hakosize) {

		this.hakoPath = hakoPath;
		img = new Img();
		hakoImg = img.getHakoImages();
/*		hakoImg = new Image[] {
			new Image(img.getHakoUrls()[0]),
			new Image(img.getHakoUrls()[1]),
			new Image(img.getHakoUrls()[2])
		};	//箱の種類数
*/		flRow = field.length;	//行数。横線が何個あるか
		flCol = field[0].length;	//列数。縦線が何個あるか
		kitenX = x; //マスを並べるときの初めの座標。Xでありrowでない
		kitenY = y; //Yでありcolでない
		hakoW = hakosize;
		ajustW = hakoW / 4;
		ajustH = hakoW / 8;
	}

	//=======================================================
	//メインアクション---------
	public void action() {
			//print("FIELDMATH==================");
		p.getChildren().clear();
		vidcnt = 0;
		lastCheck();
		//walkSet.clear(); connect();
		hakoire();
		masuNarabe();
		
			
			FtcntMapping fm = new FtcntMapping(flRow, flCol, zeroList);
			ftcntMap = fm.createMap();
			fm.panelPrint();

	}
	//-------------------------


	private void lastCheck() {//最終チェック。action
		int fccnt = 0;
		walkSet.clear();//初期化。lastCheck()は全ての処理の始めに来るので、下のwhileには必ず一回引っかかる

		while( (flRow * flCol) != (zeroList.size() + walkSet.size()) ) {//全マス数 != ０マス＋歩けるマス、であればやり直し
			walkSet.clear();
			connect();

			fccnt++;
			if(fccnt >= 100) {//無限ループ防止
				fccnt = 0;
				print(" LAST_CHECK  無限ループ防止");
				break;
			}
		}
	}


	//autoField()を成功させる
	private void connect() {//lastCheck

		int i = 0;
		int jj = 0;
		while( (flRow * flCol / 3) > walkSet.size() ) {//歩けるマスとゼロマスの比率
		
			autoField();//とりあえず作ってみる
			
			while( Arrays.asList(fstbo).contains(false) ) {//初期配置同士の連絡ができるか。fstboにfalseがあれば最初からやり直し
				jj++;
				autoField();
				if(jj >= 1000) { break; }//無限ループ防止保険
			}
		}

		//autoFieldが全部終わったら、fieldの値を修正
		for(int k=0; k < flRow-1; k++) {
			for(int j=0; j < flCol-1; j++) {
				if(walkSet.contains(new Point2D(k, j))) {//walkSet=通れるマス=１のマス、でないなら０に。２マスも１に変わる
					field[k][j] = 1;
				} else {
					field[k][j] = 0;
				}
			}
		}

		zeromasu();//fieldの値からzeroListを作り直すだけ
	}


		private void autoField() {//connect
	
			//fieldの基礎
			//field = new int[8][8];//実験用
			int minimum = 6;//最小
			field = new int[ (int)(Math.random() * 6 + minimum) ][ (int)(Math.random() * 6 + minimum) ];//ランダム生成
			flRow = field.length;	//行数。横線が何個あるか
			flCol = field[0].length;	//列数。縦線が何個あるか
			kitenX = (int)( flRow * hakoW/2 + 10 );//描画位置の調整
	
			makeBarrier();
	
			panel = new int[flRow][flCol];
			//panelers = new Paneler[flRow][flCol];
	
			syokiiti();
	
		}//autoField.end--------------------------


			private void makeBarrier() {//障害
				//障害物(０マス)生成
				for(int i=0; i < flRow-1; i++) {
					//周囲を０で囲む
					Arrays.fill(field[i], 1);//その行の全要素に 1 を格納
					for(int j=0; j < flCol-1; j++) {
						field[0][j] = 0;//上端の行
						field[ flRow-1 ][j] = 0;//下端の行

						field[i][j] = (Math.random() < 0.2) ? 0 : 1;
					}
					field[i][0] = 0;//左端の列
					field[i][ flCol-1 ] = 0;//右端の列
				}
			}



			private void syokiiti() {//autoField
				//main舞台設定時、キャラの初期配置
		
				zeromasu();
				//lastCheck引っかかり、やり直しの時のエラー対策初期化----
				for(int i=0; i < fstPds.length; i++) {
					//Point2D[4] fstPds:キャラ初期位置
					fstPds[i] = null;
					sndPds[i] = null;
				}
				fs = 0;
				//----------------------------
		
				int bkcnt = 0;//無限ループ防止
		
				while( Arrays.asList(fstPds).contains(null) ) {//すべてにnullがなくなるまで
					row = (int)(Math.random() * flRow);
					col = (int)(Math.random() * flCol);
		
					if( !zeroList.contains(new Point2D(row, col)) &&
						!Arrays.asList(fstPds).contains(new Point2D(row, col)) &&
						fstPds[fs] == null
					) {//ゼロマスでない。他と同じ場所でない
						//初期配置
						fstPds[fs] = new Point2D(row, col);
						sndPds[fs] = new Point2D(row, col);
						field[row][col] = 2;
						
						fs++;
						
					} else {}
		
					bkcnt++;
					if(bkcnt >= flRow * flCol * 50) {
						print("SYOKIITI BREAK 1 ");
						break; 
					}//無限ループ防止保険
				}
		
				//初期配置を歩けるマスリストに格納
				for(int k=0; k < fstPds.length; k++) {
					walkSet.add(fstPds[k]);
					fstbo[k] = false;//初期化
				}
		
		
				//各初期配置からスタートしnextPosにより適当に歩き回り、別の初期位置まで行く
				//その際、歩けるマスリストwalkSetに格納していく
				//０マスによって孤立しないように
				for(int j=0; j < fstPds.length; j++) {
		
					bkcnt = 0;
					
					//現在(sndPds == fstPds)である。
					//nextPos(Point2D)によって(sndPds != fstPds)になるが
					//sndPds[0] == fstPds[max]
					//sndPds[1] == fstPds[0]
					//sndPds[2] == fstPds[1]、となるように処理する
					while(fstbo[j] == false) {//すべてtrueになるまで
						bkcnt++;
		
						fstPds[j] = nextPos(fstPds[j]);
		
						//fstPdsの各POSが他のPOSまで行けるように
						if(j == fstPds.length-1) {//fstPds[max]
							if( sndPds[0].equals(fstPds[j]) ) {//sndPds[0].equals(fstPds[max])
								fstbo[j] = true;
							}
						} else if( sndPds[j+1].equals(fstPds[j]) ) {//
							fstbo[j] = true; 
						} else {
							fstbo[j] = false; 
						}
		
		
						if(bkcnt >= flRow * flCol * 50) {
							print("SYOKIITI BREAK 2 ");
							break; 
						}//無限ループ防止保険。fstboが全てtrueでなければ別メソッドで繰り返される
					}
		
				}
			}


	private Point2D nextPos(Point2D pd) {//syokiiti
		//walkSet移動可能な場所のリストに加え、次の位置ポイントを返す
		
		int pr = (int)pd.getX();
		int pc = (int)pd.getY();
		int rdm1 = (int)(Math.random() * 3) - 1;//-1,0,1
		int rdm2 = (int)(Math.random() * 3) - 1;

		if(rdm1 != 0) {//row移動

			if( field[ pr + rdm1 ][ pc ] >= 1 ) {//マスの属性が０でない
				pd = pd.add(rdm1, 0);
				walkSet.add(pd);
			}

		} else if(rdm2 != 0) {//col移動。rdm1が０である

			if( field[ pr ][ pc + rdm2 ] >= 1 ) {
				pd = pd.add(0, rdm2);
				walkSet.add(pd);
			}

		}
		return pd;//rdm1,rdm2共に０なら何も変化なしで引数のpdを返す
	}


	private void zeromasu() {//フィールドの０のマス。zeroListとzeroSize,panel[][]の属性を決める
		zeroList.clear();//このメソッドに含むか迷う

		for(int k=0; k < flRow; k++) {
			for(int j=0; j < flCol; j++) {
				if(field[k][j] == 0) {
					zeroList.add( new Point2D(k,j) );
					panel[k][j] = 0;
				} else {
					panel[k][j] = 1;
				}
			}
		}

		zeroSize = zeroList.size();
	}



	//描画準備ーーーーーーーーーーーーーーーー

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
			return kitenX-( i *hakoW/2)+( j *hakoW/2);
		}
		private double setY(int i, int j) {
			return kitenY+( j *hakoW/4)+( i *hakoW/4);
		}
		
	//舞台のイメージと並び方を設定する
	private void masuNarabe() {//action

		//zeromasu();

		for(int i=0; i < flRow; i++) {
			for(int j=0; j < flCol; j++) {
				hakoView = new HakoView();
				hakoView.setId(""+0);//並び替えで使う
				hakoView.setPos(i, j);
				//hakoImgの半分ずれ、jが増えるごとに半分ずれる
				coordChange(hakoView, i, j);


				//行と列の番号振りだけhakoViewには関係ない
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


				switch(field[i][j]) {
					case 0: hakoView.setImage(hakoImg[0]); break;
					case 1: hakoView.setImage(hakoImg[1]); break;
					case 2: hakoView.setImage(hakoImg[2]); break;
					default: break;
				}
				p.getChildren().add(hakoView);
			}//forj,end
		}//fori,end

	}


	//=============================================

	public void panelPrint() {
		//fl.panelをわかりやすくプリント
		System.out.println("===== FL.PANEL =====");
		for(int i=0; i < panel.length; i++) {
			for(int j=0; j < panel[i].length; j++) {
				//桁数によるずれちょうせい
				if(panel[i][j] < 10 ) {//一桁
					System.out.print("  " + panel[i][j]);
				} else {
					System.out.print(" " + panel[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println("===== FL.PANEL END =====");
	}


	private void print(Object obj, Object obj2) {
		System.out.println("  FIELD MASU  " + obj + obj2);
		System.out.println();
	}

	private void print(Object obj) {//Overrode
		System.out.println("  FIELD MASU  " + obj);
		System.out.println();
	}


	public void printArr(List arr, String str) {//汎用メソッド
		for(int i=0; i < arr.size()-1; i++) {
			System.out.println( str +" "+ i +" "+ arr.get(i) );
		}
	}
	public void printArr2(List<Node> arr, String str) {//汎用メソッド
		for(int i=0; i < arr.size()-1; i++) {
			System.out.println( str +" "+ i +" "+ arr.get(i).getId() );
		}
	}

	//=============================================


}
	//==============================================	




//クリックイベント用にrow,colを追加
class HakoView extends ImageView {
	int row;
	int col;

	public HakoView() {

	}

	public HakoView(Image image) {//Imageを指定
		setImage( image );
	}

	public HakoView(String path) {//Imageのpathだけ指定
		setImage( new Image(new File( path ).toURI().toString()) );
	}

	//---------------------------------

	protected void setPos(int i, int j) {//
		row = i;
		col = j;
	}



}//class.end








