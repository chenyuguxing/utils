package 第九章.实例103;
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java" %>
<%@ page	errorPage="orderErrorPage.jsp" %>
<%@ page	import="java.util.*,beans.*;" %>
<jsp:useBean id="customer" 
  class="beans.Customer" scope="request">
	<jsp:setProperty name="customer" 
	  property="*" /> 
</jsp:useBean>

<jsp:useBean id="PepsiCola" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="PepsiCola" 
	  property="name" param="PepsiColaName" /> 
	<jsp:setProperty name="PepsiCola" 
	  property="price" param="PepsiColaPrice" /> 
	<jsp:setProperty name="PepsiCola" 
	  property="item" param="PepsiColaItem" /> 
</jsp:useBean>	

<jsp:useBean id="SevenUP" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="SevenUP" 
	  property="name" param="SevenUPName" /> 
	<jsp:setProperty name="SevenUP" 
	  property="price" param="SevenUPPrice" /> 
	<jsp:setProperty name="SevenUP" 
	  property="item" param="SevenUPItem" /> 
</jsp:useBean>	

<jsp:useBean id="OrangeJuice" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="OrangeJuice" 
	  property="name" param="OrangeJuiceName" /> 
	<jsp:setProperty name="OrangeJuice" 
	  property="price" param="OrangeJuicePrice" /> 
	<jsp:setProperty name="OrangeJuice" 
	  property="item" param="OrangeJuiceItem" /> 
</jsp:useBean>	

<jsp:useBean id="FrenchFries" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="FrenchFries" 
	  property="name" param="FrenchFriesName" /> 
	<jsp:setProperty name="FrenchFries" 
	  property="price" param="FrenchFriesPrice" /> 
	<jsp:setProperty name="FrenchFries" 
	  property="item" param="FrenchFriesItem" /> 
</jsp:useBean>	

<jsp:useBean id="Sandwiches" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="Sandwiches" 
	  property="name" param="SandwichesName" /> 
	<jsp:setProperty name="Sandwiches" 
	  property="price" param="SandwichesPrice" /> 
	<jsp:setProperty name="Sandwiches" 
	  property="item" param="SandwichesItem" /> 
</jsp:useBean>	

<jsp:useBean id="Nuggets" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="Nuggets" 
	  property="name" param="NuggetsName" /> 
	<jsp:setProperty name="Nuggets" 
	  property="price" param="NuggetsPrice" /> 
	<jsp:setProperty name="Nuggets" 
	  property="item" param="NuggetsItem" /> 
</jsp:useBean>	

<jsp:useBean id="Dessert" 
	class="beans.Price" scope="request">
	<jsp:setProperty name="Dessert" 
	  property="name" param="DessertName" /> 
	<jsp:setProperty name="Dessert" 
	  property="price" param="DessertPrice" /> 
	<jsp:setProperty name="Dessert" 
	  property="item" param="DessertItem" /> 
</jsp:useBean>	

<html>
<head>
</head>
<body>
<center>
<% 
	if( customer.getName().trim().equals("") ||
	    customer.getTel().trim().equals("") ||
	    customer.getAddr().trim().equals("") ){
		throw new ServletException( "请填写完整客户信息！" );	
	}
%>	
<p><font size="3"> 
	客户：<%=ISOtoGb2312.convert(customer.getName())%>
	电话：<%=customer.getTel()%>
	住址：<%=ISOtoGb2312.convert(customer.getAddr())%>
</font></p>
<hr width="50%">	
<% 
	int totalPrice = 0;
	Enumeration itemVecEmu = PepsiCola.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}		
	totalPrice += PepsiCola.getTotalPrice();
	
	itemVecEmu = SevenUP.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}	
	totalPrice += SevenUP.getTotalPrice();
		
	itemVecEmu = OrangeJuice.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}		
	totalPrice += OrangeJuice.getTotalPrice();	
	
	itemVecEmu = FrenchFries.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}		
	totalPrice += FrenchFries.getTotalPrice();
		
	itemVecEmu = Sandwiches.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}		
	totalPrice += Sandwiches.getTotalPrice();	
	
	itemVecEmu = Nuggets.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}		
	totalPrice += Nuggets.getTotalPrice();
	
	itemVecEmu = Dessert.getItemList().elements();
	while( itemVecEmu.hasMoreElements() ){
%>
		<p><%= ISOtoGb2312.convert(itemVecEmu.nextElement().toString()) %></p>	
<%
	}	
	totalPrice += Dessert.getTotalPrice()	;
%>
<hr width="50%">	
<% 
	if( totalPrice==0 ){
		throw new ServletException( "没有订购任何快餐！" );
	}
%>	
<%= "总价是：" + totalPrice %>
</center>
</body>
</html>
