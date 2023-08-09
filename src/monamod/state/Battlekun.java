package state;

import java.io.*;
import java.util.*;

//オリジナルパック
import unit.Unit;//--source-pathではなく--class-pathで/senryakuを指定

public class Battlekun extends Statekun {

	//コンストラクタ
	public Battlekun() {}
	public Battlekun(Unit un, Work work) {
		//this.un = un;
		super(un, work);
		this.name = "BTL";
	}

//メソッド------------------
	@Override
	public void run() {
		//un.getSSM().modoru();//近すぎる時
		
		guilt();
//			print("TAISENAITE", work.getBtlHp());
	}

	private void punish(boolean b) {//懲らしめる
		if(work.getBtlHp() < 10) {
			this.goaled(b);
				print("punish", (int)work.getBtlHp());
		} else {
			damage();
				print("damage");
		}
	}

	@Override
	public void reset() {//外部にあるので注意
		//work.setBtlUn(null);
	}

		private void guilt() {//罪悪感
			if((un.getHp() <= 0) | (work.getBtlHp() <= 0)) {
				this.goaled(false);
			} else {
				damage();
			}
		}


	@Override
	protected void goaled(boolean boo) {//目的地についた後
		super.goaled(boo);
	}

	private void damage() {
		damage(work.getBtlHp() / 100);
	}

		private void damage(double dmg) {//自分が受けるダメージ
			un.addHp(-dmg);
		}
	


	//------------------------------------------

	private void printArr(String str, ArrayList<?> arr) {
		for(Object obj : arr) {
			print(str, obj);
		}
	}



}