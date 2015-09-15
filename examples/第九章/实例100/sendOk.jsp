package 第九章.实例100;
<%@ page contentType="text/html;charset=gb2312" %>
<html>
<head>
<title>Your message was sent to destination successfully</title>
<head>
<body bgcolor=#add8e6>
<center>
<font size=5 color=blue>发送邮件</font>
</center>
<br>
<hr>
<br>
<center>
<h4>恭喜!你的邮件已成功发送到你朋友的信箱中了.<h4>
<table cellspacing="2" cellpadding="2" border="1">
<tr>
<td>朋友的信箱:</td><td><%=request.getParameter("to")%></td>
</tr><tr>
<td>邮件主题：:</td><td>
&nbsp&nbsp<%
byte[] subjectTemp= request.getParameter("subject").getBytes("ISO8859_1");
String subject=new String(subjectTemp);
 out.println(subject);
%>
</td>
</tr>
<tr><td colspan="2" align=center><a href="javaMail.html" style="color: rgb(ff,0,0)">返回</a>
</td></tr>
</table>
</center>
</body>
</html>
