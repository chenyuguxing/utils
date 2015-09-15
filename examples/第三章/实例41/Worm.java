package 第三章.实例41;
//: :Worm.java
package javaio;
import java.io.*;

class Data implements Serializable {
  private int i;
  Data(int x) { i = x; }
  public String toString() {
    return Integer.toString(i);
  }
}

public class Worm implements Serializable {
  //产生随机正整数（小于10）
  private static int r() {
    return (int)(Math.random() * 10);
  }
  private Data[] d = {
    new Data(r()), new Data(r()), new Data(r())
  };
  private Worm next;
  private char c;
  //i表示worm链中的第几段
  Worm(int i, char x) {
    System.out.println(" Worm constructor: " + i);
    c = x;
    if(--i > 0)
      next = new Worm(i, (char)(x + 1));
  }
  Worm() {
    System.out.println("Default constructor");
  }
  public String toString() {
    String s = ":" + c + "(";
    for(int i = 0; i < d.length; i++)
      s += d[i].toString();
    s += ")";
    if(next != null)
      s += next.toString();
    return s;
  }
  // 抛出异常到控制台
  public static void main(String[] args) 
  throws ClassNotFoundException, IOException {
    Worm w = new Worm(6, 'a');
    System.out.println("w = " + w);
    ObjectOutputStream out =
      new ObjectOutputStream(
        new FileOutputStream("worm.out"));
    out.writeObject("Worm storage");
    out.writeObject(w);  //将产生的对象写入到worm.out文件
    out.close(); 
    ObjectInputStream in =
      new ObjectInputStream(
        new FileInputStream("worm.out"));  
    String s = (String)in.readObject();  //从worm.out中读取对象
    Worm w2 = (Worm)in.readObject();
    System.out.println(s + ", w2 = " + w2);
    //另一种用ByteArray作为读写对象的次第读写方法
    ByteArrayOutputStream bout =
      new ByteArrayOutputStream();
    ObjectOutputStream out2 =
      new ObjectOutputStream(bout);
    out2.writeObject("Worm storage");
    out2.writeObject(w);
    out2.flush();
    ObjectInputStream in2 =
      new ObjectInputStream(
        new ByteArrayInputStream(
          bout.toByteArray()));
    s = (String)in2.readObject();
    Worm w3 = (Worm)in2.readObject();
    System.out.println(s + ", w3 = " + w3);
  }
}
