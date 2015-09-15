package 第四章.实例43;

import java.applet.*;
import java.awt.*;

public class TextFieldDemo extends Applet {
	TextField text1, text2, text3;

	public void init() {
		text1 = new TextField("输入密码：", 10);
		text1.setEditable(false);
		text2 = new TextField(10);
		text2.setEchoChar('*');
		text3 = new TextField("我是一个文本域", 20);
		add(text1);
		add(text2);
		add(text3);
		text3.setText("");
	}
}
