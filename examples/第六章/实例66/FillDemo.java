package 第六章.实例66;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class FillDemo {
	public static void main(String[] args) {
		FillFrame frame = new FillFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}
}

/**
 包含一个用于panel的frame
 */
class FillFrame extends JFrame {
	public FillFrame() {
		setTitle("FillTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// 将panel加到frame中
		FillPanel panel = new FillPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;
}

/**
 用于显示填充矩形和椭圆的panel
 */
class FillPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// 画矩形
		double leftX = 100;
		double topY = 100;
		double width = 200;
		double height = 150;

		Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
		g2.setPaint(Color.RED);
		g2.fill(rect);

		// 画椭圆
		Ellipse2D ellipse = new Ellipse2D.Double();
		ellipse.setFrame(rect);
		g2.setPaint(Color.WHITE);
		g2.fill(ellipse);
	}
}
