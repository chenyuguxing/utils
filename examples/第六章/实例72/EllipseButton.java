package 第六章.实例72;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;

public class EllipseButton extends JButton
{
    public EllipseButton(String text)
    {
        super(text);
        // 创建自己的UI
        this.setUI(EllipseButtonUI.createUI(this));
        // 取消按钮的边框
        this.setBorder(null);
        // 不用内容窗格的背景
        this.setContentAreaFilled(false);
        // 设定边缘
        this.setMargin(new Insets(8, 14, 8, 14));
    }

    public static void main(String [] args)
    {
        // 在UIManager中放置按钮字体
        UIManager.put("Button.font", new Font("Arial", 0, 20));
        JFrame frame = new JFrame();
        frame.setLocation(100, 100);
        frame.setSize(200, 80);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Elliptical Button");
        frame.getContentPane().setLayout(new GridBagLayout());
        EllipseButton button = new EllipseButton("Elliptical");
        frame.getContentPane().add(button);
        frame.getContentPane().setBackground(new Color(230, 230, 150));
        frame.setVisible(true);
    }
}

// 从BasicButtonUI中继承
class EllipseButtonUI extends BasicButtonUI
{
    protected static EllipseButtonUI singleton =
                        new EllipseButtonUI();
    //创建Stroke用于画按钮
    protected static Stroke thickStroke = new BasicStroke(2.0f);
   
    public static ComponentUI createUI(JComponent c)
    {
        return singleton;
    }
  
    public void paint(Graphics g, JComponent c)
    {
        // 获得Graphics2D的对象，并开启抗锯齿处理
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        // 得到按钮的大小
        AbstractButton b = (AbstractButton) c;
        Rectangle viewRect = new Rectangle();
        viewRect.x = 0;
        viewRect.y = 0;
        viewRect.width = b.getWidth() - 1;
        viewRect.height = b.getHeight() - 1;
        // 缩小矩形以显示抗锯齿效果
        viewRect.grow(-2, -2);
        // 在按钮矩形内创建椭圆
        Ellipse2D ellipse = new Ellipse2D.Float();
        ellipse.setFrame(viewRect.getX(), viewRect.getY(),
                    viewRect.getWidth(), viewRect.getHeight());
        // 判断按钮有无被按下
        ButtonModel model = b.getModel();
        boolean pressed = (model.isArmed() && model.isPressed()) ||
                    model.isSelected();
        // 根据按钮按下与否设置画笔颜色
        if (pressed)
        {
            Color background = UIManager.getColor("Button.select");
            g2.setPaint(background == null ? Color.gray : background);
        }
        else
            g2.setPaint(UIManager.getColor("control"));
        // 填充椭圆按钮
        g2.fill(ellipse);
        // 根据椭圆按钮的大小来设定边框大小.
        Arc2D arc = new Arc2D.Float();
        arc.setFrame(viewRect.getX(), viewRect.getY(),
                    viewRect.getWidth(), viewRect.getHeight());
        arc.setArcType(Arc2D.OPEN);
       // 设定边框指定弧度的区域
        arc.setAngles(viewRect.getWidth(), 0, 0, viewRect.getHeight());
        g2.setStroke(thickStroke);
        // 根据按钮按下与否设定画笔的颜色
        g2.setPaint(pressed ?
                UIManager.getColor("controlDkShadow") :
                UIManager.getColor("controlHighlight"));
        g2.draw(arc);
        
        arc.setAngles(0, viewRect.getHeight(), viewRect.getWidth(), 0);
        g2.setPaint(pressed ?
                UIManager.getColor("controlHighlight") :
                UIManager.getColor("controlShadow"));
        g2.draw(arc);
        super.paint(g, c);
        // 将图形上下文恢复原来的抗锯齿属性的设置
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public Dimension getPreferredSize(JComponent c)
    {
        AbstractButton b = (AbstractButton) c;
        Dimension dim = super.getPreferredSize(c);
        // 调整高度和宽度，为了更好的显示效果
        dim.height +=  (b.getMargin().top + b.getMargin().bottom);
        dim.width += (b.getMargin().left + b.getMargin().right);
        return dim;
    }
}
