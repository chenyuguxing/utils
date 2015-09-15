/**
 *
 * Copyright (c) 2012, YongLe. All rights reserved.
 * Created on Jul 11, 2012 3:10:59 PM
 * 
 *
 * 正则表达式工具
 *
 * @author	"JakeLiu"
 * @version	V1.0.0
 *
 *
 *
 * Modification History:
 * Date				Author			Version		Description
 * ---------------------------------------------------------------------
 * Jul 11, 2012		"JakeLiu"		1.0.0		正则表达式工具
 */
package com.ylpw.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 正则表达式工具
 *
 * @author "JakeLiu"
 * @version Jul 11, 2012 3:10:59 PM
 * @see RegexUtils
 * @since 1.0.0
 */
public class RegexUtils {
	
	/**
	 * 用户名
	 */
	public static final String USER_NAME = "^[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9_\\u4E00-\\u9FA5]{1,11}$";
	/**
	 * 密码
	 */
	public static final String USER_PASSWORD = "^.{6,36}$";
	/**
	 * 邮箱
	 */
	public static final String EMAIL = "^[a-z\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";
	/**
	 * 手机号
	 */
	public static final String PHONE = "^1[3458]\\d{9}$";
	/**
	 * URL路径
	 */
	public static final String URL = "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/";

	

	/**
	 *
	 * 编译传入正则表达式和字符串去匹配
	 * @param regex
	 * @param beTestString
	 * @return
	 */
	public static boolean match(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.matches();
	}
	
	
	/**
	 *
	 * 编译传入正则表达式在字符串中寻找，如果匹配到则为true
	 * @param regex
	 * @param beTestString
	 * @return
	 */
	public static boolean find(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.find();
	}
	
	
	/**
	 * 编译传入正则表达式在字符串中寻找，如果找到返回第一个结果<br/>
	 * 找不到返回null
	 * @author	"JakeLiu"
	 * Feb 22, 2013 5:23:40 PM
	 * @param regex
	 * @param beFoundString
	 * @return
	 */
	public static String findResult(String regex, String beFoundString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beFoundString);
		if (matcher.find()) {
			return matcher.group();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(findResult("(?<=-)\\d+(?=\\.html)", "http://www.228.com.cn/ticket-11715061.html"));
	}
}
