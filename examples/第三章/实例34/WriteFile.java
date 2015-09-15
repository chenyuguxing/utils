package 第三章.实例34;
package javaio;
import java.io.*;

public class WriteFile{
	public static void main(String args[]){
		try{
			String fileName = "out.txt";//指定文件名
			File file = new File(fileName);
			FileOutputStream out = new FileOutputStream(file);//建立输出流
			byte[] b = new byte[1024];
			String str = "Nice to see you again!";
			b = str.getBytes();//String转化为byte[]
			out.write(b);//写入文本内容
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
	}
}
		
		
