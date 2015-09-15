/**
 *
 * Copyright (c) 2013, YongLe. All rights reserved.
 * Created on Jan 10, 2013 3:49:14 PM
 * 
 *
 *
 * @author	"JakeLiu"
 * @version	V1.0.0
 *
 *
 *
 * Modification History:
 * Date				Author			Version		Description
 * ---------------------------------------------------------------------
 * Jan 10, 2013		"JakeLiu"		1.0.0		
 */
package com.ylpw.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * 积分计算工具类
 *
 * @author "JakeLiu"
 * @version Jan 10, 2013 3:49:14 PM
 * @see BonusUtils
 * @since 1.0.0
 */
public class BonusUtils {
	
	/**
	 * 积分默认倍数
	 */
	private static final double DEFAULT_BONUS_SCALE = 1d;
	
	/**
	 * 积分配置是否可用
	 * @author JakeLiu
	 * @date Mar 12, 2013 2:01:10 PM
	 * @return
	 */
	public static boolean isEnableConfigBonus() {
		String bonusScaleValue = Constants.propertiesUtilsCore.jsp_getValue("bonusScale");
		String bonusBeginDate = Constants.propertiesUtilsCore.jsp_getValue("bonusBeginDate");
		String bonusFinishDate = Constants.propertiesUtilsCore.jsp_getValue("bonusFinishDate");
		
		
		if (StringUtils.isBlank(bonusScaleValue) || !NumberUtils.isNumber(bonusScaleValue.trim())) {
			return false;
		}
		
		Date rightNow = new Date();
		Date beginDate = null;
		Date finishDate = null;
		
		if (StringUtils.isNotBlank(bonusBeginDate)) {
			beginDate = DateUtils.parseDate(bonusBeginDate, DateUtils.YEAR_MONTH_DATE_HOUR_MINUTE);
		}
		
		if (StringUtils.isNotBlank(bonusFinishDate)) {
			finishDate = DateUtils.parseDate(bonusFinishDate, DateUtils.YEAR_MONTH_DATE_HOUR_MINUTE);
		}
		
		if (beginDate != null && beginDate.after(rightNow)) {
			return false;
		} else if (finishDate != null && finishDate.before(rightNow)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 取积分倍数，使用此方法前先调用isEnableConfigBonus进行较验
	 * @author JakeLiu
	 * @date Mar 12, 2013 3:13:30 PM
	 * @param isChecked
	 * @return
	 */
	public static Double findScale() {
		return Double.parseDouble(Constants.propertiesUtilsCore.jsp_getValue("bonusScale"));
	}
	
	/**
	 * 根据金额计算积分
	 * @author	"JakeLiu"
	 * Jan 10, 2013 5:34:25 PM
	 * @param total
	 * @return
	 */
	public static Bonus calculateBonusByOrderTotal(Double total) {
		
		double bonusScale = DEFAULT_BONUS_SCALE;
		
		if (isEnableConfigBonus()) {
			bonusScale = Double.parseDouble(Constants.propertiesUtilsCore.jsp_getValue("bonusScale"));
		}
		
		// 构建积分详情
		Bonus bonus = new Bonus();
		bonus.setScale(bonusScale);
		bonus.setValue(ArithUtils.mul(total, bonusScale));
		return bonus;
	}
	
	/**
	 *
	 * 积分明细、可扩展如积分类型、积分名称等
	 *
	 * @author "JakeLiu"
	 * @version Jan 10, 2013 5:33:12 PM
	 * @see Bonus
	 * @since 1.0.0
	 */
	static class Bonus {
		private Double scale;	// 倍数
		private Double value;	// 积分值
		
		public Double getScale() {
			return scale;
		}
		public void setScale(Double scale) {
			this.scale = scale;
		}
		public Double getValue() {
			return value;
		}
		public void setValue(Double value) {
			this.value = value;
		}
	}
}
