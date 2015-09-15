package 第三章.实例35;
//: Redirecting.java
package javaio;
import java.io.*;

public class Redirecting {
  public static void main(String[] args)
  throws IOException {
    PrintStream console = System.out;
    BufferedInputStream in = new BufferedInputStream(
      new FileInputStream("Redirecting.java"));
    PrintStream out = new PrintStream(
      new BufferedOutputStream(
        new FileOutputStream("test.out")));
    System.setIn(in);    //重定向标准输入
    System.setOut(out);  //重定向标准输出
    System.setErr(out);  //重定向标准错误
    BufferedReader br = new BufferedReader(
      new InputStreamReader(System.in));
    String s;
    while((s = br.readLine()) != null)
      System.out.println(s);
    out.close(); //  关闭输出，释放资源
    System.setOut(console);
  }
} ///:~
