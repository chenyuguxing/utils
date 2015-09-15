package 第八章.实例90;
//ViewDB.java

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewDB {
	public static void main(String[] args) {
		JFrame frame = new ViewDBFrame();
		frame.show();
	}
}

class ViewDBFrame extends JFrame implements ActionListener {
	private JButton nextButton;
	private JPanel dataPanel;
	private JComboBox tableNames;
	private ArrayList fields;
	private Connection con;
	private Statement stmt;
	private DatabaseMetaData md;
	private ResultSet rs;
	
	public ViewDBFrame() {
		setTitle("ViewDB");
		setSize(300, 200);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Container contentPane = getContentPane();
		// 组合框，用来显示表名
		tableNames = new JComboBox();
		tableNames.addActionListener(this);
		dataPanel = new JPanel();
		contentPane.add(dataPanel, "Center");
		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		JPanel p = new JPanel();
		p.add(nextButton);
		contentPane.add(p, "South");
		fields = new ArrayList();

		try {
			con = getConnection();
			stmt = con.createStatement();
			md = con.getMetaData();   // 得到数据库的整体信息
			//  获得可用于一个分类表中的表的说明
			ResultSet mrs = md.getTables(null, null, null,
					new String[] { "TABLE" }); // 获得可用于一个分类表中的表的说明
			while (mrs.next())
				tableNames.addItem(mrs.getString(3));
			mrs.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
		contentPane.add(tableNames, "North");
	}

	public static Connection getConnection() throws SQLException, IOException {
		Properties props = new Properties();
		String fileName = "ViewDB.properties";
		FileInputStream in = new FileInputStream(fileName);
		// 从输入流读特性列表。
		props.load(in);  
        //  用该特性列表中的指定关键字jdbc.drivers来查找特性。 
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null) // 设置 Properties 参数指示的系统属性。
			System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		return DriverManager.getConnection(url, username, password);
	}

	private void add(Container p, Component c, GridBagConstraints gbc, int x,
			int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		p.add(c, gbc);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == nextButton) {
			showNextRow();  // 显示下一个表项
		} 
		else if (evt.getSource() == tableNames) {
			//  如果是组合框的选项发生了变化
			remove(dataPanel);
			dataPanel = new JPanel();
			fields.clear();
			dataPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weighty = 100;

			try {
				String tableName = (String) tableNames.getSelectedItem();
				if (rs != null)
					rs.close();
				rs = stmt.executeQuery("SELECT * FROM " + tableName);
				// ResultSetMetaData 对象可以用于查找 ResultSet 中的列的类型和特性
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					// 获得打印输出和显示的列标题
					String columnName = rsmd.getColumnLabel(i);
					// 获得列的正常的最大字符宽度。
					int columnWidth = rsmd.getColumnDisplaySize(i);
					JTextField tb = new JTextField(columnWidth);
					fields.add(tb);

					gbc.weightx = 0;  // 指定如何分布额外的水平空间为0
					gbc.anchor = GridBagConstraints.EAST;
					gbc.fill = GridBagConstraints.NONE;
					add(dataPanel, new JLabel(columnName), gbc, 0, i - 1, 1, 1);

					gbc.weightx = 100;
					gbc.anchor = GridBagConstraints.WEST;
					//  设置为在水平方向上改变组件的大小，而不是垂直方向
					gbc.fill = GridBagConstraints.HORIZONTAL;
					add(dataPanel, tb, gbc, 1, i - 1, 1, 1);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e);
			}
			getContentPane().add(dataPanel, "Center");
			doLayout();
			pack();
			showNextRow();
		}
	}

	public void showNextRow() {
		if (rs == null)
			return;
		{
			try {
				if (rs.next()) {
					for (int i = 1; i <= fields.size(); i++) {
						//  把当前行的列值作为一个 Java String 获取
						String field = rs.getString(i);
						JTextField tb = (JTextField) fields.get(i - 1);
						tb.setText(field);
					}
				} else {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				System.out.println("Error " + e);
			}
		}
	}
}
