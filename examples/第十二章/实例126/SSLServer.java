package 第十二章.实例126;
package security;

import java.net.*;
import javax.net.ssl.*;
import java.io.*;

public class SSLServer{
	public static void main(String args[]) throws Exception{
		System.setProperty("javax.net.ssl.keyStore","mykeystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		SSLServerSocketFactory ssf = (SSLServerSocketFactory)
		    SSLServerSocketFactory.getDefault();
		ServerSocket ss = ssf.createServerSocket(5432);
		System.out.println("Waiting for connection...");
		while(true){
			Socket s = ss.accept();
			PrintStream out = new PrintStream(s.getOutputStream());
			out.println("Hello");
			out.close();
			s.close();
		}
	}
}
