package com.ylpw.utils;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

public class MD5 {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MD5.class);

	public final static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	private static char hexs[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
		'b', 'c', 'd', 'e', 'f' };
	
	public static String encode(String source) {
		try {
			MessageDigest digester = MessageDigest.getInstance("MD5");

			byte[] sbs = source.getBytes("UTF8");
			digester.update(sbs);
			byte[] rbs = digester.digest();
			int j = rbs.length;
			char result[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = rbs[i];
				result[k++] = hexs[b >>> 4 & 0xf];
				result[k++] = hexs[b & 0xf];
			}
			return new String(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * UTN加密串
	 * @param uname
	 * @param password
	 * @return
	 */
	public static String getUTNMd5(String uname,String password){
		return getMD5("NODECODE=21; USERCODE="+uname+"; PASSWORD="+password+";").toUpperCase();
	}
	
	
	public static void main(String args[]){
		System.out.println(getMD5("NODECODE=21; USERCODE=JIAOKE; PASSWORD=332336;").toUpperCase());
//		System.out.println(getMD5("228228"));
		
	}
}