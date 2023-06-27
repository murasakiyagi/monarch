/*使い方
//使用するソースファイルに
	import img.Img;

	Img img = new Img();
	img.printAllPath();
	ImageView view = img.getView(img.allPath(i));

*/
/*コンパイル

当クラス、コンパイル時のコード
	cd $IMG
	javac ~中略~ Img.java($IMGのPATHあり)
	*ソースパス、クラスパスはいらない

使用クラスの場合
	javac ~中略~ -cp $SOURCE [使用クラス名]

*/

package pict;

import java.io.*;
import java.util.*;
import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Img {

	private ClassLoader cl;
	private URL url;
	
	
	private String absoPath = "/Users/yngt/program/myjava/source/img/pict/";
	
	private String[] allPath;
	private String[] hakoPath;
	private String[] humanPath;
	private String[] housePath;
	private String[] allUrls;
	private String[] hakoUrls;
	private String[] humanUrls;
	private String[] houseUrls;
	
	
	//コンストラクタ
	public Img() {
		
		cl = this.getClass().getClassLoader();

		hakoPath = new String[] {
			"nanamehakoao.png",
			"nanamehako.png",
			"nanamehakoaka.png"
		};
		
		humanPath = new String[] {
			"jurokuA32+3.png",
			"jurokuB32+3.png",
			"p128.png",
			"man04.png",
			"teki.png",
			"man05.png"
		};
		
		housePath = new String[] {
			"kaoku01.png",
			"kaoku02.png",
			"kaoku03.png"
		};
		
		allPath = new String[] {
			"nanamehakoao.png",
			"nanamehako.png",
			"nanamehakoaka.png",
			"jurokuA32+3.png",
			"jurokuB32+3.png",
			"p128.png",
			"man04.png",
			"teki.png",
			"man05.png",
			"kaoku01.png",
			"kaoku02.png",
			"kaoku03.png"
		};
		
		allUrls = getUrls(allPath);
		hakoUrls = getUrls(hakoPath);
		humanUrls = getUrls(humanPath);
		houseUrls = getUrls(housePath);
		
	}//コンスト
	
	public void getUrl() {
		url = cl.getResource("teki.png");
			System.out.println("URL  "+ url );
			System.out.println("CL.RESRC 1 "+ cl.getResource("teki.png"));
			System.out.println("CL.RESRC 2 "+ cl.getResource("pack/teki.png"));
	}
	
	private String[] getUrls(String[] paths) {
		int len = paths.length;
		String[] karis = new String[len];
		
		for(int i=0; i < len; i++) {
			url = cl.getResource(paths[i]);
			karis[i] = url.toString();
		}
		
		return karis;
	}
	
		private Image[] getImages(String[] urls) {
			int len = urls.length;
			Image[] karis = new Image[len];
			Image kari;
			
			for(int i=0; i < len; i++) {
				kari = new Image( urls[i] );
				karis[i] = kari;
			}
		
			return karis;
		}
	
	
	public String[] getHakoUrls() {
		return hakoUrls;
	}
	
		public Image[] getHakoImages() {
			return getImages(hakoUrls);
		}
	
	public String[] getHumanUrls() {
		return humanUrls;
	}
	
	public String[] getHouseUrls() {
		return houseUrls;
	}
	
	public String[] getAllUrls() {
		return allUrls;
	}
	
	public String getUrl(int num) {
		return allUrls[num];
	}
	
	public Image getImage(int num) {
		return new Image(getUrl(num));
	}
	
	public ImageView getView(String path) {
		return new ImageView( getImg(path) );
	}
	
	public Image getImg(String path) {//メインクラスでimg.xxPath
		return new Image(getFile(path).toURI().toString());
	}
	
	public File getFile(String path) {
		String newPath = absoPath + path;
		return new File(newPath);
	}

	public String getFilePath(File f) {
		return f.toURI().toString();
	}
	
	
	public void printAllPath() {
		for(String path : allPath) {
			print(path);
		}
	}
	
	private void print(Object obj) {
		System.out.println("  IMG  " + obj);
	}

	private void print(Object obj, Object obj2) {
		System.out.println("  IMG  " + obj +"  "+ obj2);
	}	
}

