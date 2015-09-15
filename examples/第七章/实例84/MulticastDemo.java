package 第七章.实例84;
//package sendandrecievedata;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class MulticastDemo {
	private boolean packFrame = false;

	//	 构建应用程序
	public MulticastDemo() {
		MulticastFrame frame = new MulticastFrame();
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}
		// 把窗口置于中心
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
	//	 Main 方法
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel
			    (UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MulticastDemo();
	}
}

class MulticastFrame extends JFrame {
	private JPanel contentPane;
	private TextField textField1 = new TextField();
	private Button button1 = new Button();
	private Label label1 = new Label();
	private Label label2 = new Label();
	int port; // 声明组播使用的端口
	MulticastSocket socket; // 声明建立组播组使用的MulticastSocket类
	InetAddress group; // 声明建立组播组使用的组播组地址
	DatagramPacket packet; // 声明发送和接收数据所使用的DatagramPacket类

	//构造窗体
	public MulticastFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 控件初始化
	private void init() throws Exception {
		contentPane = (JPanel) this.getContentPane(); //设计窗体布局
		textField1.setBounds(new Rectangle(88, 127, 240, 32));
		contentPane.setLayout(null);
		this.setSize(new Dimension(400, 300));
		this.setTitle("组播组中发送和接收数据");
		button1.setLabel("发送数据");
		button1.setBounds(new Rectangle(166, 186, 88, 29));
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1_actionPerformed(e);
			}
		});
		label1.setBounds(new Rectangle(88, 84, 240, 32));
		label2.setBounds(new Rectangle(88, 41, 220, 32));
		contentPane.add(textField1, null);
		contentPane.add(button1, null);
		contentPane.add(label1, null);
		contentPane.add(label2, null);
		createMulticastGroupAndJoin();
	}

	// 关闭窗体时退出
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	public void createMulticastGroupAndJoin() // 创建一个组播组并加入此组的函数
	{
		try {
			port = 5000; // 设置组播组的监听端口为5000
			group = InetAddress.getByName("239.0.0.0"); // 设置组播组的地址为239.0.0.0
			socket = new MulticastSocket(port); // 初始化MulticastSocket类并将端口号与之关联
			socket.setTimeToLive(1); // 设置组播数据报的发送范围为本地网络
			socket.setSoTimeout(10000); // 设置套接字的接收数据报的最长时间
			socket.joinGroup(group); // 加入此组播组
			label2.setText("已加入地址为239.0.0.0的组播组");
		} catch (Exception e1) {
			System.out.println("Error: " + e1); // 捕捉异常情况
		}
	}

	public void sendData() // 向组播组发送数据的函数
	{
		try {
			byte[] data = textField1.getText().getBytes(); // 将用户要发送的数据转换为字节形式并
			// 存储在数组中
			packet = new DatagramPacket(data, data.length, group, port); // 初始化DatagramPacket
			socket.send(packet); // 通过MulticastSocket实例端口向组播组发送数据
		} catch (Exception e1) {
			System.out.println("Error: " + e1); // 捕捉异常情况
		}
	}

	public String recieveData() // 从组播组接收数据的函数
	{
		String message;
		try {
			packet.setData(new byte[512]); // 设定接收数据的DatagramPacket实例的数组大小
			packet.setLength(512); // 设定接收数据的DatagramPacket实例的长度
			socket.receive(packet); // 通过MulticastSocket实例端口从组播组接收数据
			// 将接受的数据转换成字符串形式
			message = new String(packet.getData());
		} catch (Exception e1) {
			System.out.println("Error: " + e1); // 捕捉异常情况
			message = "Error: " + e1;
		}
		return message;
	}

	// 按钮1事件处理
	void button1_actionPerformed(ActionEvent e) {
		sendData(); // 首先向组播组发送数据
		String message = recieveData(); // 然后从组播组接受此数据
		label1.setText("Data recieved: '" + message + "'"); // 本机显示接收到的数据
	}
}
