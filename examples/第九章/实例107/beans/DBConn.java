//DBConn.java
package 第九章.实例107.beans;
import java.sql.*;

public class DBConn {
	public String sql_driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	public String sql_url = "jdbc:microsoft:sqlserver://192.168.29.226:1433";
	public String sql_DBName = "Reg";
	public String user = "sa";
	public String pwd = "sa";

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	public boolean setDriver(String drv) {
		this.sql_driver = drv;
		return true;
	}

	public String getDriver() {
		return this.sql_driver;
	}

	public boolean setUrl(String url) {
		this.sql_url = url;
		return true;
	}

	public boolean setDBName(String dbname) {
		this.sql_DBName = dbname;
		return true;
	}

	public String getDBName() {
		return this.sql_DBName;
	}

	public boolean setUser(String user) {
		this.user = user;
		return true;
	}

	public String getUser() {
		return this.user;
	}

	public boolean setPwd(String pwd) {
		this.pwd = pwd;
		return true;
	}

	public String getPwd() {
		return this.pwd;
	}

	public DBConn() {
		try {
			Class.forName(sql_driver); //加载数据库驱动程序
			this.conn =
				DriverManager.getConnection(
					sql_url + ";DatabaseName=" + sql_DBName,
					user,
					pwd);
			this.stmt = this.conn.createStatement();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	//执行查询操作
	public ResultSet executeQuery(String strSql) {
		try {
			this.rs = stmt.executeQuery(strSql);
			return this.rs;
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		} catch (NullPointerException e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//执行数据的插入、删除、修改操作
	public boolean execute(String strSql) {
		try {
			if (this.stmt.executeUpdate(strSql) == 0)
				return false;
			else
				return true;
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		} catch (NullPointerException e) {
			System.out.println(e.toString());
			return false;
		}
	}

	//结果集指针跳转到某一行
	public boolean rs_absolute(int row) {
		try {
			this.rs.absolute(row);
			return true;
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public void rs_afterLast() {
		try {
			this.rs.afterLast();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	public void rs_beforeFirst() {
		try {
			this.rs.beforeFirst();
		} catch (SQLException e) {
			System.out.print(e.toString());
		}
	}

	public void rs_close() {
		try {
			this.rs.close();
		} catch (SQLException e) {
			System.out.print(e.toString());
		}
	}

	public void rs_deleteRow() {
		try {
			this.rs.deleteRow();
		} catch (SQLException e) {
			System.out.print(e.toString());
		}
	}

	public boolean rs_first() {
		try {
			this.rs.first();
			return true;
		} catch (SQLException e) {
			System.out.print(e.toString());
			return false;
		}
	}

	public String rs_getString(String column) {
		try {
			return this.rs.getString(column);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//此方法用于获取大段文本，
	//将其中的回车换行替换为<br>
	//输出到html页面
	public String rs_getHtmlString(String column) {
		try {
			String str1 = this.rs.getString(column);
			String str2 = "\r\n";
			String str3 = "<br>";
			return this.replaceAll(str1, str2, str3);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//把str1字符串中的str2字符串替换为str3字符串
	private static String replaceAll(String str1, String str2, String str3) {
		StringBuffer strBuf = new StringBuffer(str1);
		int index = 0;
		while (str1.indexOf(str2, index) != -1) {
			index = str1.indexOf(str2, index);
			strBuf.replace(
				str1.indexOf(str2, index),
				str1.indexOf(str2, index) + str2.length(),
				str3);
			index = index + str3.length();

			str1 = strBuf.toString();
		}
		return strBuf.toString();
	}

	public int rs_getInt(String column) {
		try {
			return this.rs.getInt(column);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return -1;
		}
	}

	public int rs_getInt(int column) {
		try {
			return this.rs.getInt(column);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return -1;
		}
	}

	public boolean rs_next() {
		try {
			return this.rs.next();
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
	}

	//判断结果集中是否有数据
	public boolean hasData() {
		try {
			boolean has_Data = this.rs.first();
			this.rs.beforeFirst();
			return has_Data;
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean rs_last() {
		try {
			return this.rs.last();
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean rs_previous() {
		try {
			return this.rs.previous();
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
}
