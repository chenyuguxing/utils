package 第十章.实例110;
package webservice;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.*;

public class WebAppConfig {

	public static void main(String[] args)
		throws IOException, SAXException, ParserConfigurationException {
		WebAppConfig config = new WebAppConfig(new File("web.xml"));
	
		// 添加servlet name、class mapping到 DOM tree
		config.addServlet("HelloServlet", "Hello");
		// 输出DOM tree的XML版本到标准输出口
		config.output(new PrintWriter(new OutputStreamWriter(System.out)));
	}

	org.w3c.dom.Document document; 

	// 用JAXP API来分析XML文件
	public WebAppConfig(File configfile)
		throws IOException, SAXException, ParserConfigurationException {
		javax.xml.parsers.DocumentBuilderFactory dbf =
			DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		javax.xml.parsers.DocumentBuilder parser = dbf.newDocumentBuilder();

		// 告诉分析器如何处理错误
		parser.setErrorHandler(new org.xml.sax.ErrorHandler() {
			public void warning(SAXParseException e) {
				System.err.println("WARNING: " + e.getMessage());
			}
			public void error(SAXParseException e) {
				System.err.println("ERROR: " + e.getMessage());
			}
			public void fatalError(SAXParseException e) throws SAXException {
				System.err.println("FATAL: " + e.getMessage());
				throw e; // 重掷异常
			}
		});
		document = parser.parse(configfile);
	}

	//在DOM tree中寻找指定的元素节点，以找到servlet名字相应的class名字
	public String getServletClass(String servletName) {
		// 从所有<servlet>元素中寻找
		NodeList servletnodes = document.getElementsByTagName("servlet");
		int numservlets = servletnodes.getLength();
		for (int i = 0; i < numservlets; i++) {
			Element servletTag = (Element) servletnodes.item(i);
			// 获得在<servlet>标记中的<servlet-name>标记
			Element nameTag =
				(Element) servletTag.getElementsByTagName("servlet-name").item(0);
			if (nameTag == null)
				continue;
			// 获得<servlet-name>标记中的text
			String name = ((Text) nameTag.getFirstChild()).getData().trim();

			// 如果<servlet-name> 标记中的text与要找的servlet相符
			if (servletName.equals(name)) {
				Element classTag =
					(Element) servletTag.getElementsByTagName(
						"servlet-class").item(
						0);
				if (classTag != null) {
					Text classTagContent = (Text) classTag.getFirstChild();
					return classTagContent.getNodeValue().trim();
				}
			}
		}
		return null;
	}

	// 添加一个新的name-to-class mapping到<servlet>标记中
	public void addServlet(String servletName, String className) {
		// 建立一个<servlet>标签
		Element newNode = document.createElement("servlet");
		// 建立一个<servlet-name> 和 <servlet-class> 标签
		Element nameNode = document.createElement("servlet-name");
		Element classNode = document.createElement("servlet-class");
		// 添加servlet名和class名到标签
		nameNode.appendChild(document.createTextNode(servletName));
		classNode.appendChild(document.createTextNode(className));
		// 将这些标签添加到servlet标记
		newNode.appendChild(nameNode);
		newNode.appendChild(classNode);

		// 建立了新子树后添加到原来的<servlet>标记后
		NodeList servletnodes = document.getElementsByTagName("servlet");
		Element firstServlet = (Element) servletnodes.item(0);
		firstServlet.getParentNode().insertBefore(newNode, firstServlet);
	}

	//输出DOM Tree
	public void output(PrintWriter out) {
		XMLDocumentWriter docwriter = new XMLDocumentWriter(out);
		docwriter.write(document);
		docwriter.close();
	}
}
