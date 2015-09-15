package 第七章.实例77;
import java.io.*;
import java.net.*;

public class UDPSend {
    public static final String usage = 
	"Usage: java UDPSend <hostname> <port> <msg>...\n" +
	"   or: java UDPSend <hostname> <port> -f <file>";

    public static void main(String args[]) {
        try { 
            // 检查参数的个数
            if (args.length < 3) 
                throw new IllegalArgumentException("Wrong number of args");
            
            // 解析参数
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            byte[] message;  // 要传送的数据
            if (args[2].equals("-f")) {  // 传送文件
                File f = new File(args[3]);
                int len = (int)f.length();    // 文件长度
                message = new byte[len];      // 建立缓冲区
                FileInputStream in = new FileInputStream(f);
                int bytes_read = 0, n;
                do {   // 从文件中读取
                    n = in.read(message, bytes_read, len-bytes_read);
                    bytes_read += n;
                } while((bytes_read < len)&& (n != -1));
            }
            else { //  发送字符串
                String msg = args[2];  
                for (int i = 3; i < args.length; i++) msg += " " + args[i];
                message = msg.getBytes();
            }
            
            // 根据主机名称得到IP地址
            InetAddress address = InetAddress.getByName(host);
            // 用数据和地址创建数据报文包
            DatagramPacket packet = new DatagramPacket(message, message.length,
						       address, port);
	    
            // 创建数据报文套接字并通过它传送
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            dsocket.close();
        }
        catch (Exception e) {
            System.err.println(e);
	    System.err.println(usage);
        }
    }
}
