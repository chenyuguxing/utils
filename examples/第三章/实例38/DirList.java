package 第三章.实例38;
package javaio;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DirList {
  public static void main(String[] args) {
    File path = new File(".");
    String[] list;
    if(args.length == 0){
      list = path.list();
    }
    else{
      //这里将输入的参数作为过滤因子
      list = path.list(new DirFilter(args[0]));
    }
    for(int i = 0; i < list.length; i++){
      System.out.println(list[i]);
    }
  }
}

class DirFilter implements FilenameFilter {
  private Pattern pattern;
  public DirFilter(String regex) {
    pattern = Pattern.compile(regex);
  }
  public boolean accept(File dir, String name) {
    //判断名为name的文件是不是符合过滤条件
    return pattern.matcher(
      new File(name).getName()).matches();
  }
}

