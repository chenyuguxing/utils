package com.ylpw.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * User:T.L
 * Date:Jul 19, 2010
 * Time:7:46:14 PM
 * Class Description:
 */
public class DateUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DateUtils.class);
	
	public static String DAY_FROMAT = "yyyy-MM-dd";
	public static String YEAR_MONTH_DATE_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
	public static String CURRENTTIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String TIMESTAMP_FORMAT = "yyyy-MM-dd kk:mm:ss.SSS";
	public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(CURRENTTIME_FORMAT,java.util.Locale.CHINA);
	public static String YEAR_FROMAT = "yyyy";
	public static final String YEAR_MONTH_DATE_HOUR_MINUTE_CN = "yyyy年MM月dd日 HH:mm";
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	
	/** 获得当前豪秒值 */
	public static long millisTime(){
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取当前时间
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();
		return dtNow;
	}
	
	/**
	 * 获得当前日期时间字符
	 * @return 返回当前时间字符
	 */
	public static String getCurrentDateTimeStr(){
		return DATE_TIME_FORMAT.format(getCurrentDateTime());
	}
	
	/**
	 * util Date 转换成 sql Date
	 * @author	"JakeLiu"
	 * Aug 22, 2012 11:36:42 AM
	 * @param date
	 * @return
	 */
	public static java.sql.Date transferDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * sql Date 转换成 util Date
	 * @author	"JakeLiu"
	 * Aug 22, 2012 11:36:33 AM
	 * @param date
	 * @return
	 */
	public static Date transferDate(java.sql.Date date) {
		return new Date(date.getTime());
	}
	
	
	/**
	 *
	 * 取得给定日期加上一定分钟后的日期对象.
	 * @Author:T.L
	 * @Date:2012-8-16 下午8:19:10
	 * @param date 当前日期
	 * @param minutesCount 分钟
	 * @return
	 *
	 */
	public static final Date getDateAddMinutes(Date date, int minutesCount) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutesCount);
		return calendar.getTime();
	}
	/**
	 * 
	 * User:T.L
	 * Description:字符串格式化为日期
	 * @param dateStr 日期字符串
	 * @param formatStr 需要的格式
	 * @return
	 *
	 */
	public static Date parseDate(String dateStr,String formatStr){
		simpleDateFormat.applyPattern(formatStr);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 返回 yyyy-MM-dd HH:mm:ss 格式日期
	 * User:T.L
	 * Description:
	 * @param dateStr
	 * @return
	 *
	 */
	public static Date parseDateLong(String dateStr){
		return parseDate(dateStr, CURRENTTIME_FORMAT);
	}
	
	
	/**
	 * 
	 * User:T.L
	 * Description: 日期格式化为字符串
	 * @param date 日期
	 * @param formatStr 需要的格式
	 * @return
	 *
	 */
	public static String formatDate(Date date,String formatStr){
		simpleDateFormat.applyPattern(formatStr);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 返回 yyyy-MM-dd HH:mm:ss 字符串格式日期
	 * User:T.L
	 * Description:
	 * @param date
	 * @return
	 *
	 */
	public static String formatDateLong(Date date){
		return formatDate(date, CURRENTTIME_FORMAT);
	}
	
	/**
	 * 获取当前日期年份
	 * User:T.L
	 * Description:
	 * @param date 当前日期
	 * @return
	 *
	 */
	public static String getYear(Date date){
		simpleDateFormat.applyPattern(YEAR_FROMAT);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取当前日期年份
	 * User:T.L
	 * Description:
	 * @param date 当前日期
	 * @return
	 *
	 */
	public static String getMonth(Date date){
		simpleDateFormat.applyPattern("MM");
		return simpleDateFormat.format(date);
	}
	
	
	 /**
	 * 
	 * User:T.L
	 * Description: 得到当前日期加指定日期的 yyyy-MM-dd
	 * @param date 当前日期
	 * @param day 指定当前日期往后加几天
	 * @return 返回字符串格式日期 yyyy-MM-dd
	 *
	 */
	public static String getAfterDay(Date date, int day) {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例

		c.setTime(date);
		c.add(Calendar.DATE, day);
		simpleDateFormat.applyPattern(DAY_FROMAT);
		String strDate = simpleDateFormat.format(c.getTime());// 得到你想要得指定当前日期的后几天
		return strDate;
	}
	
	/**
	 * 
	 * User:T.L
	 * Description:当前日期是否在指定日期之后
	 * @param specifiedTimeStr 指定的时间 格式为:HH:mm:ss
	 * @return true:当前日期在后  false:当前日期比指定日期靠前
	 *
	 */
	public static boolean getAfterHour(String specifiedTimeStr) {
		boolean t = true;
		
		Date date = new Date();
		//指定的时间
		String specifiedTime = null;
		simpleDateFormat.applyPattern(DAY_FROMAT);
		specifiedTime = simpleDateFormat.format(date)+specifiedTimeStr;
			try {
				simpleDateFormat.applyPattern(CURRENTTIME_FORMAT);
				t = date.after(simpleDateFormat.parse(specifiedTime));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
				t=true;
			}
		return t;
	}
	
	/**
	 * 返回要求的提前天数集合.
	 * User:T.L
	 * Description:
	 * @param day 指定天数
	 * @param specifiedTimeStr 指定的时间 格式为:HH:mm:ss
	 * @param addAfterDay 将当前日期向后加几天
	 * @return
	 *
	 */
	public static List<String> getAfterDayList(int day,String specifiedTimeStr,int addAfterDay){
		List<String> list = new ArrayList<String>();
		Date date = new Date();
		
		if(getAfterHour(specifiedTimeStr)){
			try {
				simpleDateFormat.applyPattern(DAY_FROMAT);
				//如果当前日期在指定时间之后,刚不能买明天的票.按规定只能买后天的票,所以开始日期从明天起算
				date = simpleDateFormat.parse(getAfterDay(date, addAfterDay));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}

		//开始天数
		int start = 1;
		
		for(int i=start; i<=day; i++){
			list.add(getAfterDay(date,i));
		}
		
		return list;
	}
	
	
	//-------------------------------------------
	
	/**
	 * User:T.L
	 * Description:获得指定格式的时间一周时间,按顺序从周一到周日
	 * @param dateFormatStr 需要的格式
	 * @return
	 *
	 */
	public static String[] getWeekOfDayChina(String dateFormatStr) {
		String[] arr = new String[7];
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FROMAT,Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern(dateFormatStr);
		for (int i = 1, k = 0; i < 8; i++, k++) {
			calendar.set(Calendar.DAY_OF_WEEK, i);
			if (k > 0) {
				date = calendar.getTime();
				String timeStr = sdf.format(date);
				arr[i - 2] = timeStr;
				calendar.setTime(date);
			}
		}
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		String timeStr = sdf.format(date);
		arr[6] = timeStr;
		return arr;
	}

	/**
	 * User:T.L
	 * Description:返回指定格式的第n天后的时间集合
	 * @param date 指定的日期
	 * @param n 需要的天数
	 * @param dateFormatStr 需要的格式
	 * @return
	 *
	 */
	public static String[] getAfterNDay(Date date, int n, String dateFormatStr) {
		Calendar c = Calendar.getInstance();
		String[] st = new String[n];
		DateFormat df = new SimpleDateFormat(dateFormatStr);
		for (int i = 0; i < n; i++) {
			c.setTime(date);
			c.add(Calendar.DATE, i);
			Date d = c.getTime();
			String s = df.format(d);
			st[i] = s;
		}
		return st;
	}

	/**
	 * User:T.L
	 * Description:返回两个日期之间的详细日期数组(包括开始日期和结束日期)。 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 *
	 */
	public static String[] getArrayDiffDays(String startDate, String endDate) {
		int LEN = 0; // 用来计算两天之间总共有多少天
		// 如果结束日期和开始日期相同
		if (startDate.equals(endDate)) {
			return new String[] { startDate };
		}
		Date sdate = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdate = getDateObj(startDate, "/"); // 开始日期
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdate = getDateObj(startDate, "-"); // 开始日期
		}
		LEN = getDiffDays(startDate, endDate);
		String[] dateResult = new String[LEN + 1];
		dateResult[0] = startDate;
		for (int i = 1; i < LEN + 1; i++) {
			if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
				dateResult[i] = formatDate(getDateAdd(sdate, i),"yyyy/MM/dd");
			}
			if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
				dateResult[i] = formatDate(getDateAdd(sdate, i),"yyyy-MM-dd");
			}
		}
		return dateResult;
	}

	/**
	 * 返回当前日期是星期几。
	 */
	public static String getChinaDayOfWeek(String date) {
		String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四","星期五", "星期六" };
		int week = getDayOfWeek(date);
		return weeks[week - 1];
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。
	 */
	public static int getDayOfWeek(String date) {
		String[] temp = null;
		if (date.indexOf("/") > 0) {
			temp = date.split("/");
		}
		if (date.indexOf("-") > 0) {
			temp = date.split("-");
		}
		return getDayOfWeek(temp[0], temp[1], temp[2]);
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 */

	public static int getDayOfWeek(String year, String month, String day) {
		Calendar cal = new GregorianCalendar(new Integer(year).intValue(),new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}


	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 */
	public static Date getDateAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return cal.getTime();
	}
	
	/**
	 * 取得给定日期加上指定月份
	 */
	public static Date getMonthAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 返回当月有多少天
	 */
	public static int getMonthLastDay(int year, int month){
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 返回两个日期之间相差多少天。 
	 */
	public static int getDiffDays(String startDate, String endDate) {
		long diff = 0;
		SimpleDateFormat ft = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			Date sDate = ft.parse(startDate + " 00:00:00");
			Date eDate = ft.parse(endDate + " 00:00:00");
			diff = eDate.getTime() - sDate.getTime();
			diff = diff / 86400000;// 1000*60*60*24;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return (int) diff;
	}
	
	/**
	 * 返回两个日期之间相差多少小时。 
	 */
	public static int getDiffHours(String startDate, String endDate) {	
		long diff = 0;
		SimpleDateFormat sdf = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			Date start = sdf.parse(startDate);		
			Date end = sdf.parse(endDate);		
			diff = end.getTime() - start.getTime();		
			diff = (long) (diff * 1.0 / (1000 * 60 * 60));	
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return (int) diff;	
	}
	
	/**
	 * 返回两个日期之间相差多少分钟时。 
	 */
	public static int getDiffMins(String startDate, String endDate) {	
		long diff = 0;
		SimpleDateFormat sdf = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			Date start = sdf.parse(startDate);		
			Date end = sdf.parse(endDate);		
			diff = end.getTime() - start.getTime();		
			long day = diff / (24 * 60 * 60 * 1000);  
	        long hour = (diff / (60 * 60 * 1000) - day * 24);  
	        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
			diff = (long) min;	
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return (int) diff;	
	}

	/**
	 * 取得指定分隔符分割的年月日的日期对象.
	 */
	public static Date getDateObj(String argsDate, String split) {
		String[] temp = argsDate.split(split);
		int year = new Integer(temp[0]).intValue();
		int month = new Integer(temp[1]).intValue();
		int day = new Integer(temp[2]).intValue();
		return getDateObj(year, month, day);
	}

	/**
	 * 取得指定年月日的日期对象.
	 */
	public static Date getDateObj(int year, int month, int day) {
		Calendar c = new GregorianCalendar();
		c.set(year, month - 1, day);
		return c.getTime();
	}

	/**
	 * 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
	 */
	public final static java.sql.Timestamp string2Time(String dateString, String... format) {
		DateFormat dateFormat;
		String format_ = format.length > 0 ? format[0] : TIMESTAMP_FORMAT;
		dateFormat = new SimpleDateFormat(format_, Locale.ENGLISH);// 设定格式
		// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss",
		// Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.util.Date timeDate;
		try {
			timeDate = dateFormat.parse(dateString);
			java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
			return dateTime;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	
	
	/**
	 * 判断日期格式
	 * @throws ParseException
	 */
	public static synchronized boolean getStrDateFormat(String str){
		String eL= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";   
        Pattern p = Pattern.compile(eL);    
        Matcher m = p.matcher(str);    
        boolean b = m.matches();   
        if(b){     
            return true;
        } else {   
            return false;
        } 
	}
	
	/** 取得某日期时间的特定表示格式的字符串
	 * @param format 时间格式
	 * @param date 某日期（Date）
	 * @return 某日期的字符串
	 */
	public static synchronized String getDate2Str(String format, Date date) {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 
	 * @author D.J
	 * @date 2012-7-29 下午3:14:22
	 * @param ir 订单产生的基本积分数
	 * @param orderDate 订单下单日期
	 * @param ratedDate 配置文件额定日期
	 * @param multiples 指定积分倍数
	 * @return
	 */
	public static Integer addMultiplesJF(Integer ir,Date orderDate,String ratedDate,Double multiples) {
		//积分乘以指定倍数
		if(DateUtils.getDiffDays(DateUtils.getDate2Str(DateUtils.CURRENTTIME_FORMAT, orderDate), ratedDate)>=0){
			if(null!=multiples && multiples>0){
				String strValue=String.valueOf(ir*multiples);
				ir = Integer.parseInt(strValue.substring(0,strValue.indexOf(".")));
			}
		}
		return ir;
	}
	
	
	/**
	 * 根据Calendar.fields进行清理<br/>
	 * @author	"JakeLiu"
	 * Aug 16, 2012 1:55:36 PM
	 * @param date
	 * @param calendarFields
	 * @return
	 */
	public static final Date clearByFields(Date date, int... calendarFields) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		for (int i : calendarFields) {
			if (i == Calendar.HOUR) {
				cal.set(Calendar.HOUR, 0);
			} else if (i == Calendar.HOUR_OF_DAY) {
				cal.set(Calendar.HOUR_OF_DAY, 0);
			} else {
				cal.clear(i);
			}
		}
		return cal.getTime();
	}
	
	
	private static Calendar setTime(final Calendar calendat, final String[] test) {
        if (test.length == 3) {
            calendat.set(Calendar.HOUR_OF_DAY, Integer.parseInt(test[0].trim()));
            calendat.set(Calendar.MINUTE, Integer.parseInt(test[1].trim()));
            calendat.set(Calendar.SECOND, Integer.parseInt(test[2].trim()));
        }
        if (test.length == 2) {
            calendat.set(Calendar.HOUR_OF_DAY, Integer.parseInt(test[0].trim()));
            calendat.set(Calendar.MINUTE, Integer.parseInt(test[1].trim()));
            calendat.set(Calendar.SECOND, 0);
        }
        return calendat;
    }
	
	
	/**
	 * 任何日期字符串转化为date类型
	 * @param value yyyy-MM-dd [hh:mm:ss.SSS][AM|PM]
	 * @return
	 */
	public static Date dateOfAny(final String value) {
		if(value==null || value.trim().length()==0)
			return null;
        Calendar calendat = Calendar.getInstance();
        final String datePattern = "[0-9]{4}(-|.|/)([0-1]?[0-9])(-|.|/)(([0-3]?[0-9])|([3][0-1]))";
        final String timePattern = "(([0-1]?[0-9])|([2][0-3]))((:([0-5]){0,1}([0-9]){1}){1,2})";
        final String timesPattern = " ([0-1]?[0-9]){1}(((:([0-5]){0,1}([0-9]){1}){1,2}) (([AP]M)|([ap]m)))?";
        Pattern p = Pattern.compile(datePattern);
        Matcher m = p.matcher(value);
        m.find();
        final String date_String = m.group();
        if (date_String != null) {
            final String[] test = date_String.split("-");
            if (test.length == 3) {
                calendat.set(Calendar.YEAR, Integer.parseInt(test[0].trim()));
                calendat.set(Calendar.MONTH,Integer.parseInt(test[1].trim()) - 1);
                calendat.set(Calendar.DAY_OF_MONTH, Integer.parseInt(test[2].trim()));
            }
        }
        if ((value.indexOf("pm") != -1) || (value.indexOf("PM") != -1) || (value.indexOf("am") != -1) || (value.indexOf("AM") != -1)) {
            p = Pattern.compile(timesPattern);
            m = p.matcher(value);
            m.find();
            String time_String = m.group();
            if (time_String != null) {
                final String apm = time_String.substring(time_String.trim()
                        .indexOf(" ") + 1);
                if (apm.trim().equalsIgnoreCase("pm")) {
                    time_String = time_String.trim().substring(0,
                            time_String.trim().indexOf(" "));
                    final String[] test = time_String.split(":");
                    if (test.length == 3) {
                        calendat.set(Calendar.HOUR_OF_DAY, Integer.parseInt(test[0].trim()) + 12);
                        calendat.set(Calendar.MINUTE, Integer.parseInt(test[1].trim()));
                        calendat.set(Calendar.SECOND, Integer.parseInt(test[2].trim()));
                    }
                    if (test.length == 2) {
                        calendat.set(Calendar.HOUR_OF_DAY, Integer.parseInt(test[0].trim()) + 12);
                        calendat.set(Calendar.MINUTE, Integer.parseInt(test[1].trim()));
                        calendat.set(Calendar.SECOND, 0);
                    }
                } else {
                    final String[] test = time_String.split(":");
                    calendat = setTime(calendat, test);
                }
            }
        } else {
            p = Pattern.compile(timePattern);
            m = p.matcher(value);
            final Boolean isFind = m.find();
            if (isFind == true) {
                final String time_String = m.group();
                if (time_String != null) {
                    final String[] test = time_String.split(":");
                    calendat = setTime(calendat, test);
                }
            } else {
                calendat.set(Calendar.HOUR_OF_DAY, 0);
                calendat.set(Calendar.MINUTE, 0);
                calendat.set(Calendar.SECOND, 0);
            }

        }
        return calendat.getTime();
	}
	
	/**
	 * @description 
	 * 功能描述: 获取本周日期
	 * @author 		  作         者: 张嘉杰
	 * @param	             参         数: 
	 * @return       返回类型: 
	 * @createdate   建立日期：Aug 5, 201310:59:04 AM
	 */
	public static String[] getThisWeek(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",java.util.Locale.CHINA);
		String[] thisweek = new String[2];
	 	Calendar c = Calendar.getInstance();
	  	int weekday = c.get(7)-1;
	  	c.add(5,-weekday);
	  	thisweek[0] = sdf.format(c.getTime());
	  	c.add(5,6);
	  	thisweek[1] = sdf.format(c.getTime());
	  	return thisweek;
	}
	
	
	  // 获得当前月--开始日期
    public static String[] getThisMonth(String date) {   
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",java.util.Locale.CHINA);
    	String[] thisweek = new String[2];
    	
    	Calendar calendarMin = Calendar.getInstance();   
    	calendarMin.setTime(DateUtils.parseDate(date, DateUtils.DAY_FROMAT));
    	calendarMin.set(Calendar.DAY_OF_MONTH, calendarMin.getActualMinimum(Calendar.DAY_OF_MONTH)); 
        thisweek[0] =  sdf.format(calendarMin.getTime());
        
        Calendar calendarMax = Calendar.getInstance();   
        calendarMax.setTime(DateUtils.parseDate(date, DateUtils.DAY_FROMAT));
        calendarMax.set(Calendar.DAY_OF_MONTH, calendarMax.getActualMaximum(Calendar.DAY_OF_MONTH));
        thisweek[1] =  sdf.format(calendarMax.getTime());
	    return thisweek;
    }


	
	
	/** 获得本周六的日期 */
    public static String getSaturday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * 0 + 5);
        Date monday = currentDate.getTime();
        DateFormat df = new SimpleDateFormat("yyyyMMdd",java.util.Locale.CHINA);
        String preMonday = df.format(monday);
        return preMonday;
    }
	
    /** 获得下周星期一的日期 */
    public static String getNextMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = new SimpleDateFormat("yyyyMMdd",java.util.Locale.CHINA);
        String preMonday = df.format(monday);
        return preMonday;
    }
    
    /** 获得当前日期与本周日相差的天数 */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1; //因为礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }
    
	/** 两个时间之间的天数 */
	public static int getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
			logger.error("格式化日期异常...",e);
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return Long.valueOf(day).intValue();
	}
	
    /** 
     * 获取前/后半年的开始时间 
     * @return 
     */ 
    public static String getHalfYearStartTime(){
    	DateFormat df = new SimpleDateFormat("yyyyMMdd",java.util.Locale.CHINA);
        Calendar c = Calendar.getInstance(); 
        int currentMonth = c.get(Calendar.MONTH) + 1; 
        String halfStartTime = "";
        try { 
            if (currentMonth >= 1 && currentMonth <= 6){ 
                c.set(Calendar.MONTH, 0); 
            }else if (currentMonth >= 7 && currentMonth <= 12){ 
                c.set(Calendar.MONTH, 6); 
            } 
            c.set(Calendar.DATE, 1); 
            halfStartTime = df.format(c.getTime()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return halfStartTime; 
    } 
    
    /** 
     * 获取前/后半年的结束时间 
     * @return 
     */ 
    public static String getHalfYearEndTime(){ 
    	DateFormat df = new SimpleDateFormat("yyyyMMdd",java.util.Locale.CHINA);
        Calendar c = Calendar.getInstance(); 
        int currentMonth = c.get(Calendar.MONTH) + 1; 
        String halfYearEndTime = "";
        try { 
            if (currentMonth >= 1 && currentMonth <= 6){ 
                c.set(Calendar.MONTH, 5); 
                c.set(Calendar.DATE, 30); 
            }else if (currentMonth >= 7 && currentMonth <= 12){ 
                c.set(Calendar.MONTH, 11); 
                c.set(Calendar.DATE, 31); 
            } 
            halfYearEndTime = df.format(c.getTime()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return halfYearEndTime; 
    } 
    
	
	
	public static void main(String[] args) {
		
		Date date = new Date();
//		System.out.println(parseDate("2011-10-25 10:25:30", CURRENTTIME_FORMAT));
//		
//		System.out.println(parseDateLong("2011-10-25 10:25:30"));
//		
//		System.out.println(formatDate(date, DAY_FROMAT));
//		
//		System.out.println(formatDateLong(date));
//		
//		System.out.println(getYear(date));
//		
//		System.out.println(getAfterDay(date, 2));
//		
//		System.out.println(getAfterHour(" 12:10:20"));
//		
//		System.out.println(getAfterDayList(5, " 00:00:00", 1));
//		
//		System.out.println("------------------------------------");
//		
//		System.out.println(Arrays.toString(getWeekOfDayChina("yyyy-MM-dd")));
//		
//		System.out.println(Arrays.toString(getAfterNDay(new Date(), 10,"yyyy-MM-dd")));
//		
//		System.out.println(Arrays.toString(getArrayDiffDays("2010-10-01","2010-10-10")));
//		
//		System.out.println(getChinaDayOfWeek("2011-10-25"));
//		
//		System.out.println(getDateAdd(date,1));
//		
//		System.out.println(getDiffDays("2011-10-25","2011-10-30"));
//		
////		System.out.println(getDiffHours("2013-08-23 10:20:00", "2013-08-23 12:20:00"));
//		
////		System.out.println("Timestamp: " + string2Time("2005-8-18 14:21:12.123"));
////		System.out.println("Timestamp: " + string2Time("2005-8-18 14:21:12", "yyyy-MM-dd kk:mm:ss"));
////		
////		System.out.println(Integer.parseInt("690"));
//		
//		for(String s:getArrayDiffDays("2013-09-01", "2013-09-07")){
//			System.out.println(s);
//		}
//		Calendar calendar_Now = Calendar.getInstance();
//		date=DateUtils.parseDate("2013-11-04", DateUtils.DAY_FROMAT);
//		calendar_Now.setTime(date);
//		System.out.println(getYear(date));
//		System.out.println(getMonth(date));
//		
//		
//		System.out.println(DateUtils.formatDate(getMonthAdd(new Date(),5), CURRENTTIME_FORMAT));
		System.out.println(getDiffMins("2014-05-05 16:49:00", "2014-05-05 16:52:00"));
		//System.out.println(getHalfYearEndTime());
		
	}

}
