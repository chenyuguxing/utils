package com.test.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class FileTest {
	@Test
	public void testFileJS() throws Exception{
		String path = "F:\\guofang.txt";
		InputStreamReader in = new InputStreamReader(new FileInputStream(path),"gbk");
		BufferedReader bufferReader = new BufferedReader(in);
		String lines;
		StringBuffer sb = new StringBuffer();
		int count = 01;
		while ((lines = bufferReader.readLine()) != null) {
			if(lines.length()>0){
				sb.append("\""+count+","+lines+"\""+",");
				if(count%10==0){
					System.out.println(sb.toString());
					sb = new StringBuffer();
				}
				count++;
			}
		}
		System.out.println(sb.toString());
	}
	@Test
	public void testFile1() throws Exception{
		String path = "F:\\guofang.txt";
		InputStreamReader in = new InputStreamReader(new FileInputStream(path),"gbk");
		BufferedReader bufferReader = new BufferedReader(in);
		String lines;
		Set<String> set = new HashSet<String>();
		List<String> list = new ArrayList<String>();
		while ((lines = bufferReader.readLine()) != null) {
			set.add(lines);
		}
		for(String s : set){
			System.out.println(s);
		}
	}
	
	
	/**
	 * 提取汉字
	 * @throws Exception 
	 */
	@Test
	public void getCh() throws Exception{
		String regex="([\u4e00-\u9fa5]+)";
		String path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\topic";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\member";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\magic";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\index";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\include";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\forum";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\csi";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\t\\cms\\www\\blue\\forum";
		transform(path);
	}
	
	public static boolean transform(String fromPath) throws Exception {  
        File ftmp = new File(fromPath);  
        if (!ftmp.exists()) {  
            return false;  
        }  
        // 如果是文件，则转换，结束  
        if (ftmp.isFile()) { 
        	//if(ftmp.getName().endsWith("html")){
        		readFile(fromPath);
        	//}
            return true;  
        } else {  
            // 查找目录下面的所有文件与文件夹  
            File[] childFiles = ftmp.listFiles();  
            for (int i = 0, n = childFiles.length; i < n; i++) {  
                File child = childFiles[i];  
                String childFrom = fromPath + "\\" + child.getName();  
  
                transform(childFrom);  
            }  
        }  
        return true;  
    }  
	
	public static void readFile(String path) throws Exception{
		File file = new File(path);
		InputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in, "utf-8");
		BufferedReader bufferReader = new BufferedReader(reader);
		String lines;
		String regex="([\u4e00-\u9fa5]+)";
		Pattern pattern = Pattern.compile(regex);
		Set<String> set = new HashSet<String>();
		while ((lines = bufferReader.readLine()) != null) {
				//System.out.println(lines.substring(3, lines.length()));
				//System.out.println(lines.substring(0, 3));
			Matcher matcher = pattern.matcher(lines);
			if(matcher.find()){
				//System.out.println(matcher.group(0));
				set.add(matcher.group(0));
			}
		}
		for(String i : set){
			System.out.println(i);
		}
	}
	
	@Test
	public void readFile() throws Exception{
		String path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\languages\\jeebbs_tpl\\messages_zh_CN.properties";
		path = "E:\\eclipsework\\workspace4.5\\bbs\\WebContent\\WEB-INF\\languages\\jeebbs_front\\messages_zh_CN.properties";
		File file = new File(path);
		InputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in, "utf-8");
		BufferedReader bufferReader = new BufferedReader(reader);
		String lines;
		List<String> keys = new ArrayList<String>();
		while ((lines = bufferReader.readLine()) != null) {
				//System.out.println(lines.substring(3, lines.length()));
				//System.out.println(lines.substring(0, 3));
			//System.out.println(lines);
			if(lines.contains("=")){
				keys.add(lines.split("=")[0]);
			}
		}
		
		//System.out.println(keys);
		
		InputStreamReader in2 =  new InputStreamReader(new FileInputStream(new File(path)));
		Properties properties = new Properties();
		properties.load(in2);
		
		for(String key : keys){
			System.out.println(properties.get(key));
		}
		
	}
	@Test
	public void testFileEncoding() throws Exception{
		File file = new File("E:\\eclipsework\\workspace4.5\\pro2015001\\WebContent\\res\\js\\SurveyRazor.js");
		InputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in, "utf-8");
		BufferedReader bufferReader = new BufferedReader(reader);
		String lines;
		while ((lines = bufferReader.readLine()) != null) {
			if(lines.length()>2)
				System.out.println(lines.substring(3, lines.length()));
				//System.out.println(lines.substring(0, 3));
			//System.out.println(lines);
		}
		
		
	}
	
	@Test
	public void testFile() throws IOException{
		
		File file = new File("f://test.txt");
		if(file.exists()){
			file.delete();
		}else{
			file.createNewFile();
		}
	}
	
	@Test
	public void testPrintf(){
		System.out.println(System.out.printf("111", "111"));
	}
	
}
