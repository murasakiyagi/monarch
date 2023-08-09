package monarch;

import java.io.*;
import java.util.*;


/* 実装
FtcntMapping


*/

public interface Monaject {
	public void registerObserver(Monaserver o);//オブザーバーリストに追加
	public void removeObserver(Monaserver o);//リストから削除
	public void notifyObserver();//変更通知
}