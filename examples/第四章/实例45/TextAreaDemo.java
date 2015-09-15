package 第四章.实例45;

import java.applet.*;
import java.awt.*;

public class TextAreaDemo extends Applet {
	TextArea text1, text2;

	public void init() {
		text1 = new TextArea("I am a student", 6, 16);
		text2 = new TextArea(6, 16);
		add(text1);
		add(text2);
		// 将字符串加到文本域的末尾
		text2.append("I am learning programing.");
		// 在文本域的指定位置插入字符串
		text2.insert("Java ", 14);
		// 选定文本域text1中的所有内容
		text1.selectAll();
		int length = text2.getText().length();
		// 设定文本域text2中指定位置的字符串
		text2.setSelectionStart(2);
		text2.setSelectionEnd(length);
	}
}
