package 第十一章.实例120;
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import=" myEjb.*"%>
<%@ page import="javax.rmi.PortableRemoteObject"%>
<%@ page import="java.util.Properties"%>
<html>
<head>
<title>
BSBrowser
</title>
</head>
<body>
<h1>
<%="This is BS EJB example!"%><br>
<%
try{
	//为初始化上下文获得一个系统属性对象
  Properties pro=System.getProperties();
  //初始化并得到一个上下文
  Context ctx = new InitialContext(pro);
  //查询jndi名，通过强制转型得到Home接口
  HelloWorldHome home = (HelloWorldHome) javax.rmi.PortableRemoteObject.narrow (ctx.lookup ("HelloWorldHome "), HelloWorldHome.class);
  //得到远程接口的引用
  HelloWorld test=home.create();
  out.write(test.printHelloWorld());
}
catch(Exception e)
{
  out.println(e.toString());
}
%>
<%="This is Browser speaking."%><br>
<%="The example is over!"%><br>

</h1>
</body>
</html>
