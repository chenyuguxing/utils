package 第九章.实例102;
<%@ page contentType="text/html; charset=GB2312" %>
<%@ page import="java.sql.*" %>
<%@ page language="java" %>
<HTML>
<HEAD>
<TITLE>用Bean链接数据库</TITLE>
</HEAD>
<BODY>
<CENTER>
<FONT SIZE = 5 COLOR = blue>
用Bean链接数据库
</FONT>
</CENTER>
<BR>
<HR>
<BR>
<CENTER>
<%--起始建立数据库链接的Bean对象--%>
<jsp:useBean id="ConBean" scope="session" class="beans.DBCon"/>
<%
//从Bean对象取得已完成建立的数据库链接
Connection con = ConBean.getConnection(); 
//建立Statement对象
Statement stmt = con.createStatement(); 
//建立ResultSet(结果集)对象，并执行SQL叙述
ResultSet rs = stmt.executeQuery("SELECT ID, name FROM STUDENTS"); 

%>
<TABLE  bgcolor=DodgerBlue>
	<TR  bgcolor=SkyBlue>	
	<TD><B>学 号</B></TD>
	<TD><B>姓 名</B></TD>	
	</TR>
	<%
	//利用while循环将数据表中的记录列出
	while (rs.next())
	{
		%>
		<TR bgcolor=LightGoldenrodYellow>		
		<TD><B><%= rs.getString("ID") %></B></TD>
		<TD><B><%= rs.getString("name") %></B></TD>			
		</TR>
		<%
	}
	rs.close(); //关闭记录集
	stmt.close(); //关闭Statement对象
	ConBean.close(); //关闭数据库链接	
%>	
</TABLE>
</CENTER>
</BODY>
</HTML>
