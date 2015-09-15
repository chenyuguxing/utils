package 第九章.实例107;
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312" %>
<%@ page import="beans.reg"%>
<html>
<head>
<title>Java世界</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<%
 String username = new String(request.getParameter("username").getBytes("ISO8859_1")).trim();
 String password = new String(request.getParameter("password").getBytes("ISO8859_1")).trim();
 String confirm = new String(request.getParameter("confirm").getBytes("ISO8859_1")).trim();
 String email = new String(request.getParameter("email").getBytes("ISO8859_1")).trim();
%>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr bgcolor="green"> 
    <td height="80" colspan="5"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="120">&nbsp;</td>
          <td class="caption">Java世界</td>
          <td width="200">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td width="100" align="center" valign="top">&nbsp;</td>
    <td width="1" valign="top"></td>
    <td width="400" align="center" valign="top">
<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
<jsp:useBean id="regID" class="beans.reg" scope="session"/>
<%
 if(regID.reg(username,password,confirm,email))
 {
  out.print("ok");
  String newID = regID.getID() + ""; 
%>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
        <tr align="center"> 
          <td height="30" colspan="2" bgcolor="#CCCCCC" class="deepred">恭喜您，注册成功！</td>
        </tr>
        <tr> 
          <td width="50%" height="24" align="center" bgcolor="#FFFFFF">编号</td>
          <td align="center" bgcolor="#FFFFFF"><%=newID%></td>
        </tr>
        <tr> 
          <td width="50%" height="24" align="center" bgcolor="#FFFFFF">用户名</td>
          <td align="center" bgcolor="#FFFFFF"><%=username%></td>
        </tr>
        <tr> 
          <td width="50%" height="24" align="center" bgcolor="#FFFFFF">密码</td>
          <td align="center" bgcolor="#FFFFFF"><%=password%></td>
        </tr>
        <tr> 
          <td width="50%" height="24" align="center" bgcolor="#FFFFFF">E-mail</td>
          <td align="center" bgcolor="#FFFFFF"><%=email%></td>
        </tr>
      </table>
<%
  out.print("<br>");
  out.print("<a href=javascript:window.close()>关闭</a>");
 }else{
  out.print("注册失败！<br>");
  out.print("该用户名已有人使用，请使用另外的用户名！");
  out.print("<a href=javascript:history.go(-1)>返回</a>");
 } 
%>
   </td>
    <td width="1" valign="top"></td>
    <td width="100">&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="green"> 
    <td height="60" colspan="5" class="white">欢迎来到Java世界</td>
  </tr>
</table>
</body>
</html>
