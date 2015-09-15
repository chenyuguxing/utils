package 第九章.实例105;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;
import java.awt.image.*;
import java.awt.*;

public class JPEGServlet extends HttpServlet{
	
	//处理HTTP Get请求
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	                   throws ServletException,IOException{
	    response.setContentType("image/jpeg");
	    ServletOutputStream out= response.getOutputStream();
	    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
	    Graphics g = image.getGraphics();
	    g.setColor(Color.green);
	    g.fillRect(0,0,100,100);
	    g.setColor(Color.red);
	    g.drawOval(0,0,100,100);
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	    encoder.encode(image);
	    out.close();
	}
	
	// 处理HTTP Post请求
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doGet(request,response);
	}
	
	// 得到Servlet信息
	public String getServletInfo() {
		return "JPEGServlet Information";
	}
}
	
	
