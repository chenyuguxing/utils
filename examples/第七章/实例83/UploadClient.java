package 第七章.实例83;
//客户端 UploadClient.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class UploadClient extends JFrame implements ActionListener {
	String ReadT2 = "";

	private JTextField[] T = new JTextField[4];
	private JButton ExitJB, OKJB, JOpen1, JOpen2, JOpen3;
	private JFileChooser choose = new JFileChooser();
	private File[] Myfile = new File[3];

	public UploadClient() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		T[0] = new JTextField(35);
		T[1] = new JTextField(35);
		T[2] = new JTextField(35);
		T[3] = new JTextField(45);
		ExitJB = new JButton("退出");
		OKJB = new JButton("上传");
		JOpen1 = new JButton("浏览...");
		JOpen2 = new JButton("浏览...");
		JOpen3 = new JButton("浏览...");
		ExitJB.addActionListener(this);
		OKJB.addActionListener(this);
		JOpen1.addActionListener(this);
		JOpen2.addActionListener(this);
		JOpen3.addActionListener(this);
		c.add(T[0]);
		c.add(JOpen1);
		c.add(T[1]);
		c.add(JOpen2);
		c.add(T[2]);
		c.add(JOpen3);
		c.add(OKJB);
		c.add(ExitJB);
		c.add(T[3]);
		T[0].setText("");
		T[1].setText("");
		T[2].setText("");
		T[3].setText("");
		setSize(500, 500);
		show();
	}

	public void actionPerformed(ActionEvent e) {
		ReadT2 = "";
		if (e.getSource() == ExitJB) {
			System.exit(0);
		}
		try {
			if (e.getSource() == OKJB) {
				for (int i = 0; i < 3; i++) {
					if (T[i].getText() != "") {
						this.client(i);
					}
				}
			}
		} catch (Exception t) {
			System.out.println(t.toString());
		}

		if (e.getSource() == JOpen1) {
			this.openf(0);
		}
		if (e.getSource() == JOpen2) {
			this.openf(1);
		}
		if (e.getSource() == JOpen3) {
			this.openf(2);
		}
	}

	public void openf(int s) {

		int result = choose.showOpenDialog(null);
		Myfile[s] = choose.getSelectedFile();
		if (result == JFileChooser.APPROVE_OPTION) {
			T[s].setText(Myfile[s].getPath());
		} else if (result == JFileChooser.CANCEL_OPTION) {
			T[s].setText("");
			Myfile[s] = null;
		}
	}

	public void client(int s) {
		byte[] ff;
		int size;
		Socket clientSocket;
		PrintStream ps;
		String fileName;

		try {
			//  创建一个上传的Socket
			clientSocket = new Socket("192.168.0.1", 8765);
			ps = new PrintStream(clientSocket.getOutputStream());
			size = (int) Myfile[s].length();
			fileName = Myfile[s].getName();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			String over = "\n";
			byte pp[] = over.getBytes();
			byte byname[] = (fileName + over).getBytes();
			System.out.println(new String(byname));
			//文件名
			ps.write(byname);
			ps.flush();
			String gh = br.readLine();
			String tmp;
			tmp = String.valueOf(size) + over;
			byte bySize[] = tmp.getBytes();
			//文件大小
			ps.write(bySize);
			ps.flush();
			String jj = br.readLine();
			ReadT2 += "已上传的文件为" + gh.trim() + ";大小为" + jj.trim() + " ";
			T[3].setText(ReadT2);

			//文件内容
			FileInputStream in = new FileInputStream(Myfile[s]);
			BufferedInputStream bf = new BufferedInputStream(in);
			ff = new byte[1024];
			int c;
			while ((c = bf.read(ff)) != -1) {
				ps.write(ff, 0, c);
			}

			ps.close();
			bf.close();
			in.close();
			clientSocket.close();
			T[s].setText("上传结束，请选择下个文件！");
			T[s].setText("");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String args[]) {
		UploadClient app = new UploadClient();
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
