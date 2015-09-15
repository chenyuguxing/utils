package 第十二章.实例128;
package security;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordDemo{
	// 口令和文本输入域
	JPasswordField passwordField = new JPasswordField(12);
	JTextField textField = new JTextField(12);
	
	public static void main(String[] args){
		PasswordDemo pd = new PasswordDemo();
		pd.go();
	}
	
	void go(){
		// 设置口令回显字符为X
		passwordField.setEchoChar('X');
		// 创建各个标签、按钮
		final JFrame f = new JFrame("PasswordDemo");
		JLabel label1 = new JLabel("输入用户名: ");
		JLabel label2 = new JLabel("输入口令");
		JButton button1 = new JButton("确定");
		JButton button2 = new JButton("取消");
		
		// 鼠标单击“OK”按钮则执行这段代码
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// 获取输入的名字
				String name = textField.getText();
				// 获取输入的口令值
				char[] password = passwordField.getPassword();
				System.out.println("输入用户名: " + name);
				System.out.println("输入口令: "+ new String(password));
			}
		});
		
		// 使用网络布局
		JPanel contentPane = new JPanel(new GridLayout(3,2));
		contentPane.add(label1);
		contentPane.add(textField);
		contentPane.add(label2);
		contentPane.add(passwordField);
		contentPane.add(button1);
		contentPane.add(button2);
		f.setContentPane(contentPane);
		
		// 关闭窗口
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		f.pack();
		f.setVisible(true);
	}
}
		
			
