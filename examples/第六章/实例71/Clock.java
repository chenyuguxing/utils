package 第六章.实例71;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Calendar;
import javax.swing.*;

public class Clock extends JPanel implements ActionListener
{
    // 创建时钟的外形
    protected static Ellipse2D face = new Ellipse2D.Float(3, 3, 94, 94);
    // 创建时钟的标记
    protected static GeneralPath tick = new GeneralPath();
    static
    {
        tick.moveTo(100, 100);
        tick.moveTo(49, 0);
        tick.lineTo(51, 0);
        tick.lineTo(51, 6);
        tick.lineTo(49, 6);
        tick.lineTo(49, 0);
    }
    // 创建时针
    protected static GeneralPath hourHand = new GeneralPath();
    static
    {
        hourHand.moveTo(50, 15);
        hourHand.lineTo(53, 50);
        hourHand.lineTo(50, 53);
        hourHand.lineTo(47, 50);
        hourHand.lineTo(50, 15);
    }
    // 创建分针
    protected static GeneralPath minuteHand = new GeneralPath();
    static
    {
        minuteHand.moveTo(50, 2);
        minuteHand.lineTo(53, 50);
        minuteHand.lineTo(50, 58);
        minuteHand.lineTo(47, 50);
        minuteHand.lineTo(50, 2);
    }
	// 创建秒针
    protected static GeneralPath secondHand = new GeneralPath();
    static
    {
        secondHand.moveTo(49, 5);
        secondHand.lineTo(51, 5);
        secondHand.lineTo(51, 62);
        secondHand.lineTo(49, 62);
        secondHand.lineTo(49, 5);
    }
	// 设置时钟的颜色
    protected static Color faceColor = new Color(220, 220, 220);
    protected static Color hourColor = Color.red.darker();
    protected static Color minuteColor = Color.blue.darker();
    protected static Color secondColor = new Color(180, 180, 0);
    protected static Color pinColor = Color.gray.brighter();
    // 创建时钟的枢纽
    protected Ellipse2D pivot = new Ellipse2D.Float(47, 47, 6, 6);
    protected Ellipse2D centerPin = new Ellipse2D.Float(49, 49, 2, 2);
    
    // 创建绕时钟枢纽转的变换
    protected AffineTransform hourTransform =
                    AffineTransform.getRotateInstance(0, 50, 50);
    protected AffineTransform minuteTransform =
                    AffineTransform.getRotateInstance(0, 50, 50);
    protected AffineTransform secondTransform =
                    AffineTransform.getRotateInstance(0, 50, 50);
    // 创建每秒触发一次的Timer
    protected Timer timer = new Timer(1000, this);
    protected Calendar calendar = Calendar.getInstance();
    
    public Clock()
    {
        setPreferredSize(new Dimension(100, 100));
    }
	// 当plane加入container中时发生
    public void addNotify()
    {
        super.addNotify();
        timer.start();
    }
	// 当plane从container中移处时发生
    public void removeNotify()
    {
        timer.stop();
        super.removeNotify();
    }

    public void actionPerformed(ActionEvent event)
    {
        // 更新calender的时间
        this.calendar.setTime(new java.util.Date());
        int hours = this.calendar.get(Calendar.HOUR);
        int minutes = this.calendar.get(Calendar.MINUTE);
        int seconds = this.calendar.get(Calendar.SECOND);
        
        //设置变换，使得时针、分针、秒针各自按绕枢纽旋转一定的角度
        hourTransform.setToRotation(((double) hours) *
                                            (Math.PI / 6.0), 50, 50);
        minuteTransform.setToRotation(((double) minutes) *
                                            (Math.PI / 30.0), 50, 50);
        secondTransform.setToRotation(((double) seconds) *
                                            (Math.PI / 30.0), 50, 50);
        repaint();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        
        // 得到图形上下文和抗锯齿处理
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(faceColor);
        g2.fill(face);
        g2.setPaint(Color.black);
        g2.draw(face);
        
        // 产生钟面上12个滴答位置
        for (double p = 0.0; p < 12.0; p += 1.0)
        {
            //利用变换画出同心的滴答的标线
            g2.fill(tick.createTransformedShape(
                AffineTransform.getRotateInstance((Math.PI / 6.0)  * p,
                        50, 50)));
        }
        g2.setPaint(hourColor);
        g2.fill(hourHand.createTransformedShape(hourTransform));
        g2.setPaint(minuteColor);
        g2.fill(minuteHand.createTransformedShape(minuteTransform));
        g2.setPaint(secondColor);
        g2.fill(secondHand.createTransformedShape(secondTransform));
        g2.fill(pivot);
        g2.setPaint(pinColor);
        g2.fill(centerPin);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setLocation(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Clock());
        frame.pack();
        frame.show();
    }
}
