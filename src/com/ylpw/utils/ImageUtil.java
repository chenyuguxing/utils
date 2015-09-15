package com.ylpw.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author ZJJ
 * @Date Mar 26, 2011
 * @Time 2:22:36 PM 类说明
 */
public class ImageUtil {
	//使用原zimg.jar包把以下两方法放开...
//	public static String cutImage(String srcPath, int width, int height) throws IOException{
//		File srcFile = new File(srcPath);
//		String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);
//		String paths = srcPath.substring(0, srcPath.lastIndexOf("/")+1)+"x/"+ srcPath.substring(srcPath.lastIndexOf("/")+1, srcPath.length());
//		return com.zjj.core.web.utils.ImageUtil.createMiniImage(srcFile,fileSufix,paths,width,height);
//	}
//	public static String cutImageNew(String srcPath, int width, int height,int j) throws IOException{
//		File srcFile = new File(srcPath);
//		String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);
//		String img =srcPath.substring(srcPath.lastIndexOf("/")+1, srcPath.length());
//		String paths = srcPath.substring(0, srcPath.lastIndexOf("/")+1)+"AfterTreatment/"+ img.substring(0,img.lastIndexOf("."))+"-"+j+img.substring(img.lastIndexOf("."));
//		System.out.println(paths+"><><><><><><><><><>");
//		return com.zjj.core.web.utils.ImageUtil.createMiniImage(srcFile,fileSufix,paths,width,height);
//	}
	
	
	//使用ImageMagick切图把以下两方法放开...
	public static String cutImage(String srcPath, int width, int height) throws IOException{
		System.setProperty("jmagick.systemclassloader","no");
		String paths = srcPath.substring(0, srcPath.lastIndexOf("/")+1)+"x/"+ srcPath.substring(srcPath.lastIndexOf("/")+1, srcPath.length());
		System.out.println("cutImage srcPath-------:"+srcPath);
		System.out.println("cutImage paths---------:"+paths);
		com.zjj.core.web.utils.ImageUtil.createMiniImage(width,height,5,90,srcPath,paths);
		return paths;
	}
	public static String cutImageNew(String srcPath, int width, int height,int j) throws IOException{
		System.setProperty("jmagick.systemclassloader","no");
		String img =srcPath.substring(srcPath.lastIndexOf("/")+1, srcPath.length());
		//System.out.println("cutImageNew img<><><><><><><><><><>1111:"+img);
		if(null!=img && !"".equals(img)){
			img = img.toLowerCase().replaceAll(".bmp", ".jpg").replaceAll(".png", ".jpg");
		}
		//System.out.println("cutImageNew img<><><><><><><><><><>2222:"+img);
		String paths = srcPath.substring(0, srcPath.lastIndexOf("/")+1)+"AfterTreatment/"+ img.substring(0,img.lastIndexOf("."))+"-"+j+img.substring(img.lastIndexOf("."));
		//System.out.println("cutImageNew srcPath<><><><><><><><><><>:"+srcPath);
		//System.out.println("cutImageNew paths<><><><><><><><><><>:"+paths);
		com.zjj.core.web.utils.ImageUtil.createMiniImage(width,height,5,90,srcPath,paths);
		return paths;
	}
	/**
	 *
	 * 原图转换为jpg图片
	 * @Author:T.L
	 * @Date:2013-1-31 下午5:03:35
	 * @param srcPath 原图地址
	 * @param minName 转换为会默认给转换后图加 _x 名称,如需要请自行传值
	 * @param DPI 图片存储DPI,默认96
	 * @return
	 *
	 */
	public static String copyImageToJpg(String srcPath,String minName,Integer DPI){
		System.setProperty("jmagick.systemclassloader","no");
		String img =srcPath.substring(srcPath.lastIndexOf("/")+1, srcPath.length());
		if(null!=img && !"".equals(img)){
			img = img.toLowerCase().replaceAll(".bmp", ".jpg").replaceAll(".png", ".jpg");
		}
		minName = (null==minName || "".equals(minName))?"x":minName;
		String paths = srcPath.substring(0, srcPath.lastIndexOf("/")+1)+ img.substring(0,img.lastIndexOf("."))+"_"+minName+img.substring(img.lastIndexOf("."));
		if(null==DPI || 0==DPI){DPI=96;}
		com.zjj.core.web.utils.ImageUtil.createImage(5, DPI, srcPath, paths);
		return paths;
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public static String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		//String name = fileName.substring(0, position);
		String extension = fileName.substring(position);
		//return name + formatDate + random + extension;
		return formatDate + random + extension;
	}

	public static void main(String[] args) {
	//	com.zjj.core.web.utils.ImageUtil.createMiniImage(100,100,5,85,"f://1.bmp","f://2.jpg");
	//	String s = "E:\\javatool\\apache-tomcat-7.0.28\\webapps\\h228cn_v4\\upload/2013/01/26/1359198749109_s8k7.bmp";
	//	System.out.println(s.replaceAll("/", "\\\\"));
	//	ImageInfo a = new ImageInfo(s);
	//	a.setQuality(90);
		
		System.setProperty("jmagick.systemclassloader","no");
		
	}

}
