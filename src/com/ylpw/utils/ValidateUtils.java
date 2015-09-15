/**
 *
 * Copyright (c) 2012, YongLe. All rights reserved.
 * Created on Jul 11, 2012 3:16:03 PM
 * 
 *
 * 较验工具
 *
 * @author	"JakeLiu"
 * @version	V1.0.0
 *
 *
 *
 * Modification History:
 * Date				Author			Version		Description
 * ---------------------------------------------------------------------
 * Jul 11, 2012		"JakeLiu"		1.0.0		较验工具
 */
package com.ylpw.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;


/**
 *
 * 较验工具
 *
 * @author "JakeLiu"
 * @version Jul 11, 2012 3:16:03 PM
 * @see ValidateUtils
 * @since 1.0.0
 */
public class ValidateUtils {
	
	/**
	 *
	 * os是否包含o
	 * 
	 * @param o
	 * @param os
	 * @return
	 */
	public static boolean in(Integer o, int... os) {
		if (o == null || os == null) {
			return false;
		}
		
		for (int i : os) {
			if (o == i) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 *
	 * os是否不包含o
	 * @param o
	 * @param os
	 * @return
	 */
	public static boolean notIn(Integer o, int... os) {
		return !in(o, os);
	}
	
	
	/**
	 * 
	 * 检查表单必填字段<br/>
	 * 如果有空对象、空字符串("")、则返true
	 * 
	 * @author	"JakeLiu"
	 * Jan 31, 2013 6:00:18 PM
	 * @param strs
	 * @return
	 */
	public static boolean hasBlank(Object... objs) {
		for (Object obj : objs) {
			
			if (obj == null) {
				return true;
			}
			
			if (obj instanceof String && StringUtils.isBlank(obj.toString())) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 字符串安全清理
	 * @author	"JakeLiu"
	 * Jan 31, 2013 6:57:53 PM
	 * @param text
	 * @return
	 */
	public static String cleanse(String text) {
		return HtmlUtils.htmlEscape(text.trim());
	}
}
