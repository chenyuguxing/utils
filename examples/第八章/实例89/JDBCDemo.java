package 第八章.实例89;
//JDBCDemo.java
//Connect database with JDBC 2.0 Driver

import java.sql.*;
public class JDBCDemo
{
	private String dbURL;			// 数据库标识名
	private String user;				// 数据库用户
	private String password;		// 数据库用户密码

	public Connection getConnection()
	{
		try
		{
            // 装载数据库驱动
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			return DriverManager.getConnection(dbURL,user,password);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return null;
	}
	public void setURL(String dbURL)
	{
		this.dbURL=dbURL;		// 设置数据库标识
	}
	public void setUser(String user)
	{
		this.user=user;			// 设置当前用户
	}
	public void setPassword(String password)
	{
		this.password=password;	// 设置用户密码
	}
	
	public static void main(String args[])
	{
		try
		{
			JDBCDemo driver=new JDBCDemo();
			driver.setURL("jdbc:microsoft:sqlserver://192.168.28.129:1433;DatabaseName=student");
			driver.setUser("sa");
			driver.setPassword("sa");
			Connection con=driver.getConnection();	// 得到数据库连接
			System.out.println(con.getCatalog());		// 打印当前数据库目录名称
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
