//自動追尾、最短距離アルゴリズムほぼ完成品
//一部不具合があるが、FieldMasuの生成やUnitのtgtCbなどが原因かと

package monarch;

import java.io.*;
import java.util.*;


import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.util.*;
import javafx.concurrent.*;
import javafx.event.*;

//オリジナル
import engine.QuickUtil;
import engine.P2Dint;
import unit.Unit;

//斜めマス配置。ｉとｊを入れ替えで逆に。
//rectの色柄で配置の確認が取れる
//Mijikai6からippo部分を変更


// guide呼び出し頻度がすこぶる高いのでなんとかする
// ftcntMappingはよしとする




public class Scouter {


	P2Dint startPd;//移動する者の初期位置
	P2Dint targetPd;//目的地
	//slf -> gst -> rip
	P2Dint tgtPd, slfPd, gstPd, ripPd, trnPd;

	boolean search = true;//捜査するか？

	Map<P2Dint, Integer> ftcntMap = new HashMap<>();//座標と歩数を紐づけ

	//現在地slfRow,slfColは含まれない
 	List<P2Dint> walkList = new ArrayList<P2Dint>();//歩道。このクラスの目的

	//行けない点
	//オリジナルクラスFieldMasuから引き継ぐ値
 	List<P2Dint> ftprList = new ArrayList<P2Dint>();//踏んだところ。一時的に壁になる
	List<P2Dint> stopList = new ArrayList<P2Dint>();//立ち止まって（次に進めなくて）stopcnt >= 4になった所。
	//List<P2Dint> keepOfList = new ArrayList<P2Dint>();//zero,ftpr,stopの総合。面倒だからやめ

	//行ける十字線
	List<P2Dint> crosListG = new ArrayList<P2Dint>();//ゴール地点から十字線
	List<P2Dint> crosListS = new ArrayList<P2Dint>();//スタート地点から
	List<P2Dint> crosListT = new ArrayList<P2Dint>();//transitから
	List<P2Dint> crosListR = new ArrayList<P2Dint>();//探索中のマスから
	
 	List<P2Dint> tranList = new ArrayList<P2Dint>();//通過点（曲がり角）

	int ftcntSlf = 0;//実体の歩数。ftcntMapping()により取得
	int ftcntRip = 0;//十字線上の歩数。波紋ripの距離。masukyori()
	int stopcnt;//一歩進めなかったカウント
	Integer[] stopRansu = { -1, -1,- 1, -1 };//Arrays.asListするとList<?>になる。?はObject
	Integer ransu = -1;


	//オリジナルクラスFieldMasuから引き継ぐ値
	static private List<P2Dint> zeroList;

	private void zenkesi() {
		walkList.clear();
		ftcntMap.clear();
		
	}


	//コンストラクタ=========================================
	public Scouter() {
		this.zeroList = P2Dint.cast( FieldMasu.zeroList );
	}
	public Scouter(List<Point2D> zeroList) {
		this.zeroList = P2Dint.cast( zeroList );
	}
	//=======================================================


	public List<Point2D> getZeroList() {
		return P2Dint.recast(zeroList);
	}

	//略語メソッド
	private P2Dint p2d(int ro, int co) {
		return new P2Dint(ro, co);
	}

		private void error() {
			print("    ERROR    ERROR    ERROR");
		};



//-----------------------------------------------------------
//各オブジェのスタート地点から、直線状のマスリスト化

	private void bodySoul() {//離れた精神と体を重ねる
		gstPd = slfPd.copy();
	}
	
	public void furidasi() {//歩数を保持しつつ、振出しに戻る
		ftcntSlf = 0;
		bodySoul();
	}


	private void addAbsent(List<P2Dint> arr, P2Dint pd) {//唯一のしか入れない
		if( !arr.contains(pd) ) { arr.add(pd); }
	}
	
		private void addAllAbsent(List<P2Dint> arr1, List<P2Dint> arr2) {
			for(P2Dint pd : arr2) { addAbsent(arr1, pd); }
		}
	



	//壁にぶつかるまで進む十字線。超重要メソッド
	
	public void zeroCrossAdd(List<P2Dint> arr, P2Dint pi) {
		//初期位置（引数で受け取った位置）も加入
		arr.clear();
		for(int i=-1; i <= 1; i=i+2) {//下左、上右の二回分
			loopAdd(arr, pi, i, 0);
			loopAdd(arr, pi, 0, i);
		}
	}
	
		private void loopAdd(List<P2Dint> arr, P2Dint pi, int x, int y) {//go.add(i, 0);
			P2Dint kari = pi.copy();
			while( !zeroList.contains(kari) ) {//壁にぶつかるまでループ
				addAbsent(arr, kari.copy());//代入してから
				kari = kari.add(x, y);//移動
			}
		}


	//P2Dint p の上下左右の４点をftcntMapに追加
	//このメソッドが起こる地点は立ち入り可能が保証されている(zeroマスでないこと)
	public void ftcntMapping(P2Dint pd) {
		mapping(ftcntMap, pd, ftcntSlf);
	}
	
	public void mapping(Map<P2Dint, Integer> map, P2Dint pd, int ftCnt) {
		//順番、rowマイナス方向、colマイナス方向、rowプラス、colプラス
		for(int i=-1; i <= 1; i=i+2) {
			loopPut(map, pd, ftCnt, i, 0);
			loopPut(map, pd, ftCnt, 0, i);
		}
	}

		private void loopPut(Map<P2Dint, Integer> map, P2Dint pd, int ftCnt, int x, int y) {
			int mk;
			P2Dint cngPd = pd.copy();

			//cngPdはwhile内で０マスに当たるまで移動する。pdは不変
			while( !zeroList.contains(cngPd) ) {
				mk = masukyori( cngPd, pd );	//任意の座標と幽体の距離
				ftcntPutAbsent( map, cngPd.copy(), (ftCnt + mk) );	//ftcntMapに幽体の座標と歩数をプット
				cngPd = cngPd.add(x, y);//＜ーーーーーーーーー
			}
		}
		
			private void ftcntPutAbsent(Map<P2Dint, Integer> map, P2Dint pd, int cnt) {//唯一のしか入れないけれど
				map.putIfAbsent( pd, cnt );//putIfAbsent()はkeyと値を持っていない場合に追加する
				if( map.get( pd ) >= cnt ) {//キー(p2d)のバリューより今回の数値以下なら。以下にするのは可能性を広げるため
					map.put( pd, cnt );
				}
			}


//----------------------------------------------------------
//ランダムに動いて目標を見つける


	public int masukyori(P2Dint slfPd, P2Dint tgtPd) {//マスとマスの距離を測る。壁は無視される
		int ro1 = slfPd.x();		int co1 = slfPd.y();
		int ro2 = tgtPd.x();		int co2 = tgtPd.y();;
		return Math.abs(ro1 - ro2) + Math.abs(co1 - co2);
	}

		public int masukyori(Point2D slfPd, Point2D tgtPd) {
			return masukyori(P2Dint.cast(slfPd), P2Dint.cast(tgtPd));
		}


	private int x(Point2D pd) {
		return (int)pd.getX();
	}
	private int y(Point2D pd) {
		return (int)pd.getY();
	}
	
	private boolean arrConta(List<P2Dint> myArr, List<P2Dint> tgtArr) {
		boolean kari = false;
		for(P2Dint pd : myArr) {
			if(tgtArr.contains(pd)) { kari = true; }
		}
		return kari;
	}

//----------------------------------------------------------

	//追いかける
	//オーバーロード
	public List<Point2D> guide(Unit un) {
		int myR = un.getRow();		int myC = un.getCol();
		int tgtR = x(un.getTgtPd());	int tgtC = y(un.getTgtPd());
		return guide( p2d(myR, myC), p2d(tgtR, tgtC) );
	}

	public List<Point2D> guide(Unit un, Point2D pd) {
		int myR = un.getRow();		int myC = un.getCol();
		int tgtR = x(pd);	int tgtC = y(pd);
		return guide( p2d(myR, myC), p2d(tgtR, tgtC) );
	}

	public List<Point2D> guide(Point2D pd, Point2D pd2) {
		return guide( P2Dint.cast(pd), P2Dint.cast(pd2) );
	}


	boolean through = false;//スルー
	public List<Point2D> guide(P2Dint herePd, P2Dint therPd) {
// 			print("SCOUTER");

		
		//自分の場所から敵などの場所
		this.slfPd = herePd;
		this.tgtPd = therPd;
		bodySoul();//初期化。gst=slf

		ftcntSlf = 0;//初期化
		ftcntMapping(gstPd);//幽体十字上の歩数と点をftcntMapに格納。スタート地点
		ftprList.add(gstPd.copy());

		zeroCrossAdd(crosListS, gstPd);//スタート地点の十字線
		zeroCrossAdd(crosListG, tgtPd);//ゴールから十字
		crosListT.clear();//必要。なぜかは謎
		tranList.clear();//初期化
		
		
//--------------------------------------
//スタート地点からゴールかトランジットが見えればおしまい

		int brkcnt = 0;//無限ループ防止
		while( brkcnt < 10000 ) {
			brkcnt++;

			if( startToGoal() ) { break; }

			startToCross();

			if(through) { break; }//while

//through == falseなら以下の処理をする
//----------------------------------------------------------
//幽体を進める。波紋を広げる

			//ransu = (int)(Math.random()*4);
			ippoRansu();
			//ここで同じ数が出続けると行ける方向があってもstopListに入ってしまう
			//ippo幽体が一歩進み、ftprList,ftcntMap,crosListRを書き換える
			ippoSusumu();

//----------------------------------------------------------
//幽体が一歩進んだ後

			rippleToCross();

//-------------------------------------------------------------
			stopCount();

		}//while,turn
//===========================================
//}//if(bkcnt % 10 = 0) {


		if(brkcnt >= 10000) {//無限ループ防止
			print("~~~~~~~~~MUGEN LOOP DAKARA OK~~~~~~~~~");
			search = true;
		} else {
			search = false;

			//目標、通過点、始点をつなぐ-----------------------
			connect();
			//-------------------------------------------------

			//最終チェック-------------------------------------
			lastCheck();
			//-------------------------------------------------


		}

		if( search ) {//無限ループ防止で引っかかった
			walkList.clear();
				print("if(search)", walkList.size());
		}

		ftprList.clear();
		stopList.clear();
		bodySoul();
		tranList.clear();
		ftcntMap.clear();

		//結果
		//print("KEKKA ", slfPd);
		//print("to", tgtPd, walkList);
		
		return P2Dint.recast( walkList );

	}//guide,end




//ippo----------------------

	public int ippoRansu(Integer[] array, Integer num) {
		List<Integer> ransuArr = Arrays.asList(array);
		while( ransuArr.contains(num) ) {//stopRansuリストに同じ乱数があればもう一度（ransu初期値=-1）
			num = (int)(Math.random()*4);//0~3
			if( !ransuArr.contains(-1) ) { break; }//リストから初期値が全てなくなったら強制終了。エラー回避
		}
		return num;
	}

	public void ippoRansu() {
		ransu = ippoRansu(stopRansu, ransu);
	}

	private void ransuFormat() {
		for(int i=0; i < stopRansu.length; i++) {
			stopRansu[i] = -1;
		}
		ransu = -1;
	}


// 	List<Point2D> = {(1,0), (0,1)...}を作り、Point2dの分解をippoメソッドに委ねれば、ransu.equals(n){ippo(list.get(n))}の一行でいける
	public void ippoSusumu() {
		if(ransu < 0 || ransu > 3) { print("DEFAULT"); }
		if(ransu.equals(0)) { ippo(1,0); }
		if(ransu.equals(1)) { ippo(0,1); }
		if(ransu.equals(2)) { ippo(-1,0); }
		if(ransu.equals(3)) { ippo(0,-1); }
	}


	public void ippo(int i, int j) {//現在地の隣に行けたら行く
		try {
			//i,j = -1～1
			if( !tripleBarrier( gstPd.add(i,j) ) ) {
				inIppo(i,j);
			} else {
				stopRansu[stopcnt++] = ransu; 
			}
		} catch(NullPointerException ne) {
			System.out.println(ne.getStackTrace());
				print("-------IPPO NULL EXCEPTION---------");
		}
	}


		private void inIppo(int i, int j) {
			//gstPdはこことbodySoulだけで変化
			gstPd = gstPd.add(i,j);//addはこの形("="を使う)にしないと実質変化しない
			ftprList.add(gstPd.copy());//壁になる
			ftcntSlf = ftcntMap.get(gstPd);//その座標の歩数。この時点で必ずNullは無い
			ftcntMapping(gstPd);//Mapにデータを格納または書き換え
			zeroCrossAdd(crosListR, gstPd);//crosListR
			stopcnt = 0;
			//ransuFormat()があってもなくても変わらない。むしろ気持ちカクカクする
		}
		
		
		public boolean tripleBarrier(P2Dint pd) {//接触があればtrue
			if( zeroList.contains(pd) ) { return true; };
			if( ftprList.contains(pd) ) { return true; };
			if( stopList.contains(pd) ) { return true; };
			return false;
		}
		
		private void stopCount() {
			if(stopcnt >= 4) {//四方のいずれも行けない場合、その地点が壁になる
				ftprList.clear();//足跡の壁は無くなる
				stopList.add(gstPd.copy());
				stopcnt = 0;
				ransuFormat();
					//print("STOP CNT", gstPd);
				furidasi();
			}
		}


//end,ippo-----------------------





	private boolean startToGoal() {//スタートとゴールが一直線。トランジットは無い
		return crosListS.contains(tgtPd);
	}


	private void startToCross() {
		//transitと敵初期位置の交差
		for(P2Dint pd : crosListS) {
			//「スタート地点から”見える”所」に、「ゴールから見える所」もしくは「通過点から見える所」がある
			if( crosListG.contains(pd) ) {//G
				discovary(pd);
				break;//for
			} else {
				through = false;
			}//if,end
		}//for,end
	}

		private void discovary(P2Dint pd) {//発見
			//trnPd = pd;
			//addAbsent(tranList, pd);
			tranAdd(pd);
			furidasi();
			through = true;
		}

			//tranListがrow->col->rowのように交互になるように
			private void tranAdd(P2Dint pd) {
				if(tranList.size() >= 2) {
					P2Dint pi0 = tranList.get(tranList.size()-2);//get0
					P2Dint pi1 = tranList.get(tranList.size()-1);//get1
					switchAdd(pi0.x(), pi1.x(), pd.x(), pd);
					switchAdd(pi0.y(), pi1.y(), pd.y(), pd);
				} else {
					addAbsent(tranList, pd);
				}
			}
			
				private void switchAdd(int get0, int get1, int piInt, P2Dint setPi) {
					if( get0 == get1 ) {//rowが同じ
						if( get1 == piInt ) {//さらにrowが同じ
							tranList.set(tranList.size()-1, setPi);//書き換え
								//print("KAKIKAE");
						} else {
							addAbsent(tranList, setPi);
						}
					}
				}

	private void rippleToCross() {
		for(P2Dint pd : crosListR) {//探査中のマスから見える所（波紋）
			//初めて見つけた。通過点が今までない
				//補足。幽体がスタート地点(slfPd)から十字線を出しながら一歩(ippo)ずつ進む。
				//現在地の幽体が出す十字線crosListRに、ゴールの十字線が掛かるとその交点がtransit、つまりtranListに追加される
				//スタート地点に戻り(furidasi)、一歩ずつ進みtransitからの十字線を探し、crosListTとcrosListSが交われば終了する
			//transit十字線を見つけた
			if( crosListG.contains(pd) ) {
				tranAddRip(pd);
				break;//for
			}
		}//foreach,end
	}

		private void tranAddRip(P2Dint pd) {
			//crosListG.clear();
			tranAdd(pd);//
			zeroCrossAdd(crosListG, pd);//crosListTを廃して、Gに追加
			furidasi();
		}
	
	
	
	public void connect() {
		walkList.clear();//初期化。重要
		//addAbsent()を使おうとしたがなんか面倒
		tranList.add(0, tgtPd.copy());//リストの最初に入れる
		tranList.add(slfPd.copy());//リストの最後
		for(int i=tranList.size()-1; i >= 1; i--) {//-1のせいで、slfRow,colはwalkListに入らない
			oneWalk(tranList.get(i), tranList.get(i-1));
		}
	}

	//tranList完成後、呼び出し
	private void oneWalk(P2Dint herePd, P2Dint nextPd) {//slfPdマスをtgtPdマスに一歩ずつ近づける。walkList
		int ro = nextPd.x() - herePd.x();
		int co = nextPd.y() - herePd.y();
		
		forAddSig(herePd, ro, co);
	}
	
		//Math.signum(double):±の符号を得る。1,0,-1。
		private void forAddSig(P2Dint herePd, int ro, int co) {//ro,coのどちらかは０
			int max = Math.max( Math.abs(ro), Math.abs(co) );//繰り返し回数を得るだけ
			for(int i=0; i < max; i++) {//
				herePd = herePd.add( (int)Math.signum(ro), (int)Math.signum(co) );//P2DintのXYの値に足し算
				addAbsent(walkList, herePd.copy());
			}
		}




	private void lastCheck() {
		ScoutTester st = new ScoutTester();
		for(int i=0; i < walkList.size()-1; i++) {
			//次のマスは隣か、０マスに乗らないか
			
			//KYORIエラーが出る場合はtgtPdが途中で死亡した場合と考えられる
			if( masukyori(walkList.get(i), walkList.get(i+1)) != 1 ) {
				print("=========LASTCHECK  KYORI================");
					print("SLF ", slfPd);
					print("TGT ", tgtPd);
					printArr("WALK ", walkList);
					printArr("TRAN ", tranList);
				print("=========================");
				search = true;
				
					st.createField(walkList);
				
				break;
			}

			//ZEROエラーが出るのは、多くはキャラが増えすぎてからなので、処理落ちかと思う
			if(zeroList.contains(walkList.get(i))) {
				print("=========LASTCHECK  ZERO================");
					printArr("ZERO ", walkList);
					print("ftcntMappingは保証されていない");
				print("=========================");
				search = true;
				break;
			}
		}
	}



//--------------------------------------

	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}

// 	void print(Object obj, Object obj2, Object obj3) {
// 		System.out.println("  MIJI  " + obj +" "+ obj2 +" "+ obj3);
// 		System.out.println();
// 	}
// 	
// 	void print(Object obj, Object obj2) {
// 		System.out.println("  MIJI  " + obj +" "+ obj2);
// 		System.out.println();
// 	}
// 
// 	void print(Object obj) {//Overrode
// 		System.out.println("  MIJI  " + obj);
// 		System.out.println();
// 	}

	private void printArr(Object str, List<?> arr) {//汎用メソッド
		for(Object obj : arr) {
			print(str, obj);
		}
	}
	
	private void printVal(Object str, List<?> arr) {
		for(Object obj : arr) {
			P2Dint kari = (P2Dint)obj;
			print(str, kari.x(), kari.y());
		}
	}
	
	
}