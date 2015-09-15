package 第三章.实例40;
// ZipCompress.java
package javaio;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ZipCompress {
  // 抛出异常到控制台:
  public static void main(String[] args) 
  throws IOException {
    FileOutputStream f =
      new FileOutputStream("test.zip");  //建立输出文件流
    CheckedOutputStream csum = new CheckedOutputStream(
        f, new Adler32());  //建立冗余验证流
    ZipOutputStream out =
      new ZipOutputStream(
        new BufferedOutputStream(csum));  //建立zip流
    out.setComment("A test of Java Zipping");
    for(int i = 0; i < args.length; i++) {
      System.out.println(
        "Writing file " + args[i]);
      BufferedReader in =
        new BufferedReader(
          new FileReader(args[i]));
      out.putNextEntry(new ZipEntry(args[i]));  //建立压缩实体
      int c;
      while((c = in.read()) != -1){
        out.write(c);
    }
      in.close();  //关闭文件流，释放资源
    }
    out.close();
    System.out.println("Checksum: " +
      csum.getChecksum().getValue());  //输出效验码
    // 解压缩
    System.out.println("Reading file");
    FileInputStream fi =
       new FileInputStream("test.zip");
    CheckedInputStream csumi =
      new CheckedInputStream(
        fi, new Adler32());
    ZipInputStream in2 =
      new ZipInputStream(
        new BufferedInputStream(csumi));
    ZipEntry ze;
    while((ze = in2.getNextEntry()) != null) {
      System.out.println("Reading file " + ze);
      int x;
      while((x = in2.read()) != -1)
        System.out.write(x);
    }
    System.out.println("Checksum: " +
      csumi.getChecksum().getValue());
    in2.close();
    //输出解压缩后的各个文件名
    ZipFile zf = new ZipFile("test.zip");
    Enumeration e = zf.entries();
    while(e.hasMoreElements()) {
      ZipEntry ze2 = (ZipEntry)e.nextElement();
      System.out.println("File: " + ze2);
    }
  }
} 
