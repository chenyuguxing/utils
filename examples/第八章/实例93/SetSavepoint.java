package 第八章.实例93;
// SetSavepoint.java

import java.sql.*;

public class SetSavepoint {

	public static void main(String args[]) {
		String url = "jdbc:microsoft:sqlserver://192.168.28.129:1433;DatabaseName=coffee;SelectMethod=cursor";
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		} 
		catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			Connection con = DriverManager.getConnection(url, "sa","sa");
			con.setAutoCommit(false);
			String query = "SELECT COF_NAME, PRICE FROM COFFEES "
					+ "WHERE TOTAL > ?";
			String update = "UPDATE COFFEES SET PRICE = ? "
					+ "WHERE COF_NAME = ?";
			PreparedStatement getPrice = con.prepareStatement(query);
			PreparedStatement updatePrice = con.prepareStatement(update);
			getPrice.setInt(1, 7000);
			ResultSet rs = getPrice.executeQuery();
			// 设置保存点
			Savepoint save1 = con.setSavepoint();

			while (rs.next()) {
				String cof = rs.getString("COF_NAME");
				float oldPrice = rs.getFloat("PRICE");
				float newPrice = oldPrice + (oldPrice * .05f);
				updatePrice.setFloat(1, newPrice);
				updatePrice.setString(2, cof);
				updatePrice.executeUpdate();
				System.out.println("New price of " + cof + " is " + newPrice);
				if (newPrice > 11.99) {
					// 在一定条件下回滚
					con.rollback(save1);
				}
			}

			getPrice = con.prepareStatement(query);
			updatePrice = con.prepareStatement(update);
			getPrice.setInt(1, 8000);
			rs = getPrice.executeQuery();
			System.out.println();
			//Savepoint save2 = con.setSavepoint();

			while (rs.next()) {
				String cof = rs.getString("COF_NAME");
				float oldPrice = rs.getFloat("PRICE");
				float newPrice = oldPrice + (oldPrice * .05f);
				updatePrice.setFloat(1, newPrice);
				updatePrice.setString(2, cof);
				updatePrice.executeUpdate();
				System.out.println("New price of " + cof + " is " + newPrice);
				if (newPrice > 11.99) {
					//con.rollback(save2);
				}
			}

			con.commit();
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COF_NAME, " + "PRICE FROM COFFEES");
			System.out.println();
			while (rs.next()) {
				String name = rs.getString("COF_NAME");
				float price = rs.getFloat("PRICE");
				System.out.println("Current price of " + name + " is " + price);
			}
			con.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
