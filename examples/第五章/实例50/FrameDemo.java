package 第五章.实例50;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameDemo {
	public static void main(String args[]){
		JFrame frame = new JFrame("画框显示");
		// 使用窗口监听器结束程序
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		JLabel label = new JLabel("JFrame"); //创建一个文本标签
		label.setPreferredSize(new Dimension(200,100)); //设定label的大小
		frame.getContentPane().add(label, BorderLayout.CENTER); //设置布局
		frame.pack();   // 使JFrame的大小自动适合所含组件大小
		frame.setVisible(true); //设定为可见的
	}
}
