package 第七章.实例78;

import java.net.*;
import java.io.*;

public class MyProxyServer {
	public static void main(String args[]) {
		try {
			ServerSocket ss = new ServerSocket(8080);
			System.out.println("proxy server OK");
			while (true) {
				Socket s = ss.accept();
				Process p = new Process(s);
				Thread t = new Thread(p);
				t.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class Process implements Runnable {
	Socket s;

	public Process(Socket s1) {
		s = s1;
	}

	public void run() {
		String content = " ";
		try {
			// 从Socket中获得输入
			PrintStream out = new PrintStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(s
					.getInputStream()));
			String info = in.readLine();
			// 分析请求字符串，得到URL
			System.out.println("now got " + info);
			int sp1 = info.indexOf(' ');
			int sp2 = info.indexOf(' ', sp1 + 1);
			String gotourl = info.substring(sp1, sp2);
			System.out.println("now connecting " + gotourl);
			URL con = new URL(gotourl);
			InputStream gotoin = con.openStream();
			int n = gotoin.available();
			byte buf[] = new byte[1024];
			out.println("HTTP/1.0 200 OK");
			out.println("MIME_version:1.0");
			out.println("Content_Type:text/html");
			out.println("Content_Length:" + n);
			out.println(" ");
			while ((n = gotoin.read(buf)) >= 0) {
				out.write(buf, 0, n);
			}
			out.close();
			s.close();
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}
}
