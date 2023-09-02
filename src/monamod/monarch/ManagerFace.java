package monarch;

import java.io.*;
import java.util.*;
import engine.QuickUtil;


public interface ManagerFace {

	public void register(ManagerFace mf);
	public void remove(ManagerFace mf);
	public void update();
	public void notifyMana();


}