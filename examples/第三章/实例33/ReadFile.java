package 第三章.实例33;
//ReadFile.java
package javaio;
import java.io.*;
public class ReadFile
{
	public static void main(String[] args) 
	{
		try
		{
			String fileName = "D:\\test.txt"; //文件名
			FileInputStream in = new FileInputStream(new File(fileName));
			//当文件没有结束时每次读取一个字节显示
			while (in.available()>0)
			{
				System.out.print((char)in.read());
			}
			in.close();
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}
