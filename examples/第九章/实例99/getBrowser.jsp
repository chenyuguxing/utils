package 第九章.实例99;
<%@ page contentType="text/html;charset=gb2312" %>
<%@ page language="java" %>
<%@ page import="test.*"%>
<HTML>
<HEAD>
<TITLE>Get Browser</TITLE>
</HEAD>
<BODY>
<center>
<font size=5 color=blue>获得客户端浏览器信息</font>
<center>
<%
	Browser eins = new Browser(request, session);
%>
<br>
<hr>
<br>
<font size=4 face="隶书" color=blue>
您使用的浏览器是：<%=eins.getName()%>
</font>
<br>
<font size=r face="隶书" color=blue>
它是<%=eins.getCompany()%>公司的<%=eins.getVersion()%>版
</font>
<br>
<font size=4 face="隶书" color=blue>
您使用的操作系统是：<%=eins.getOs()%>
</font>
<br>
</BODY>
</HTML>
