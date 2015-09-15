package 第一章.实例4;
class DayShower {//定义一个DayShower类
	//本程序的main()方法
	public static void main(String[] arguments) {//定义一个String对象的数组，用来存放参数
		//初始化变量
		int yearIn = 2004;//定义一个整数类型的变量，并且赋初值
		int monthIn = 2;//定义一个整数类型的变量，并且赋初值
		//检验命令行参数的数量
		if (arguments.length > 0)
			monthIn = Integer.parseInt(arguments[0]);
		if (arguments.length > 1)
			yearIn = Integer.parseInt(arguments[1]);
		//在屏幕上输出得到的结果
		System.out.println(yearIn + "年" + monthIn + "月有"
				+ countDays(monthIn, yearIn) + "天");
	}

	//countDays()方法，可以被DayShower调用
	static int countDays(int month, int year) {//用switch语句来计算某个月的天数
		int count = -1;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			count = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			count = 30;
			break;
		case 2:
			if (year % 4 == 0)
				count = 29;
			else
				count = 28;
			if ((year % 100 == 0) & (year % 400 != 0))
				count = 28;
		}
		return count;//返回count的值
	}
}
