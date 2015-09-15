package 第九章.实例100;
<%@ page contentType="text/html;charset=gb2312" %>
<%@ page errorPage="errorPage.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<html>
<head>
<title>发送邮件</title>
</head>
<body bgcolor=#add8e6>
<center>
<font size=5 color=blue>发送邮件</font>
</center>
<BR>
<HR>
<BR>
<%
String host = "202.96.44.20";
String to = request.getParameter("to");
String from = request.getParameter("from");
String cc= request.getParameter("cc");
String bcc= request.getParameter("bcc");
byte[] subjectTemp= request.getParameter("subject").getBytes("ISO8859_1");
String subject=new String(subjectTemp);
byte[] messageTextTemp = request.getParameter("body").getBytes("ISO8859_1");
String messageText=new String(messageTextTemp);
boolean sessionDebug = false;
//validate the email address form.
if ((to.trim().indexOf("@")==-1)||(to.trim().length()<5)){
%>
<jsp:forward page="error.jsp"/>
<%}
if ((from.trim().indexOf("@")==-1)||(from.trim().length()<5)){
%>
<jsp:forward page="error.jsp"/>
<%}
if ((cc.trim().length()>0&&cc.trim().length()<5)||((cc.trim().length()!=0)&&(cc.trim().indexOf("@")==-1)))
{
%>
<jsp:forward page="error1.jsp"/>
<%
}
if ((bcc.trim().length()>0&&bcc.trim().length()<5)||((bcc.trim().length()!=0)&&(bcc.trim().indexOf("@")==-1)))
{
%>
<jsp:forward page="error1.jsp"/>
<%
}
Properties props = System.getProperties();
props.put("mail.host", host);
props.put("mail.transport.protocol", "smtp");
Session mailSession = Session.getDefaultInstance(props, null);
mailSession.setDebug(sessionDebug);
try{
Message msg = new MimeMessage(mailSession);
msg.setFrom(new InternetAddress(from));
InternetAddress[] addressTo = {new InternetAddress(to)};
msg.setRecipients(Message.RecipientType.TO,addressTo);
if (cc.trim().length()!=0){
InternetAddress[] addressCc = {new InternetAddress(cc)};
msg.setRecipients(Message.RecipientType.CC,addressCc);
}
if (bcc.trim().length()!=0){
InternetAddress[] addressBcc = {new InternetAddress(bcc)};
msg.setRecipients(Message.RecipientType.BCC,addressBcc);
}
msg.setSubject(subject);
msg.setSentDate(new Date());
msg.setText(messageText);
Transport.send(msg);
}
catch(Exception ex){
out.println("sending failed");
}%>
<jsp:forward page="sendOk.jsp"/>
</table>
</body>
</html>
