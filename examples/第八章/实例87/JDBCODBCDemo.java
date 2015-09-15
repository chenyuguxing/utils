package 第八章.实例87;
//JDBCODBCDemo.java
import java.sql.*;

public class JDBCODBCDemo
{
	private String dbURL;			//数据库标识名
	private String user;				//数据库用户
	private String password;		//数据库用户密码
	public static void main(String args[])
	{
		try
		{
			JDBCODBCDemo bridge=new JDBCODBCDemo();
			bridge.setURL("jdbc:odbc:student");
			bridge.setUser("");
			bridge.setPassword("");
			Connection con=bridge.getConnection();	//得到数据库连接
			System.out.println(con.getCatalog());		//打印当前数据库目录名称
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public Connection getConnection()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	//装载数据库驱动
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
		this.dbURL=dbURL;		//设置数据库标识
	}
	public void setUser(String user)
	{
		this.user=user;			//设置当前用户
	}
	public void setPassword(String password)
	{
		this.password=password;	//设置用户密码
	}
}
