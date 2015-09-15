package 第十四章.实例136;
public class TestDLL {
	static {
		System.loadLibrary("Test"); 
	}
	public native static void helloWorld();
	public native static String cTOJava();
	
	public static void main(String[] args)
	{
		TestDLL test = new TestDLL();
		test.helloWorld();
		System.out.println(test.cTOJava());
	}
}
