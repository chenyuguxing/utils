package 第一章.实例22;
import java.util.*;

public class  VectorDemo{
	public VectorDemo(){
	}
	
	public static void main(String[] args){
		Vector v = new Vector();
		v.add(new Integer(13));
		v.add(new Integer(5));
		v.add(new Integer(8));
		v.add(new Integer(9));
		v.add(new Integer(2));
		// 在向量中的元素9前插入一个新元素
		v.insertElementAt(new Integer(7), v.indexOf(new Integer(9)));
		// 输出向量中的所有元素
		for(int i=0; i<v.size(); i++)
			System.out.print(v.elementAt(i)+"\t");
		System.out.println();
		// 输出向量中的第一个元素
		System.out.println("The first element in Vector is " + v.firstElement());
		// 输出向量中的最后一个元素
		System.out.println("The last element in Vector is "+ v.lastElement());
		Object[] content = new Object[v.size()];
		v.copyInto(content);
		int[] contentnumber = new int[v.size()];
		for(int i=0; i<content.length; i++)
			contentnumber[i] = Integer.parseInt(content[i].toString());
		// 将整型数组中的元素升序排列，并输出
		Arrays.sort(contentnumber);
		for(int i=0; i<contentnumber.length; i++)
			System.out.print(contentnumber[i] +"\t");
	}
}
