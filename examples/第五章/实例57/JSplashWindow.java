package 第五章.实例57;
import javax.swing.*;
import java.awt.*;
import java.net.*;

public class JSplashWindow extends JWindow implements Runnable {
	Thread splashThread = null;
	
	public JSplashWindow() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		JPanel splash = new JPanel(new BorderLayout());
		// 获得图片资源的绝对路径
		URL url = getClass().getResource("/images/tu.gif");  
		if (url != null) { //图片嵌入JLabel中显示
			splash.add(new JLabel(new ImageIcon(url)), BorderLayout.CENTER);
		}
		setContentPane(splash);  // 给启动窗口设置内容窗格
		Dimension screen = getToolkit().getScreenSize(); //获得屏幕的大小
		pack(); 
		//设定启动窗口在屏幕中的位置
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2); 
	}

	public void start() {
		this.toFront();  // 将启动窗口放在当前位置
		splashThread = new Thread(this);
		splashThread.start();
	}

	public void run() {
		try {
			show();
			Thread.sleep(5000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.dispose();
	}

	static void showFrame(String title) {
		JFrame frame = new JFrame(title);
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 将画窗置于屏幕中央
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		showFrame("Demo splash window"); //显示画窗
		JSplashWindow splash = new JSplashWindow();
		splash.start(); // 显示启动窗口,并维持一定时间
	}
}
