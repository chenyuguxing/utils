package 第九章.实例103;
<%@ page language="java" isErrorPage="true" %>
<%@ page contentType="text/html; charset=gb2312" %>
<html>
<head>
<title>orderErrorPage</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<body bgcolor="#FFFFFF">
<div align="center">
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p><%= exception.toString() %></P>
  <form name="form1" action="webOrder.html" >
    <input type="submit" name="Submit" value=" 回订购页 " >
  </form>
  <p>&nbsp;</p>
</div>
</body>
</html>
