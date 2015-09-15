package 第一章.实例23;
import java.util.*;

public class PropertiesDemo{
	public PropertiesDemo(){
	}
	
	public static void main(String[] args){
		PropertiesDemo propertiesDemo = new PropertiesDemo();
		Properties p = new Properties();
		// 设置属性
		p.setProperty("C", "China");
		p.setProperty("A", "America");
		p.setProperty("J", "Japan");
		p.setProperty("K", "Korea");
		p.setProperty("S", "Spain");
		// 枚举属性
		Enumeration e = p.propertyNames();
		System.out.print("The all keys in p:");
		while(e.hasMoreElements())
			System.out.println(e.nextElement().toString()+"\t");
		System.out.println();
		System.out.println("The property of key 'K' is " + p.getProperty("K"));
		System.out.println("The property of key 'J' is " + p.getProperty("J"));
		System.out.println("The property of key 'Q' is " + p.getProperty("Q"));
	}
}
