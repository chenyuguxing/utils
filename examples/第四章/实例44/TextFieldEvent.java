package 第四章.实例44;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class TextFieldEvent extends Applet implements ActionListener {
	TextField text1, text2, text3;

	public void init() {
		text1 = new TextField(10);
		text2 = new TextField(10);
		text3 = new TextField(20);
		add(text1);
		add(text2);
		add(text3);
		text1.addActionListener(this); //将主类的实例作为text1的监视器，
		//因此主类必须实现接口ActionListener 。
		text2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == text1) {
			String word = text1.getText();
			if (word.equals("boy")) {
				text3.setText("男孩");
			} else if (word.equals("girl")) {
				text3.setText("女孩");
			} else if (word.equals("sun")) {
				text3.setText("太阳");
			} else {
				text3.setText("没有该单词");
			}
		} else if (e.getSource() == text2) {
			String word = text2.getText();
			if (word.equals("男孩")) {
				text3.setText("boy");
			} else if (word.equals("女孩")) {
				text3.setText("girl");
			} else if (word.equals("太阳")) {
				text3.setText("sun");
			} else {
				text3.setText("没有该单词");
			}
		}
	}
}
