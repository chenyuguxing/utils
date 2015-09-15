package 第二章.实例27;
import java.awt.*;
import java.awt.event.*;

public class Mutative {
	public static void main(String args[]) {
		Mywin win = new Mywin();
		win.pack();
	}
}

class Mywin extends Frame implements Runnable {
	Button b = new Button("ok");
	int x = 5;
	Thread thread = null;
	Mywin() {
		setBounds(100, 100, 200, 200);
		setLayout(new FlowLayout());
		setVisible(true);
		add(b);
		b.setBackground(Color.green);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
        // 创建一个新的线程，窗口做目标对象，替线程thread实现接口Runnable。
		thread = new Thread(this); 
        // 在创建窗口时又开始了线程thread。
		thread.start(); 
	}
	
	public void run() 
	{
		while (true) {
			x = x + 1;
			if (x > 100)
				x = 5;
			b.setBounds(40, 40, x, x);
			try {
				thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}
}
