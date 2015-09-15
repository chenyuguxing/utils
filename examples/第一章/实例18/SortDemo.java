package 第一章.实例18;
public class SortDemo {
	public static void main(String[] args) {
		//初始化变量
		int[] arrayOfInts = { 12, 8, 3, 59, 132, 1236, 3400, 8, 352, 37 };
		//循环整个数组
		for (int i = arrayOfInts.length; --i >= 0;) {
			//循环每个数字
			for (int j = 0; j < i; j++) {
				if (arrayOfInts[j] > arrayOfInts[j + 1]) {
					//将两个数字的位置进行对调
					int temp = arrayOfInts[j];
					arrayOfInts[j] = arrayOfInts[j + 1];
					arrayOfInts[j + 1] = temp;
				}
			}
		}
		//循环整个数组
		for (int i = 0; i < arrayOfInts.length; i++) {
			System.out.print(arrayOfInts[i] + " ");//每个数字后加一个空格
		}
		System.out.println();//一行结束
	}
}
