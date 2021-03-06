package 第八章.实例91;
// QueryDB.java

import java.net.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class QueryDB {
	public static void main(String[] args) {
		JFrame frame = new QueryDBFrame();
		frame.show();
	}
}

class QueryDBFrame extends JFrame implements ActionListener {
	private JComboBox authors;
	private JComboBox publishers;
	private JTextField priceChange;
	private JTextArea result;
	private Connection con;
	private Statement stmt;
	private PreparedStatement authorQueryStmt;
	private PreparedStatement authorPublisherQueryStmt;
	private PreparedStatement publisherQueryStmt;
	private PreparedStatement allQueryStmt;
	
	public QueryDBFrame() {
		setTitle("QueryDB");
		setSize(400, 300);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		authors = new JComboBox();
		authors.setEditable(false);
		authors.addItem("Any");

		publishers = new JComboBox();
		publishers.setEditable(false);
		publishers.addItem("Any");

		result = new JTextArea(4, 50);
		result.setEditable(false);

		priceChange = new JTextField(8);
		priceChange.setText("-5.00");

		try {
			//  连接数据库
			con = getConnection();
			stmt = con.createStatement();

			// 将数据库中的作者名添加到组合框
			String query = "SELECT Name FROM Authors";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				authors.addItem(rs.getString(1));

			//  将出版社名添加到组合框
			query = "SELECT Name FROM Publishers";
			rs = stmt.executeQuery(query);
			while (rs.next())
				publishers.addItem(rs.getString(1));
		} catch (Exception e) {
			result.setText("Error " + e);
		}

		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 100;
		gbc.weighty = 100;
		add(authors, gbc, 0, 0, 2, 1);

		add(publishers, gbc, 2, 0, 2, 1);

		gbc.fill = GridBagConstraints.NONE;
		JButton queryButton = new JButton("Query");
		queryButton.addActionListener(this);
		add(queryButton, gbc, 0, 1, 1, 1);

		JButton changeButton = new JButton("Change prices");
		changeButton.addActionListener(this);
		add(changeButton, gbc, 2, 1, 1, 1);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(priceChange, gbc, 3, 1, 1, 1);

		gbc.fill = GridBagConstraints.BOTH;
		add(result, gbc, 0, 2, 4, 1);
	}

	/** 利用属性文件中的信息连接数据库 **/
	public static Connection getConnection() throws SQLException, IOException {
		Properties props = new Properties();
		String fileName = "QueryDB.properties";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);

		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
			System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		return DriverManager.getConnection(url, username, password);
	}

	private void add(Component c, GridBagConstraints gbc, int x, int y, int w,
			int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		getContentPane().add(c, gbc);
	}

	public void actionPerformed(ActionEvent evt) {
		String arg = evt.getActionCommand();
		if (arg.equals("Query")) { // 用户按下Query按钮
			ResultSet rs = null;
			try {
				String author = (String) authors.getSelectedItem();
				String publisher = (String) publishers.getSelectedItem();
				if (!author.equals("Any") && !publisher.equals("Any")) {
					if (authorPublisherQueryStmt == null) {
						//  根据用户选择的出版社名和作者名查询相关的书名和书价
						String authorPublisherQuery = "SELECT Books.Price, Books.Title "
								+ "FROM Books, BooksAuthors, Authors, Publishers "
								+ "WHERE Authors.Author_Id = BooksAuthors.Author_Id AND "
								+ "BooksAuthors.ISBN = Books.ISBN AND "
								+ "Books.Publisher_Id = Publishers.Publisher_Id AND "
								+ "Authors.Name = ? AND "
								+ "Publishers.Name = ?";
						authorPublisherQueryStmt = con
								.prepareStatement(authorPublisherQuery);
					}
				
					authorPublisherQueryStmt.setString(1, author);
					authorPublisherQueryStmt.setString(2, publisher);
					rs = authorPublisherQueryStmt.executeQuery();
				} else if (!author.equals("Any") && publisher.equals("Any")) {
					if (authorQueryStmt == null) {
						//  根据用户选择的作者名查询相关的书名和书价
						String authorQuery = "SELECT Books.Price, Books.Title "
								+ "FROM Books, BooksAuthors, Authors "
								+ "WHERE Authors.Author_Id = BooksAuthors.Author_Id AND "
								+ "BooksAuthors.ISBN = Books.ISBN AND "
								+ "Authors.Name = ?";
						authorQueryStmt = con.prepareStatement(authorQuery);
					}
					authorQueryStmt.setString(1, author);
					rs = authorQueryStmt.executeQuery();
				} else if (author.equals("Any") && !publisher.equals("Any")) {
					if (publisherQueryStmt == null) {
						//  根据用户选择的出版社名查询相关的书名和书价
						String publisherQuery = "SELECT Books.Price, Books.Title "
								+ "FROM Books, Publishers "
								+ "WHERE Books.Publisher_Id = Publishers.Publisher_Id AND "
								+ "Publishers.Name = ?";
						publisherQueryStmt = con
								.prepareStatement(publisherQuery);
					}
					publisherQueryStmt.setString(1, publisher);
					rs = publisherQueryStmt.executeQuery();
				} else {
					if (allQueryStmt == null) {
						// 若用户未选任何信息，则输出所有的书名和对应的书价
						String allQuery = "SELECT Books.Price, Books.Title FROM Books";
						allQueryStmt = con.prepareStatement(allQuery);
					}
					rs = allQueryStmt.executeQuery();
				}

				result.setText("");
				while (rs.next())
					result.append(rs.getString(1) + " | " + rs.getString(2)
							+ "\n");
				rs.close();
			} catch (Exception e) {
				result.setText("Error " + e);
			}
		} else if (arg.equals("Change prices")) { //  用户选择“Change prices”按钮
			String publisher = (String) publishers.getSelectedItem();
			if (publisher.equals("Any"))
				result.setText("I am sorry, but I cannot do that.");
			else
				try {
					// 根据用户输入的新的书价更新Books表的数据
					String updateStatement = "UPDATE Books "
							+ "SET Price = Price + "
							+ priceChange.getText()
							+ " WHERE Books.Publisher_Id = "
							+ "(SELECT Publisher_Id FROM Publishers WHERE Name = '"
							+ publisher + "')";
					int r = stmt.executeUpdate(updateStatement);
					result.setText(r + " records updated.");
				} catch (Exception e) {
					result.setText("Error " + e);
				}
		}
	}

	public void dispose() {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
		}
	}
}
