package 第一章.实例24;
import java.util.*;

public class ArrayListDemo{
	public ArrayListDemo(){
	}
	
	public static void main(String[] args){
		ArrayList a = new ArrayList();
		// 往ArrayList中添加元素
		a.add("a");
		a.add("b");
		a.add("c");
		a.add("d");
		System.out.println(a);
		// 获得Iterator对象
		Iterator it = a.iterator();
		it.next();
		it.remove();
		System.out.println(a);
		it.next();
		it.remove();
		System.out.println(a);
	}
}
