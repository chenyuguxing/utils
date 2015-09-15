package 第一章.实例5;
//演示如何调用Java API完成数值运算
public class MathDemo {
	public static void main(String[] args) {
		// 演示求绝对值和求最大、最小值函数的用法
		System.out.println("abs(-5) = " + Math.abs(-5));
		System.out.println("max(2.72, 3.14) = " + Math.max(2.72, 3.14));
		System.out.println("min(256, 285) = " + Math.min(256, 285));
		// 演示四舍五入函数的用法
		System.out.println("round(3.8) = " + Math.round(3.8));
		System.out.println("round(-3.8) = " + Math.round(-3.8));
		// 演示求平方根和求幂函数的用法
		System.out.println("sqrt(2) = " + Math.sqrt(2));
		System.out.println("pow((1+2.25/100), 5) = " + Math.pow((1 + 2.25 / 100), 5));
		// 演示指数与对数函数的用法
		System.out.println("E = " + Math.E);
		System.out.println("exp(2) = " + Math.exp(2));
		System.out.println("log(2) = " + Math.log(2));
		// 演示天花板与地板函数的用法
		System.out.println("ceil(3.14) = " + (int) Math.ceil(3.14));
		System.out.println("floor(3.14) = " + (int) Math.floor(3.14));
		// 演示三角函数的用法
		System.out.println("Pi = " + Math.PI);
		System.out.println("sin(Pi / 2) = " + Math.sin(Math.PI / 2));
		System.out.println("cos(0) = " + Math.cos(0));
	}
}
