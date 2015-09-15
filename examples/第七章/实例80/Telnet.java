package 第七章.实例80;
import java.io.*;
import java.net.*;

public class Telnet {
	public static void main(String [] args) throws IOException {
		if (args.length < 1) {
			System.out.println("用法：Telnet <主机名> <端口号>");
			return;
		}
		Socket sock = new Socket(args[0], 
			(args.length < 2) ? 23 : Integer.parseInt(args[1]));
		// 创建一个写线程
		new TelnetWriter(sock.getOutputStream()).start();
		// 创建一个读线程
		new TelnetReader(sock.getInputStream()).start();
	}
}

class TelnetWriter extends Thread {
	private PrintStream out;

	public TelnetWriter(OutputStream out) {
		this.out = new PrintStream(out);
	}
	public void run() {
		try {
			// 包装控制台输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			// 反复将控制台输入写到Telnet服务程序
			while (true)  out.println(in.readLine());
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
}

class TelnetReader extends Thread {
	private InputStreamReader in;

	public TelnetReader(InputStream in) {
		this.in = new InputStreamReader(in);
	}
	public void run() {
		try {
			// 反复将Telnet服务程序的反馈信息显示在控制台屏幕上
			while (true) {
				// 从Telnet服务程序读取数据
				int b = in.read();
				if (b == -1)  System.exit(0);
				// 将数据显示在控制台屏幕上
				System.out.print((char) b);
			}
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
}
