package 第九章.实例102.beans;

import java.io.*;
import javax.servlet.http.*; 
import java.sql.*;

public class DBCon implements HttpSessionBindingListener
{
	private Connection con = null; //与资料库连结有关的Bean属性

	public DBCon()  //在建构子中完成资料库连结
	{
		BulidConnection(); //建立资料库连结
	}
	
	private void BulidConnection() //建立资料库连结的方法
	{
		try{
            //	载入驱动程式类别
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
            //	建立资料库连线
			con = DriverManager.getConnection("jdbc:odbc:student");				
		}
		catch(Exception ex)
		{	
			System.out.println(ex.toString());
		}		
	}

	public Connection getConnection()
	{ 
		if(con == null)  //若con为null时, 重新建立资料库连结
			BulidConnection();
		return this.con;
	}	

	public void close()
	{
		try{
			con.close(); //关闭Connection物件					
			con =  null;
		}
		catch(SQLException sex)
		{	
			System.out.println(sex.toString());
		}	
	}

    //	当物件加入session时, 将自动执行此函数
	public void valueBound(HttpSessionBindingEvent event){}	

    //	当session物件消灭时, 将自动执行此函数
	public void valueUnbound(HttpSessionBindingEvent event)	
	{
		if(con != null)
			close(); //呼叫close方法
	}
}
