package 第七章.实例83;
// 服务器端  UploadServer.java
import java.net.*;
import java.io.*;
import java.util.*;

public class UploadServer {
	private static Socket clientSocket;
	private static int counter;

	public static void main(String[] args) {
		int i = 1;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(8765);
			while (true) {
                //	监听与本 socket 的连接并且接受它
				clientSocket = serverSocket.accept();
				// 创建线程，将上传数据写至服务器的文件中
				Thread t = new MyThread(clientSocket, i);
				t.start();
				i++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
