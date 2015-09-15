package com.ylpw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @Author ZJJ
 * @Date 2010-2-1
 * @Time 下午02:41:26 类说明
 */
public class PropertiesUtils {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);

	private String propertiesName;

	public PropertiesUtils(String properties) {
		this.propertiesName = properties;
	}

	/* 根据Key 读取Value */
	public String getValue(String key) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.propertiesName);
		Properties p = new Properties();
		try {
			p.load(inputStream);
			return p.getProperty(key);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public String jsp_getValue(String key) {
		
		try {
			FileInputStream in = new FileInputStream(this.propertiesName);
			Properties p = new Properties();
			p.load(in);
			return p.getProperty(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/* 修改或添加键值对 如果key存在，修改 反之，添加。 */
	public void jsp_writeData(String key, String value) {
		Properties p = new Properties();
		//String path = this.propertiesName;
		try {
			File file = new File(this.propertiesName);
			if (!file.exists())
				file.createNewFile();
			FileInputStream in = new FileInputStream(this.propertiesName);
			p.load(in);
			in.close();
			OutputStream outputStream = new FileOutputStream(this.propertiesName);
			p.setProperty(key, value);
			p.store(outputStream, "Item");
			outputStream.close();
		} catch (IOException e) {
			logger.error("Visit " + this.propertiesName + " for updating " + value + " value error", e);
		}
		
	}
	

	/* 修改或添加键值对 如果key存在，修改 反之，添加。 */
	public void writeData(String key, String value) {
		Properties p = new Properties();
		String path = p.getClass().getResource("/").getPath().substring(1, p.getClass().getResource("/").getPath().length()) + this.propertiesName;
		//String path = this.propertiesName;
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.propertiesName);
			p.load(inputStream);
			inputStream.close();
			OutputStream outputStream = new FileOutputStream(path);
			p.setProperty(key, value);
			p.store(outputStream, "Item");
			outputStream.close();
		} catch (IOException e) {
			logger.error("Visit " + this.propertiesName + " for updating " + value + " value error", e);
		}
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}
	
	/**
	 * @description 
	 * 功能描述: Properties 一级目录 [Map] aaa=1,bbb=2 
	 * @author 		  作         者: 张嘉杰
	 * @param	             参         数: 
	 * @return       返回类型: 
	 * @createdate   建立日期：Jul 24, 20138:28:07 PM
	 */
	public Map<String,String> toMap(){
		return get(this.propertiesName);
	}
	
	/**
	 * @description 
	 * 功能描述: Properties 二级目录  [Map包含Map] aaa.a1,aaa.a2,aaa.a3
	 * @author 		  作         者: 张嘉杰
	 * @param	             参         数: 
	 * @return       返回类型: 
	 * @createdate   建立日期：Jul 24, 20138:28:14 PM
	 */
	public Map<String,Map<String,String>> toMapContainMap(){
		return getByType(this.propertiesName);
	}
	
	
	/**
	 * 获取以.为分割的区域类别划分信息，如db1.uname,db1.url;db2.uname,db2.url;
	 * @param propertiesName 资源文件名称
	 * @return 返回  Map 类型的数据结果
	 */
	public static Map<String,Map<String,String>> getByType(String propertiesName){
		Map<String,Map<String,String>> map = new HashMap<String, Map<String,String>>();
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName);
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		Map<String,String> values = null;
		for(Object key : properties.keySet()){
			String property = (String)key;
			int dotIndex = property.indexOf('.');
			String newType = property.substring(0,dotIndex);//类型名
			String value = property.substring(dotIndex+1,property.length());//同类型下的属性名
			String proValue = properties.getProperty(property);//属性值
			values = map.get(newType);
			if(values==null){
				values =new HashMap<String,String>();
			}
			values.put(value, proValue);
			map.put(newType, values);
		}
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return map;
	}
	
	/**
	 * 获取properties文件属性
	 * @param propertiesName 资源文件名称
	 * @return 返回  Map 类型的数据结果
	 */
	public static Map<String,String> get(String propertiesName){
		Map<String,String> map = new HashMap<String, String>();
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName);
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		for(Object key : properties.keySet()){
			String property = (String)key;
			String proValue = properties.getProperty(property);
			map.put(property, proValue);
		}
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return map;
	}

	public static void main(String[] args) {
//		PropertiesUtils p = new PropertiesUtils("config/xiangmu.properties");
		PropertiesUtils p = new PropertiesUtils("E:/fiscalsystemII/apache-tomcat-5.5.26/webapps/guoan/WEB-INF/classes/config/xiangmu.properties");
//		PropertiesUtils p = new PropertiesUtils("D:/workspace/GuoAn/WebRoot/WEB-INF/classes/config/xiangmu.properties");
		/* 项目一 */
//		p.jsp_writeData("xm1.30", "0");
//		p.jsp_writeData("xm1.50", "0");
//		p.jsp_writeData("xm1.80", "0");
//		p.jsp_writeData("xm1.100", "0");
//		p.jsp_writeData("xm1.type", "北京国安队-陕西中建,北京工人体育场,2010.04.18 19:30");	//
//		p.jsp_writeData("xm1.struts", "1"); //项目状态--1:预售 0:预定
//		/* 项目二 */
//		p.jsp_writeData("xm2.30", "1");
//		p.jsp_writeData("xm2.50", "0");
//		p.jsp_writeData("xm2.80", "0");
//		p.jsp_writeData("xm2.100", "0");
//		p.jsp_writeData("xm2.type", "亚冠联赛 北京国安-城南一和,北京工人体育场,2010.03.31 19:30");	//
//		p.jsp_writeData("xm2.struts", "1"); //项目状态--1:预售 0:预定
//		
//		/* 联赛套票 */
//		p.jsp_writeData("xm3.A","1000");
//		p.jsp_writeData("xm3.B","1000");
//		p.jsp_writeData("xm3.C","1000");
//		p.jsp_writeData("xm3.D","1000");
//		p.jsp_writeData("xm3.Am","套餐A:北京国安队 对 天津泰达队|辽宁宏远队|长沙金德队");
//		p.jsp_writeData("xm3.Bm","套餐B:北京国安队 对 上海申花队|河南建业队|杭州绿城队");
//		p.jsp_writeData("xm3.Cm","套餐C:北京国安队 对 江苏舜天队|山东鲁能队|重庆办帆队");
//		p.jsp_writeData("xm3.Dm","套餐D:北京国安队 对 大连实德队|长春亚泰队|青岛中能队");
//		p.jsp_writeData("xm3.A1", "北京国安-天津泰达队,北京工人体育场,80,2010.10.17 19:30");
//		p.jsp_writeData("xm3.A2", "北京国安-辽宁宏远队,北京工人体育场,100,2010.07.24 19:30");
//		p.jsp_writeData("xm3.A3", "北京国安-长沙金德队,北京工人体育场,80,2010.05.16 19:30");
//		p.jsp_writeData("xm3.B1", "北京国安-上海申花队,北京工人体育场,100,2010.09.25 19:30");
//		p.jsp_writeData("xm3.B2", "北京国安-河南建业队,北京工人体育场,50,2010.08.22 19:30");
//		p.jsp_writeData("xm3.B3", "北京国安-杭州绿城队,北京工人体育场,100,2010.05.26 19:30");
//		p.jsp_writeData("xm3.C1", "北京国安-江苏舜天队,北京工人体育场,50,2010.11.06 19:30");
//		p.jsp_writeData("xm3.C2", "北京国安-山东鲁能队,北京工人体育场,80,2010.09.11 19:30");
//		p.jsp_writeData("xm3.C3", "北京国安-重庆办帆队,北京工人体育场,100,2010.07.14 19:30");
//		p.jsp_writeData("xm3.D1", "北京国安-大连实德队,北京工人体育场,80,2010.10.27 19:30");
//		p.jsp_writeData("xm3.D2", "北京国安-长春亚泰队,北京工人体育场,80,2010.08.14 19:30");
//		p.jsp_writeData("xm3.D3", "北京国安-青岛中能队,北京工人体育场,100,2010.05.03 19:30");
		
		System.out.println(p.jsp_getValue("xm1.30"));
	}
}
