package 第九章.实例97;
<%@ page contentType="text/html; charset=GB2312" %>
<%@ page language="java" %>
<HTML>
<HEAD>
<TITLE>汉字处理</TITLE>
</HEAD>
<BODY>
<CENTER>
<FONT SIZE = 5 COLOR = blue>汉字处理</FONT>
</CENTER>
<BR>
<HR>
<BR>
<H3>
<FONT COLOR = RED>
<CENTER>
<!--取得Name文本区的输入值-->
<%
	String tmpName = test.ISOtoGb2312.convert( request.getParameter("inputName") );
	out.println(tmpName);
%>
</FONT>
对大家说：
</CENTER>
<BR>
<FONT COLOR=BLUE>
<!--取得文字区的输入值-->
<%
	String tmpMsg = test.ISOtoGb2312.convert( request.getParameter("txtaMsg") );
	out.println(tmpMsg);
%>
</FONT>
</H3>

</BODY>
</HTML>
