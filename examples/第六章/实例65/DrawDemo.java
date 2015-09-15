package 第六章.实例65;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class DrawDemo {
	public static void main(String[] args) {
		DrawFrame frame = new DrawFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}
}
/**
   一个frame中包含一个panel用来画图
 */
class DrawFrame extends JFrame {
	public DrawFrame() {
		setTitle("DrawDemo");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// 将 panel 加到 frame
		DrawPanel panel = new DrawPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;
}

/**
 用来显示矩形和椭圆的panel
 */
class DrawPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// 画矩形
		double leftX = 100;
		double topY = 100;
		double width = 200;
		double height = 150;

		Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
		g2.draw(rect);

		// 画rect的内切椭圆
		Ellipse2D ellipse = new Ellipse2D.Double();
		ellipse.setFrame(rect);
		g2.draw(ellipse);

		// 画一条对角线
		g2.draw(new Line2D.Double(leftX, topY, leftX + width, topY + height));

		// 画一个同心的圆
		double centerX = rect.getCenterX();
		double centerY = rect.getCenterY();
		double radius = 150;

		Ellipse2D circle = new Ellipse2D.Double();
		circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY
				+ radius);
		g2.draw(circle);
	}
}
