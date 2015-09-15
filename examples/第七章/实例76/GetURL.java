package 第七章.实例76;
import java.io.*;
import java.net.*;

public class GetURL {
    public static void main(String[] args) {
        InputStream in = null;   
        OutputStream out = null;
        try {
            // 检查命令行参数
            if ((args.length != 1)&& (args.length != 2)) 
                throw new IllegalArgumentException("Wrong number of args");
	    
            // 建立输入输出流
            URL url = new URL(args[0]); 
            in = url.openStream();        
            if (args.length == 2) 
                out = new FileOutputStream(args[1]);
            else out = System.out;
	    
            // 从 URL 拷贝到输出流
            byte[] buffer = new byte[4096];
            int bytes_read;
            while((bytes_read = in.read(buffer)) != -1)
                out.write(buffer, 0, bytes_read);
	}
        // 捕获异常，输出报错信息
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java GetURL <URL> [<filename>]");
        }
        finally {  // 保证关闭输入流和输出流
            try { in.close();  out.close(); } catch (Exception e) {}
        }
    }
}
