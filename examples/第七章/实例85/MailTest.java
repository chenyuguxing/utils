package 第七章.实例85;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class MailTest {
	public static void main(String[] args) {
		JFrame frame = new MailTestFrame();
		frame.show();
	}
}

class MailTestFrame extends JFrame implements ActionListener {
	private BufferedReader in;
	private PrintWriter out;
	private JTextField from;
	private JTextField to;
	private JTextField smtpServer;
	private JTextArea message;
	private JTextArea response;
	
	public MailTestFrame() {
		setTitle("MailTest");
		setSize(300, 300);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// 当容器比组件要大时，只调整垂直方向组件的大小
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.weighty = 0;
		
		gbc.weightx = 0;
		add(new JLabel("发送地址:"), gbc, 0, 0, 1, 1);
		gbc.weightx = 100;
		from = new JTextField(20);
		add(from, gbc, 1, 0, 1, 1);

		gbc.weightx = 0;
		add(new JLabel("收件地址:"), gbc, 0, 1, 1, 1);
		gbc.weightx = 100;
		to = new JTextField(20);
		add(to, gbc, 1, 1, 1, 1);

		gbc.weightx = 0;
		add(new JLabel("SMTP服务器:"), gbc, 0, 2, 1, 1);
		gbc.weightx = 100;
		smtpServer = new JTextField(20);
		add(smtpServer, gbc, 1, 2, 1, 1);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 100;
		message = new JTextArea();
		add(new JScrollPane(message), gbc, 0, 3, 2, 1);

		response = new JTextArea();
		add(new JScrollPane(response), gbc, 0, 4, 2, 1);

		gbc.weighty = 0;
		JButton sendButton = new JButton("发送");
		sendButton.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(sendButton);
		add(buttonPanel, gbc, 0, 5, 2, 1);
	}

	private void add(Component c, GridBagConstraints gbc, int x, int y, int w,
			int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		getContentPane().add(c, gbc);
	}

	public void actionPerformed(ActionEvent evt) {
		// 异步机制，使得当所有的awt事件发生后才进行以下工作
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sendMail();
			}
		});
	}

	public void sendMail() {
		try {
			Socket s = new Socket(smtpServer.getText(), 25);
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String hostName = InetAddress.getLocalHost().getHostName();

			send(null);
			// 传送本机域名
			send("HELO " + hostName);
			// 传送发信者信箱名称
			send("MAIL FROM: " + from.getText());
			// 传送收信者信箱名称
			send("RCPT TO: " + to.getText());
			// 发送信箱数据，包括信头和信体
			send("DATA");
			out.println(message.getText());
			// 发送结束标志
			send(".");
			s.close();
		} catch (IOException exception) {
			response.append("Error: " + exception);
		}
	}

	public void send(String s) throws IOException {
		if (s != null) {
			response.append(s + "\n");
			out.println(s);
			out.flush();
		}
		String line;
		if ((line = in.readLine()) != null)
			response.append(line + "\n");
	}
}

