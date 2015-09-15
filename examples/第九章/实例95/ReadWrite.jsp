package 第九章.实例95;
<%@ page language="java" import="java.io.*"%>
<%@ page contentType="text/html;charset=gb2312"%>
<html>
<head>
<title>读写文件</title>
<body>
<center>
<font size=6 color=blue>
读写文件示例
</font>
</center>
<%
//读文件
//变量声明
String strFileName; //文件名
File objFile; //文件对象
FileReader objFileReader; //读文件对象
char[] chrBuffer = new char[10]; //缓冲
int intLength; //实际读出的字符数(一个中文为一个字符)

//设置待读文件名
strFileName = "d:\\test.txt";

//创建文件对象
objFile = new File(strFileName);

//判断文件是否存在
if(objFile.exists()){//文件存在
  //创建读文件对象
  objFileReader = new FileReader(objFile);

  //读文件内容
  while((intLength=objFileReader.read(chrBuffer))!=-1){
    //输出
    out.write(chrBuffer,0,intLength);
  }

  //关闭读文件对象
  objFileReader.close();
}
else{//文件不存在
  out.println("下列文件不存在："+strFileName);
}
%>

<%
//写文件
String str = "预祝2008年奥运成功！";
//always give the path from root. This way it almost always works.
String nameOfTextFile = "D:\\out.txt";
try { 
  PrintWriter pw = new PrintWriter(new FileOutputStream(nameOfTextFile));
  pw.println(str);
  //clean up
  pw.close();
} catch(IOException e) {
  out.println(e.getMessage());
}
%>
</body>
</head>
</html>
