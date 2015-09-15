package 第五章.实例56;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JTableDemo implements TableModelListener
{
    JTable table = null;
    MyTable mt = null;
    JLabel label = null; //显示修改字段位置
    
    public JTableDemo() {
       	JFrame f = new JFrame();
       	mt=new MyTable();
       	// 增加JTable事件监听器
    	mt.addTableModelListener(this);
       	table=new JTable(mt);
       	// 创建组合框
       	JComboBox c = new JComboBox();
       	// 给组合框增加条目
       	c.addItem("广州");
       	c.addItem("上海");
       	c.addItem("北京");
       	// 利用组合框作为table的第一列值的选项
       	table.getColumnModel().
		   getColumn(1).setCellEditor(new DefaultCellEditor(c));    
       	// 设定table的显示的大小
       	table.setPreferredScrollableViewportSize(new Dimension(550, 40));
       	// 给table增加滚动条
        JScrollPane s = new JScrollPane(table);    
        label = new JLabel("修改字段位置：");
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.getContentPane().add(label, BorderLayout.SOUTH);
	    f.setTitle("TableEventHandle");
        f.pack();
        f.setVisible(true);
        
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void tableChanged(TableModelEvent e)
    {
    	int row = e.getFirstRow();  // 得到被修改的行（第一个)的行数
        int column = e.getColumn(); // 得到被修改的列数
        label.setText("修改字段位置："+(row+1)+" 行 "+(column+1)+" 列");
        //  得到第row行第6列的值，即看看是否作弊
        boolean cheat =((Boolean)(mt.getValueAt(row,6))).booleanValue();
        int grade1=((Integer)(mt.getValueAt(row,2))).intValue();
        int grade2=((Integer)(mt.getValueAt(row,3))).intValue();
	    int total = grade1+grade2;
    	if(cheat)        
    	{   //  如果作弊，则总分重设
    		if(total > 120)	
    			mt.mySetValueAt(new Integer(119),row,4);
    		else
    			mt.mySetValueAt(new Integer(total),row,4);
    		mt.mySetValueAt(new Boolean(false),row,5);
    	}
    	else
    	{
    		if(total > 120)	 // 判断该学生是否及格
    			mt.mySetValueAt(new Boolean(true),row,5);
    		else
    			mt.mySetValueAt(new Boolean(false),row,5);   
    		
    		mt.mySetValueAt(new Integer(total),row,4);
    	}
    	table.repaint();
    }
	
    public static void main(String args[]) {
        new JTableDemo();
    }
}

class MyTable extends AbstractTableModel {
    Object[][] p = {
    {"小明", "广州",new Integer(66), new Integer(32), new Integer(98),
      new Boolean(false),new Boolean(false)},
    {"小军", "上海",new Integer(85), new Integer(69), new Integer(154),
      new Boolean(true),new Boolean(false)},
	{"小东", "北京",new Integer(88), new Integer(70), new Integer(158),
      new Boolean(false),new Boolean(false)}};
    String[] n = {"姓名","居住地","语文","数学","总分","及格","作弊"};

    // 返回总列数
    public int getColumnCount() { 
        return n.length;
    }
    
    // 返回总行数
    public int getRowCount() {
        return p.length;
    }
    
    // 返回列名
    public String getColumnName(int col) {
        return n[col];
    }
    
    // 返回table的一个字段，若没有设置列标题则返回默认值,依次为A,B,C,...Z,AA,AB,..;
    // 若无此column,则返回一个空的String
    public Object getValueAt(int row, int col) {
        return p[row][col];
    }
    
    // 返回字段数据类型的类名称
	public Class getColumnClass(int c) {
        	return getValueAt(0, c).getClass();
    }
	
	// 返回指定字段是否可修改
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;	
    }
    
	// 给指定字段赋值，并通知table已修改
	public void setValueAt(Object value, int row, int col) {
        	p[row][col] = value;
        	fireTableCellUpdated(row, col);
    }
    
	// 自定义修改某指定字段
    public void mySetValueAt(Object value, int row, int col) {
    	p[row][col] = value;
    }
}
