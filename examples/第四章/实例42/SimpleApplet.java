package 第四章.实例42;
import java.awt.*;
import javax.swing.*;
public class SimpleApplet extends JApplet{
	private String text = "这是一个简单的小应用程序......";
	private int count = 0;
	public void init() {
		System.out.println(count + ": init() ...");  count++;
		setBackground(Color.cyan);
	}
	public void start() {
		System.out.println(count + ": start() ...");  count++;
	}
	public void stop() {
		System.out.println(count + ": stop() ...");  count++;
	}
	public void destroy() {
		System.out.println(count + ": destroy() ...");  count++;
	}
	public void paint(Graphics g) {
		System.out.println(count + ": painting() ...");  count++;
		g.setColor(Color.blue);
		g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
		g.setColor(Color.red);
		g.drawString(text, 15, 25);
	}
}
