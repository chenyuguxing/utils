package 第六章.实例69;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.plaf.*;

public class DigitalClock extends JPanel
{
    public DigitalClock()
    {
        // 指定一个UI对象
        this.setUI(DigitalClockPanelUI.createUI(this));
        // 设本panel为不透明
        this.setOpaque(true);
    }

    public static void main(String [] args)
    {
        JFrame frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DigitalClock panel = new DigitalClock();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

// 继承PanelUI类，该类在所有平台上都能使用
class DigitalClockPanelUI extends PanelUI implements ActionListener
{
    protected final static Font clockFont =
                            new Font("Arial", Font.BOLD, 24);
    // 时间显示的格式
    protected final static SimpleDateFormat dateFormat =
                            new SimpleDateFormat("hh:mm:ss a");
    protected final static FontRenderContext frc =
                            new FontRenderContext(null, true, true);
    // 字体之间空格
    protected final static int FUDGE = 6;
    // 含当前时间的AttributedString
    protected AttributedString timeString = null;
    protected TextLayout textLayout = null;
    //每秒一次的timer控件
    protected javax.swing.Timer timer =
                        new javax.swing.Timer(1000, this);
    protected DigitalClock panel;

    private DigitalClockPanelUI(DigitalClock panel)
    {
        this.panel = panel;
        panel.setBackground(Color.black);
        actionPerformed(null);
        timer.start();
    }
    
    public static ComponentUI createUI(JComponent component)
    {
        return new DigitalClockPanelUI((DigitalClock) component);
    }
 
    public void paint(Graphics g, JComponent c)
    {
        // 没有时间或是没有textLayout时，不进行重画
        if (this.timeString == null || this.textLayout == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        // 在textLayout指示的位置上显示字符串
        g2.drawString(timeString.getIterator(), 1,
                    (int) (textLayout.getAscent()));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_DEFAULT);
    }
    
    // 当间隔时间到时，timer会自动调用此方法
    public void actionPerformed(ActionEvent event)
    {
        // 为新的时间字符串创建AttributedString
        timeString = new AttributedString(dateFormat.format(new Date()));
        // 设定字体和颜色
        timeString.addAttribute(TextAttribute.FONT, clockFont);
        timeString.addAttribute(TextAttribute.FOREGROUND, Color.red);
        // 将中间的时间分隔符设定为黄色
        timeString.addAttribute(TextAttribute.FOREGROUND,
                                Color.yellow, 2, 3);
        timeString.addAttribute(TextAttribute.FOREGROUND,
                                Color.yellow, 5, 6);
        // 创建新的TextLayout
        textLayout = new TextLayout(timeString.getIterator(), frc);
        panel.repaint();
    }
    // 返回组件的合适大小
    public Dimension getPreferredSize(JComponent c)
    {
        Dimension size = textLayout.getBounds().getBounds().getSize();
        // 适当调大高度和宽度
        size.height += FUDGE;
        size.width += (FUDGE + 2);
        return size;
    }
}
