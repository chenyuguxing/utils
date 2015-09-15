package 第二章.实例28;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Run extends Applet implements Runnable, ActionListener {
	Button b = new Button("go");
	TextField text = null;
	Thread judgment, athlete1, athlete2;
	int x = 10; //线程运动的起始位置。
	Graphics mypen = null;
	public void init() {
		b.addActionListener(this);
		text = new TextField(20);
		judgment = new Thread(this);
		athlete1 = new Thread(this);
		athlete2 = new Thread(this);
		add(b);
		add(text);
		mypen = getGraphics();
	}
	public void start() {
		judgment.start();
	}
	public void actionPerformed(ActionEvent e) {
		judgment.interrupt(); //点击按扭结束发令员的生命。
	}
	public void run() {
		if (Thread.currentThread() == judgment) {
			while (true) {
				text.setText("准备跑... ...");
				text.setText("......");
				try {
					judgment.sleep(30);
				} catch (InterruptedException e) { //点击按扭结束生命，并让运动员_1开始跑。
					text.setText("跑");
					athlete1.start();
					break;
				}
			}
		}
		if (Thread.currentThread() == athlete1) {
			while (true) {
				x = x + 1;
				mypen.setColor(Color.blue);
				mypen.clearRect(10, 80, 99, 100); //显示线程运动的动画。  
				mypen.fillRect(x, 85, 5, 5);
				try {
					athlete1.sleep(10);
				} catch (InterruptedException e) { //通知运动员_2开始跑，运动员_1结束生命： 
					athlete2.start();
					return;
				}
				if (x >= 100) {
					athlete1.interrupt(); //运动员_1当跑到100米处时结束生命。
				}
			}
		}
		if (Thread.currentThread() == athlete2) {
			while (true) {
				x = x + 1;
				mypen.setColor(Color.red);
				mypen.clearRect(105, 80, 150, 100); //显示线程运动的动画。 
				mypen.fillRect(x + 5, 85, 7, 7);
				try {
					athlete2.sleep(10);
				} catch (InterruptedException e) {
					text.setText("到达终点");
					return;
				}
				if (x >= 200) //运动员_2跑到200米处时结束生命。
				{
						athlete2.interrupt();
				}
			}
		}
	}
}
