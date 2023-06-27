package monarch;

import java.io.*;
import java.util.*;


public class Mcon {

	public static boolean btn = true;
	private static int cnt = 0;

	public static void cntUp(int lim) {
		cnt++;
		if(cnt < lim) {
			off();
		} else {
			on();
			cnt = 0;
		}
	}

	public static void on() { btn = true; }
	public static void off() { btn = false; }
	
}