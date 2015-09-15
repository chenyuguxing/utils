package 第一章.实例8;
//演示字符串对象的用法
public class StringDemo {
	public static void main(String[] args) {
		// 访问字符串长度
		String myName = "G. Leeman";
		int length = "Best Wishes!".length();
		System.out.println("长度1：" + myName.length());    // 输出9
		System.out.println("长度2：" + length);    // 输出3而不是6
		// 比较字符串大小
		String name1 = "programming in java";
		String name2 = "Programming in Java";
		System.out.println("比较1：" + name1.equals(name2));    // 输出false
		System.out.println("比较2：" + name1.equalsIgnoreCase(name2));    // 输出true
		System.out.println("比较3：" + name2.compareTo("Program"));    // 输出正数
		// 访问字符串中的字符
		System.out.println("字符1：" + name1.charAt(4));    // 输出r而不是g
		System.out.println("字符2：" + name1.indexOf('a'));    // 输出5
		System.out.println("字符3：" + name2.lastIndexOf('a'));    // 输出18
		// 访问字符串中的子串
		String subname = "in";
		System.out.println("子串1：" + name1.substring(3, 10));    // 输出grammin
		System.out.println("子串2：" + "abc".concat("123"));    // 输出abc123
		System.out.println("子串3：" + name2.startsWith("Pro"));    // 输出true
		System.out.println("子串4：" + name2.endsWith("in Java"));    // 输出true
		System.out.println("子串5：" + name1.indexOf(subname));    // 输出8
		System.out.println("子串6：" + name1.lastIndexOf(subname));    // 输出12
		// 字符串的其他操作
		System.out.println("小写：" + name2.toLowerCase());    // 输出programming in java
		System.out.println("大写：" + name2.toUpperCase());    // 输出PROGRAMMING IN JAVA
		System.out.println("替换："+name1.replace('a','A'));  // 输出progrAmming in jAvA
	}
}
