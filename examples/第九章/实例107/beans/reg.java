//reg.java
package 第九章.实例107.beans;
import java.sql.*;

public class reg {
	public int newID = 0;
	public boolean result = false;
	public boolean reg(
		String username,
		String password,
		String confirm,
		String email) {
		try {
			if (!this.checkUser(username))
				return false;
			if (!this.checkPwd(password))
				return false;
			if (!this.verifyPwd(password, confirm))
				return false;
			if (!this.checkEmail(email))
				return false;
			if (!this.userNotExit(username))
				return false;
			this.getNewID();
			this.result = this.register(username, password, confirm, email);
			return this.result;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean checkUser(String user) {
		try {
			if (user.indexOf("'") != -1) {
				System.out.println("姓名中含有非法字符！");
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean checkPwd(String pwd) {
		try {
			if (pwd.indexOf("'") != -1) {
				System.out.println("密码中含有非法字符！");
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean verifyPwd(String pwd, String confirm) {
		try {
			if (!pwd.equals(confirm)) {
				System.out.println("两次输入的密码不一致！");
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean checkEmail(String email) {
		try {
			if (email.indexOf("'") != -1) {
				System.out.println("E-mail中含有非法字符！");
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean userNotExit(String user) {
		try {
			DBConn userDBConn = new DBConn();
			userDBConn.executeQuery(
				"select * from Users where name='" + user + "'");
			if (userDBConn.rs_next()) {
				System.out.println("用户名已存在，请选择其它的用户名！");
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public int getNewID() {
		try {
			DBConn newIDDBConn = new DBConn();
			newIDDBConn.executeQuery("select * from Users order by id desc");
			if (newIDDBConn.rs_next()) {
				this.newID = newIDDBConn.rs_getInt("id") + 1;
				System.out.println(this.newID);
			} else {
				this.newID = 1;
			}
			return this.newID;
		} catch (Exception e) {
			System.out.println(e.toString());
			return -1;
		}
	}

	public int getID() {
		return this.newID;
	}

	public boolean register(
		String username,
		String password,
		String confirm,
		String email) {
		try {
			DBConn regDBConn = new DBConn();
			String strSQL =
				"insert into Users(id,name,pwd,email) values('"
					+ this.newID
					+ "','"
					+ username
					+ "','"
					+ password
					+ "','"
					+ email
					+ "')";
			regDBConn.execute(strSQL);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
}
