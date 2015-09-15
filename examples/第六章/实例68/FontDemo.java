package 第六章.实例68;
 import java.awt.*;
 import java.awt.font.*;
 import java.awt.geom.*;
 import javax.swing.*;

 public class FontDemo
 {
    public static void main(String[] args)
    {
       FontFrame frame = new FontFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.show();
    }
 }

 class FontFrame extends JFrame
 {
    public FontFrame()
    {
       setTitle("FontDemo");
       setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
       FontPanel panel = new FontPanel();
       Container contentPane = getContentPane();
       contentPane.add(panel);
    }

    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;
 }

 class FontPanel extends JPanel
 {
    public void paintComponent(Graphics g)
    {
       super.paintComponent(g);
       Graphics2D g2 = (Graphics2D)g;
       String message = "Hello, World!";
       // 创建字体对象
       Font f = new Font("Serif", Font.BOLD, 36);
       g2.setFont(f);

       // 获得文本字体的规格
       FontRenderContext context = g2.getFontRenderContext();
       Rectangle2D bounds = f.getStringBounds(message, context);

       // 得到文本的左上角坐标(x,y)
       double x = (getWidth() - bounds.getWidth()) / 2;
       double y = (getHeight() - bounds.getHeight()) / 2;

       // 计算下划线的y坐标值
       double ascent = -bounds.getY();
       double baseY = y + ascent;

       // 显示文本信息
       g2.setPaint(Color.YELLOW);
       g2.drawString(message, (int)x, (int)(baseY));
       g2.setPaint(Color.BLUE);

       // 画下划线
       g2.draw(new Line2D.Double(x, baseY,
          x + bounds.getWidth(), baseY));

       // 画包围框
       Rectangle2D rect = new Rectangle2D.Double(x, y,
          bounds.getWidth(),
          bounds.getHeight());
       g2.draw(rect);
    }
 }
