package 第十二章.实例126;
package security;

import java.io.*;
import javax.net.ssl.*;
import java.net.*;

public class SSLClient{
	public static void main(String args[]) throws Exception{
		System.setProperty("javax.net.ssl.trustStore", "clienttrust");
		SSLSocketFactory ssf = (SSLSocketFactory)SSLSocketFactory.getDefault();
		Socket s = ssf.createSocket("127.0.0.1", 5432);
		BufferedReader in = new BufferedReader(
		    new InputStreamReader(s.getInputStream()));
	    String x = in.readLine();
	    System.out.println(x);
	    in.close();
	}
}
