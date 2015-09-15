package 第五章.实例51;
import javax.swing.*;    
import java.awt.*;
import java.awt.event.*;

public class ButtonDemo {
    private static String labelPrefix = "Number of button clicks: ";
    private int numClicks = 0;

    public Component createComponents() {
        final JLabel label = new JLabel(labelPrefix + "0    ");
        JButton button = new JButton("I'm a Swing button!");
        button.setMnemonic(KeyEvent.VK_I); //设置快捷键为Alt+i
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numClicks++;
                label.setText(labelPrefix + numClicks);
            }
        });
        label.setLabelFor(button); //为即将获得焦点的组件添加描述性信息
        /* 在顶层容器和它的组件之间设置空白边框 */
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(
                                        30, //上边
                                        30, //左边
                                        10, //下边
                                        30) //右边
                                        );
        pane.setLayout(new GridLayout(0, 1));
        pane.add(button);
        pane.add(label);
        return pane;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        //创建顶层容器并加入中间容器
        JFrame frame = new JFrame("SwingApplication");
        ButtonDemo app = new ButtonDemo();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        //完成JFrame的创建并显示
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}
