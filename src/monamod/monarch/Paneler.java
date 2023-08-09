package monarch;

import java.io.*;
import java.util.*;

import javafx.geometry.Point2D;


import engine.QuickUtil;
import unit.Unit;

/*use a
FieldMasu
BuildTask
Susumukunまだ使ってない
UnitManager
*/


public class Paneler {

	static public HashMap<Point2D, Paneler> panelMap = new HashMap<Point2D, Paneler>();
// 	int row, col;
// 	ImageView view;
	boolean human = false;
	boolean house = false;
	int team = 0;;
	int objNum = 0;

	boolean ride = true;//乗っていいか
	boolean build = true;//建てていいか

	public Paneler() {}
	public Paneler(HashSet<Point2D> walkSet) {
		panelMap.clear();
		panelMapping(walkSet);
	}

		private void panelMapping(HashSet<Point2D> walkSet) {
			for(Point2D pd : walkSet) {
				panelMap.put(pd, new Paneler());
			}
		}
		
	static private Paneler pn(Point2D pd) {
		return panelMap.get(pd);
	}

	static public boolean setBuild(Point2D pd, int team) {
		return pn(pd).setBuild(team);
	}
	static public boolean setRide(Point2D pd, int team, int objNum) {
		return pn(pd).setRide(team, objNum);
	}
	static public void resetBuild(Point2D pd) {
		pn(pd).resetBuild();
	}
	static public void resetRide(Point2D pd) {
		pn(pd).resetRide();
	}

	static public void resetUnit(Point2D pd, int objNum) {
		if(objNum == 1) {
			pn(pd).resetRide();
		} else {
			pn(pd).resetBuild();
		}
	}

	static public void unsetUnit(Point2D pd) {
		pn(pd).unsetUnit();
	}

// 	static public void allCheck() {
// 		for(Point2D pd : panelMap.keySet()) {
// 			nowState();
// 		}
// 	}

	private boolean setBuild(int team) {
		build = build( team );
		if(build) {
			house = true;
			setParam(team, 2);
		}
		return build;
	}

	private boolean setRide(int team, int objNum) {
		ride = ride( team, objNum );
		if(ride) {
			human = true;
			setParam(team, objNum);
		}
		return ride;
	}

		private void resetBuild() {
			house = false;
			nowState();
		}
		private void resetRide() {
			human = false;
			nowState();
		}

	private void setParam(int team, int objNum) {
		this.team = team;
		this.objNum += objNum;
	}

	private void unsetUnit() {
		this.human = false;
		this.house = false;
		objNum = 0;
		team = 0;
		ride = true;
		build = true;
	}

	private boolean ride(int team, int objNum) {
		boolean kari = false;
		if(this.objNum == 0) {
			kari = true;
		} else {
			if(this.team == team) {
				kari = true;
			}
		}
		return kari;
	}

	private boolean build(int team) {
		boolean kari = false;
		if(this.objNum >= 2) {
			kari = false;
		} else {
			if(this.team == team || this.team == 0) {
				kari = true;
			}
		}
		return kari;
	}



	private void nowState() {
		if(house) {
			if(human) {
				objNum = 3;
			} else {
				objNum = 2;
			}
		} else {
			if(human) {
				objNum = 1;
			} else {
				unsetUnit();
			}
		}
	}
	
	QuickUtil qu = new QuickUtil(this);//サブクラスも大丈夫
	public void print(Object... objs) {
		qu.print(objs);
	}

}