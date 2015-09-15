package 第九章.实例107;
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312" %>
<html>
<head> 
<title>Java世界</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head> 
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<jsp:useBean id="checklogin" class="beans.checkLogin" scope="page"/>
<%
   boolean login = checklogin.check(request,response);
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
    <td width="200" align="center" valign="top"><table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
   <%
    if(!login){
   %>
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
   <form name="form1" method="post" action="login">
        <tr align="center" bgcolor="#CCCCCC"> 
          <td height="30" colspan="2" class="deepred">用户登录</td>
        </tr>
        <tr> 
          <td width="50%" height="24" align="center" bgcolor="#FFFFFF">用户名</td>
          <td align="center" bgcolor="#FFFFFF"><input name="username" type="text" id="username" size="10"></td>
        </tr>
        <tr> 
          <td height="24" align="center" bgcolor="#FFFFFF">密码</td>
          <td align="center" bgcolor="#FFFFFF"><input name="password" type="password" id="password" size="10"></td>
        </tr>
        <tr> 
          <td height="24" align="center" bgcolor="#FFFFFF"><a href="reg.jsp" target="_blank" class="red">注册</a></td>
          <td align="center" bgcolor="#FFFFFF"><input type="submit" name="Submit" value="进入"></td>
        </tr>
  </form>
      </table>
  <%
    } 
  else
  {
   out.println("您好，" + checklogin.getUserName() + "!");
  }
   %>
   </td>
 <td width="1" valign="top" bgcolor="#CCCCCC"></td>
    <td width="400">&nbsp;</td>
 <td width="1" valign="top" bgcolor="#CCCCCC"></td>
    <td width="200">&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="green"> 
    <td height="60" colspan="5" class="white">欢迎来到Java世界</td>
  </tr>
</table>
</body>
</html>
