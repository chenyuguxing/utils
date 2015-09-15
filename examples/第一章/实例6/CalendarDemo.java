package 第一章.实例6;
import java.util.*;
public class CalendarDemo {
	public static void main(String args[]) {
		Calendar calendar = Calendar.getInstance(); //创建一个日历对象。
		calendar.setTime(new Date()); //用当前时间初始化日历时间。
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
		System.out.println("现在的时间是：");
		System.out.println("" + year + "年" + month + "月" + date + "日 " + "星期" + day);
		System.out.println("" + hour + "时" + minute + "分" + second + "秒");
		calendar.set(1949, 9, 1); //将日历翻到1949年10月1日,注意9表示十月。
        // 返回当前时间，作为从开始时间的 UTC 毫秒值。
		long time1949 = calendar.getTimeInMillis();
		calendar.set(2004, 9, 1); //将日历翻到2004年10月1日。9表示十月。
        // 返回当前时间，作为从开始时间的 UTC 毫秒值。
		long time2004 = calendar.getTimeInMillis();
		long interdays = (time2004 - time1949) / (1000 * 60 * 60 * 24);
		System.out.println("2004年10月1日和1949年10月1日相隔" + interdays + "天");
	}
}
