package 第九章.实例104;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TestServlet extends HttpServlet {

	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	private int number;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		int n = 1;
		number = Integer.parseInt(request.getParameter("DATA"));

		for (int i = 1; i <= number; i++) {
			n *= i;
		}

		out.println("<h2 align='center'>" + "计算结果" + "</h2>");
		out.println("<html>");
		out.println("<head><title>TestServlet</title></head>");
		out.println("<body>");
		out.println("<P>"+number +"! = " + n + "</P>");
		out.println("</body></html>");
		out.close();
	}

}

