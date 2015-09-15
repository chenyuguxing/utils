package 第四章.实例48;
import java.awt.*;
import java.applet.Applet;

//定义Fader类
public class Fader extends Applet implements Runnable {
	Dimension d;  //定义一个Dimension对象
	Image ii;  //定义一个Image对象
	Graphics goff;  //定义一个Graphics对象
	Thread thethread;  //定义一个线程
	// 定义一个字体对象
	Font font = new Font("Helvetica", Font.BOLD, 36);
	FontMetrics fm;  //定义一个字体矩阵  
	String s1;
	String s2;
	int color1, color2;
	int dcolor1, dcolor2;
	
	//获取小应用程序信息
	public String getAppletInfo() {
		return ("Text fader");
	}

	//初始化
	public void init() {
		Graphics g;
		int i;
		d = size();         //获取小应用程序大小
		g = getGraphics();  //为画图做准备
		g.setFont(font);    //设置字体
		fm = g.getFontMetrics();    //获取字体矩阵
		setBackground(Color.black); //设置背景色
		s1 = "Welcome";      //所要显示的文字 
		s2 = "Best Wishes";  //所要显示的文字
		color1 = 10;
		color2 = 245;
		dcolor2 = -2;
		dcolor1 = 2;
	}

	//paint()方法
	public void paint(Graphics g) {
		if (goff == null && d.width > 0 && d.height > 0) {
			//创建一个可以使用的双缓冲图像	
			ii = createImage(d.width, d.height);
			goff = ii.getGraphics();
		}
		if (goff == null || ii == null)
			return;
		g.setFont(font);
		goff.setFont(font);
		if (color1 < color2) {
			//设置颜色，并将文字画在屏幕上
			goff.setColor(new Color(color1 / 4, color1 / 2, color1));
			goff.drawString(s1, (d.width - fm.stringWidth(s1)) / 2,
					d.height / 2);
			goff.setColor(new Color(color2 / 4, color2 / 2, color2));
			goff.drawString(s2, (d.width - fm.stringWidth(s2)) / 2,
					d.height / 2);
		} else {
			goff.setColor(new Color(color2 / 4, color2 / 2, color2));
			goff.drawString(s2, (d.width - fm.stringWidth(s2)) / 2,
					d.height / 2);
			goff.setColor(new Color(color1 / 4, color1 / 2, color1));
			goff.drawString(s1, (d.width - fm.stringWidth(s1)) / 2,
					d.height / 2);
		}
		g.drawImage(ii, 0, 0, this);//
		//变换颜色
		color1 += dcolor1;
		color2 += dcolor2;
		if (color1 <= 3 || color1 >= 250)
			dcolor1 = -dcolor1;
		if (color2 <= 3 || color2 >= 250)
			dcolor2 = -dcolor2;
	}

	//run()方法
	public void run() {
		long starttime;
		Graphics g;
		//设置线程优先权
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		g = getGraphics();
		while (true) {
			starttime = System.currentTimeMillis();//获取系统当前时间
			try {
				paint(g);
				starttime += 20;
				//使线程睡眠
				Thread.sleep(Math.max(0, starttime - System.currentTimeMillis()));
			} catch (InterruptedException e) //抛出异常
			{
				break;
			}
		}
	}

	//启动线程
	public void start() {
		if (thethread == null) {
			thethread = new Thread(this);
			thethread.start();
		}
	}

	//停止线程
	public void stop() {
		if (thethread != null) {
			thethread.stop();
			thethread = null;
		}
	}
}
