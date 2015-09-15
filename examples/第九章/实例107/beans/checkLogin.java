//checkLogin.java
package 第九章.实例107.beans;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class checkLogin {
	public String username = "";

	public boolean check(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		String cookieName = "username";
		PrintWriter out = res.getWriter();
		try {
			Cookie[] myCookies = req.getCookies();
			this.username =
				this.getCookieValue(myCookies, cookieName, "not found");
		} 
		catch (Exception e) {
			return false;
		}

		if (!this.username.equals(new String("not found"))) {
			return true;
		} 
		else {
			return false;
		}

	}

	public String getUserName() {
		return this.username;
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
}
