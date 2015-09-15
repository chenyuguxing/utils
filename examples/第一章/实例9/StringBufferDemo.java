package 第一章.实例9;
public class StringBufferDemo{
	public StringBufferDemo(){
	}
	
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer("Java");
		double d = 1.4;
		sb.append(d).append("have").append(
				new String(" a lot of new features"));
		System.out.println(sb);
		sb.insert(4, "SDK");
		System.out.println(sb);
		sb.delete(4, 10);
		System.out.println(sb.reverse());
	}
}
