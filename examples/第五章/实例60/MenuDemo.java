package 第五章.实例60;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuDemo extends JApplet {
	JTextField t = new JTextField(15);  //建立单行文本域
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			t.setText(((JMenuItem)e.getSource()).getText());
		}
	};
	JMenu[] menus = { new JMenu("文件"),
			          new JMenu("编辑"),
					  new JMenu("帮助")};
	JMenuItem[] items = { 
		new JMenuItem("新建"), new JMenuItem("撤销"),
		new JMenuItem("帮助主题"), new JMenuItem("保存"),
		new JMenuItem("剪切"),new JMenuItem("查找"),
		new JMenuItem("退出"),new JMenuItem("复制"),
		new JMenuItem("关于") };
	JPopupMenu popup = new JPopupMenu(); // 定义一个弹出式菜单
	
	public void init(){
		for(int i = 0; i < items.length; i++){
            //为每一个菜单项增加监听器
			items[i].addActionListener(al);  
			// 将每个菜单项添加到各自的菜单中
			menus[i%3].add(items[i]);
		}
	    JMenuBar mb = new JMenuBar();
	    for(int i = 0; i < menus.length; i++){
	    	//将每个菜单添加到菜单控制栏中
		    mb.add(menus[i]);
		    setJMenuBar(mb);
	    }
	    Container cp = getContentPane();
	    // 设定内容窗格的布局控制为FlowLayout格式
	    cp.setLayout(new FlowLayout());
	    cp.add(t);
	    // 为弹出式菜单添加菜单项，和监视器
	    JMenuItem m = new JMenuItem("最小化");
	    popup.add(m);
	    m.addActionListener(al);
	    popup.addSeparator(); // 在菜单的末尾添加分隔空隙
	    m = new JMenuItem("最大化");
	    popup.add(m);
	    m.addActionListener(al);
	    PopupListener pl = new PopupListener();
	    addMouseListener(pl);  // 将弹出菜单事件添加到鼠标事件处理模式中
	}
	
	class PopupListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			maybeShowPopup(e);
		}
		
		public void mouseReleased (MouseEvent e){
			maybeShowPopup(e);
		}
		
		private void maybeShowPopup(MouseEvent e){
			if(e.isPopupTrigger()){ 
				// 如果鼠标右键是弹出式菜单则在当前鼠标位置显示该菜单
				popup.show(e.getComponent(),e.getX(),e.getY());
			}
		}
	}
	
	public static void main(String args[]) {
		JApplet applet = new MenuDemo();
		// 创建一个Frame，将applet添加到frame中
		JFrame frame = new JFrame("Menu Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(applet);
		applet.init();
		applet.start();
		frame.pack();
		frame.setVisible(true);
	}
}
