package 第六章.实例67;
import java.awt.*;
import javax.swing.*;

public class BouncingCircle extends JApplet implements Runnable {
	int x = 150, y = 50, r = 50; // 圆的位置和半径
	int dx = 11, dy = 7; // 圆的轨迹
	Thread animator; // 产生动态效果的线程
	volatile boolean pleaseStop; // 线程结束的标记

	//在当前的位置画圆
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x - r, y - r, r * 2, r * 2);
	}

	/**
	 * 重新设定圆的位置，并执行重画操作
	 * 不断被线程调用
	 **/
	public void animate() {
		// 到达边界时反弹
		Rectangle bounds = getBounds();
		if ((x - r + dx < 0) || (x + r + dx > bounds.width))
			dx = -dx;
		if ((y - r + dy < 0) || (y + r + dy > bounds.height))
			dy = -dy;

		// 重新设定圆的位置
		x += dx;
		y += dy;
		
		repaint();
	}

	// 实现Runnable接口的run方法
	public void run() {
		while (!pleaseStop) { // Loop until we're asked to stop
			animate();   // 重画操作
			try {
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {
			} 
		}
	}

	/** 当applet被加载时启动线程 */
	public void start() {
		animator = new Thread(this); // 创建线程
		pleaseStop = false; 
		animator.start();
	}

	/** 当applet关闭时线程停止 */
	public void stop() {
		pleaseStop = true;
	}
}
