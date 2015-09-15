package 第八章.实例92;
// TransactionDemo.java

import java.sql.*;

public class TransactionDemo {

	public static void main(String args[]) {
		String url = "jdbc:odbc:coffee";
		Connection con = null;
		Statement stmt;
		PreparedStatement updateSales;
		PreparedStatement updateTotal;
		String updateString = "update COFFEES " +
						"set SALES = ? where COF_NAME = ?";
		String updateStatement = "update COFFEES " +
				"set TOTAL = TOTAL + ? where COF_NAME = ?";
		String query = "select COF_NAME, SALES, TOTAL from COFFEES";

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} 
		catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url,"", "");
			updateSales = con.prepareStatement(updateString);
			updateTotal = con.prepareStatement(updateStatement);
			int [] salesForWeek = {175, 150, 60, 155, 90};
			String [] coffees = {"Colombian", "French_Roast",
								"Espresso", "Colombian_Decaf",
								"French_Roast_Decaf"};
			int len = coffees.length;
			// 禁止自动提交，设置回滚点
			con.setAutoCommit(false);
			for (int i = 0; i < len; i++) {
				updateSales.setInt(1, salesForWeek[i]);
				updateSales.setString(2, coffees[i]);
				updateSales.executeUpdate();

				updateTotal.setInt(1, salesForWeek[i]);
				updateTotal.setString(2, coffees[i]);
				updateTotal.executeUpdate();
				con.commit();  // 事务提交
			}

			con.setAutoCommit(true);

			updateSales.close();
			updateTotal.close();

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) { // 打印COFFEES表的信息
				String c = rs.getString("COF_NAME");
				int s = rs.getInt("SALES");
				int t = rs.getInt("TOTAL");
				System.out.println(c + "     " +  s + "    " + t);
			}

			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back");
					con.rollback(); // 操作不成功，回滚
				} catch(SQLException excep) {
					System.err.print("SQLException: ");
					System.err.println(excep.getMessage());
				}
			}
		}
	}
}
