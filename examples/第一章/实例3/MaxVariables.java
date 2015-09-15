package 第一章.实例3;
public class MaxVariables { //java中的一切都可以用类表示
    public static void main(String args[]) { //每个程序必须有main()函数

        // 整数类型
        byte largestByte = Byte.MAX_VALUE;// 定义一个byte类型的变量
        short largestShort = Short.MAX_VALUE;//定义一个short类型的变量
        int largestInteger = Integer.MAX_VALUE;//定义一个int类型的变量
        long largestLong = Long.MAX_VALUE;//定义一个long类型的变量

        // 实数类型
        float largestFloat = Float.MAX_VALUE;//定义一个float类型的变量
        double largestDouble = Double.MAX_VALUE;//定义一个double类型的变量

        // 其它基本类型
        char aChar = 'S';//定义一个字符
        boolean aBoolean = true;//定义一个布尔类型的变量

        // 在屏幕上显示对应类型的最大值
        System.out.println("最大的byte值是：" + largestByte);
        System.out.println("最大的short值是：" + largestShort);
        System.out.println("最大的integer值是：" + largestInteger);
        System.out.println("最大的long值是：" + largestLong);
        System.out.println("最大的float值是：" + largestFloat);
        System.out.println("最大的double值是：" + largestDouble);
        if (Character.isUpperCase(aChar)) {//判断字符是否大写
            System.out.println("字符" + aChar + "是大写的字符");
        } else {
            System.out.println("字符" + aChar + "是小写的字符");
        }
            System.out.println("布尔型变量的值是：" + aBoolean);
    }
}
