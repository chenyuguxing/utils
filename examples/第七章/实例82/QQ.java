package 第七章.实例82;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import javax.swing.JDialog;

public class QQ extends Frame implements ActionListener {
	Label label1 = new Label("请输入您要发送的信息（限英文）：");
	Label label2 = new Label("以下是你收到的消息记录：");
	Label label3 = new Label("把以上消息发给如下IP地址：");
	TextArea input = new TextArea("", 7, 14, TextArea.SCROLLBARS_BOTH);
	TextArea output = new TextArea("", 8, 14, TextArea.SCROLLBARS_BOTH);
	TextField IPAdd = new TextField("192.168.1.88");
	Button send = new Button("发送消息");
	Button about = new Button("关于");
	Button clear = new Button("清空消息纪录");
	GregorianCalendar time = new GregorianCalendar();

	QQ() {
		super("仿QQ聊天工具");
		setLayout(null);
		setLocation(250, 250);
		this.setSize(518, 218);
		this.setResizable(false); // 大小不可变
		this.setBackground(new Color(220, 220, 220));
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image myImage = kit.getImage("icons\\QQ.bmp");
		this.setIconImage(myImage);

		label1.setFont(new Font("宋体", Font.PLAIN, 12));
		label1.setForeground(new Color(0, 0, 192));
		label1.setBounds(8, 28, 216, 16);

		input.setBackground(new Color(255, 255, 128));
		input.setFont(new Font("Times New Roman", Font.BOLD, 15));
		input.setForeground(Color.magenta);
		input.setBounds(8, 44, 248, 120);

		output.setBackground(new Color(128, 255, 255));
		output.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		output.setForeground(Color.magenta);
		output.setBounds(264, 44, 248, 136);
		output.setEditable(false);

		send.setFont(new Font("新宋体", Font.PLAIN, 12));
		send.setLocation(136, 188);
		send.setSize(120, 22);

		clear.setFont(new Font("新宋体", Font.PLAIN, 12));
		clear.setLocation(392, 188);
		clear.setSize(120, 22);

		label2.setFont(new Font("宋体", Font.PLAIN, 12));
		label2.setForeground(new Color(0, 0, 192));
		label2.setBounds(264, 28, 216, 16);

		about.setFont(new Font("新宋体", Font.PLAIN, 12));
		about.setLocation(264, 188);
		about.setSize(120, 22);

		label3.setFont(new Font("宋体", Font.PLAIN, 12));
		label3.setForeground(new Color(0, 0, 192));
		label3.setBounds(8, 172, 160, 16);

		IPAdd.setFont(new Font("新宋体", Font.PLAIN, 12));
		IPAdd.setLocation(8, 190);
		IPAdd.setSize(120, 19);

		add(label1);
		add(input);
		add(label3);
		add(label2);
		add(output);
		add(IPAdd);
		add(send);
		add(about);
		add(clear);
		addWindowListener(new closeWin());
		send.addActionListener(this);
		about.addActionListener(this);
		clear.addActionListener(this);

		show();
		waitForData();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == send)
			sendData();
		else if (e.getSource() == clear)
			output.setText("");
		else if (e.getSource() == about) {
			AboutQQ test = new AboutQQ(this);
		}
	}

	public static void main(String args[]) {
		new QQ();
	}

	void sendData() {
		try {
			String msg = input.getText();
			if (msg.equals(""))
				return;
			input.setText("");
			String ad = IPAdd.getText();
			InetAddress tea = InetAddress.getLocalHost();
			String asd = tea.getHostAddress();//发送方的IP地址
			output.append("[" + asd + "]:(" + time.get(GregorianCalendar.YEAR)
					+ "-" + time.get(GregorianCalendar.MONTH) + "-"
					+ time.get(GregorianCalendar.DATE) + " "
					+ time.get(GregorianCalendar.HOUR) + ":"
					+ time.get(GregorianCalendar.MINUTE) + ":"
					+ time.get(GregorianCalendar.SECOND) + ") " + "\n" + msg
					+ "\n");
			msg = "From [" + asd + "]:(" + time.get(GregorianCalendar.YEAR)
					+ "-" + time.get(GregorianCalendar.MONTH) + "-"
					+ time.get(GregorianCalendar.DATE) + " "
					+ time.get(GregorianCalendar.HOUR) + ":"
					+ time.get(GregorianCalendar.MINUTE) + ":"
					+ time.get(GregorianCalendar.SECOND) + ") \n" + msg;
			InetAddress address = InetAddress.getByName(ad);
			int len = msg.length();
			byte[] message = new byte[len];
			msg.getBytes(0, len, message, 0);
			DatagramPacket packet = new DatagramPacket(message, len, address,
					9999);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
		} catch (Exception e) {
		}
	}

	void waitForData() {
		try {
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			DatagramSocket socket = new DatagramSocket(9999);
			while (true) {
				socket.receive(packet);
				String s = new String(buffer, 0, 0, packet.getLength());
				output.append(s + "\n");
				packet = new DatagramPacket(buffer, buffer.length);
			}
		} catch (Exception e) {
		}
	}
}

class closeWin extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		Frame fr = (Frame) (e.getSource());
		fr.dispose();
		System.exit(0);
	}
}

class AboutQQ {
	private Label label;
	private JDialog dialog;
	
	public AboutQQ(Frame f){
		label = new Label("Version 1.0");
		dialog = new JDialog(f, "About", true);
		dialog.setLocation(f.getLocation());
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		dialogPane.add(label);
		dialogPane.setBounds(50,50,50,50);
		dialog.pack();
		dialog.show();
	}
}
