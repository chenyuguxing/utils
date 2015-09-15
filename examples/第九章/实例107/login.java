package 第九章.实例107;
//login.java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import 第九章.实例107.beans.DBConn;

import java.sql.*;

public class login extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (this.checklogin(username, password)) {
			Cookie mylogin = new Cookie("username", username);
			mylogin.setVersion(1);
			mylogin.setPath("/");
			mylogin.setComment("Your login username");
			res.addCookie(mylogin);
		}
		//　修改重定向的URL，指向首页
		res.sendRedirect("/Login/index.jsp");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		doGet(req, res);
	}

	public static String getCookieValue(
		Cookie[] cookies,
		String cookieName,
		String defaultValue) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return (defaultValue);
	}

	public boolean checklogin(String username, String password) {
		try {
			DBConn loginConn = new DBConn();
			loginConn.executeQuery(
				"select * from Users where name='" + username + "'");
			if (loginConn.rs_next()) {
				System.out.println("Connection created!");
				if (loginConn.rs_getString("pwd").trim().equals(password)) {
					System.out.println(loginConn.rs_getString("name"));
					return true;
				} else {
					return false;
				}
			}
			return false;
		} 
		catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
}
