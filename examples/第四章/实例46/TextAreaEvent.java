package 第四章.实例46;

import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class TextAreaEvent extends Applet implements TextListener {
	TextArea text1, text2;

	public void init() {
		text1 = new TextArea(6, 15);
		text2 = new TextArea(6, 15);
		add(text1);
		add(text2);
		text2.setEditable(false);
		text1.addTextListener(this);
	}

	public void textValueChanged(TextEvent e) {
		if (e.getSource() == text1) {
			String s = text1.getText();
			StringTokenizer fenxi = new StringTokenizer(s, " ,'\n'");
			int n = fenxi.countTokens();
			String a[] = new String[n];
			for (int i = 0; i <= n - 1; i++) {
				String temp = fenxi.nextToken();
				a[i] = temp;
			}
			for (int i = 0; i <= n - 1; i++) //按字典序从小到大排序。
			{
				for (int j = i + 1; j <= n - 1; j++) {
					if (a[j].compareTo(a[i]) < 0) {
						String t = a[j];
						a[j] = a[i];
						a[i] = t;
					}
				}
			}
			text2.setText(null); //刷新显示。
			for (int i = 0; i < n; i++) {
				text2.append(a[i] + "\n");
			}
		}
	}
}
