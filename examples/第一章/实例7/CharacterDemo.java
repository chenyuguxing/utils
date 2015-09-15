package 第一章.实例7;
public class CharacterDemo{
	public CharacterDemo(){
	}
	
	public static void main(String[] args){
		Character c1 = new Character('A');
		Character c2 = new Character('b');
		// 输出字符的ASCII码值
		System.out.println(c1.charValue() + 0);
		System.out.println(c2.charValue() + 0);
		// 比较两个字符ASSII码值大小
		int i = c1.compareTo(c2);
		System.out.println(i);
		if(i==0){
			System.out.println("c1 is equal c2!");
		}
		else if(i<0){
			System.out.println("c1 is smaller than c2!");
		}
		// 大小写转换
		System.out.println(Character.toLowerCase('A')+" "
				+ Character.toUpperCase('a'));
		// 判断是否为数字
		System.out.println(Character.isDigit('9'));
	}
}
