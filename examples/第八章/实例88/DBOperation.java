package 第八章.实例88;
import java.sql.*;

import 第八章.实例87.JDBCODBCDemo;
public class DBOperation
{
	private Statement sta;			//用于数据库操作的Statement
	public static void main(String args[])
	{
		try
		{
			JDBCODBCDemo bridge=new JDBCODBCDemo();
			DBOperation operator=new DBOperation();
			ResultSet rs;				//结果集
			String sqlCommand;		//用于操作的SQL命令
			bridge.setURL("jdbc:odbc:student");
			bridge.setUser("");
			bridge.setPassword("");
			Connection con=bridge.getConnection();
			operator.setStatement(con);		//设置Satement
			//查询操作
			sqlCommand="SELECT * FROM STUDENTS";
			System.out.println("\n######### Query #########");
			System.out.println("input SQL command"+sqlCommand+"\n");
			rs=operator.executeQuery(sqlCommand);
			rs.next();				
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println(rs.getString("NAME"));
				rs.next();
			}
			//修改操作
			sqlCommand=
				"UPDATE STUDENTS SET AGE=22 WHERE NAME=\'Andy\'";
			System.out.println("\n######### modify #########");
			System.out.println("input SQL command"+sqlCommand+"\n");
			operator.executeUpdate(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE NAME=\'Andy\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println(rs.getString("NAME")+
					"'s age is now "+rs.getInt("AGE"));
				rs.next();
			}
			//插入操作
			sqlCommand="INSERT INTO STUDENTS(ID,NAME,AGE)"+
				"VALUES(\'5\',\'Lilei\',1)";
			System.out.println("\n######### Add #########");
			System.out.println("??SQL??"+sqlCommand+"\n");
			operator.executeInsert(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE ID=\'5\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println("ID "+rs.getString("ID")+
					" added "+rs.getString("NAME"));
				rs.next();
			}
			//删除操作
			sqlCommand="DELETE FROM STUDENTS WHERE ID=\'5\'";
			System.out.println("\n######### Delete #########");
			System.out.println("input SQL command "+sqlCommand+"\n");
			operator.executeDelete(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE ID=\'5\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			if(!rs.next())
			{
				System.out.println("ID = 5 doesn't exists");
			}
			//??????,????
			rs.close();
			operator.closeStatement();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public void setStatement(Connection con)
	{
		try
		{
			this.sta=con.createStatement();		//Statement中有关数据库查询的函数
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public ResultSet executeQuery(String sqlCommand)
	{
		try
		{
			//Statement中修改数据库的函数
			return sta.executeQuery(sqlCommand); 
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return null;
	}
	
	public void executeUpdate(String sqlCommand)
	{
		try
		{
			sta.executeUpdate(sqlCommand);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public void executeInsert(String sqlCommand)
	{
		try
		{
			sta.executeUpdate(sqlCommand);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	} 
	public void executeDelete(String sqlCommand)
	{
		try
		{
			sta.executeUpdate(sqlCommand);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}  
	public void closeStatement()
	{
		try
		{
			sta.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
