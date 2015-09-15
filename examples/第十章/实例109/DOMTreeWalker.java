package 第十章.实例109;
package webservice;

import org.w3c.dom.*;
import org.w3c.dom.traversal.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.io.*;

public class DOMTreeWalker implements TreeModel {
	TreeWalker walker;

	public DOMTreeWalker(TreeWalker walker) {
		this.walker = walker;
	}

	public DOMTreeWalker(Document document) {
		DocumentTraversal dt = (DocumentTraversal) document;
		walker =
			dt.createTreeWalker(document, NodeFilter.SHOW_ALL, null, false);
	}

	public DOMTreeWalker(Element element) {
		DocumentTraversal dt = (DocumentTraversal) element.getOwnerDocument();
		walker = dt.createTreeWalker(element, NodeFilter.SHOW_ALL, null, false);
	}

	// 返回树的根 
	public Object getRoot() {
		return walker.getRoot();
	}

	// 判断是否为叶子
	public boolean isLeaf(Object node) {
		walker.setCurrentNode((Node) node);  
		Node child = walker.firstChild(); 
		return (child == null); 
	}

	// 计算该节点的子节点数目
	public int getChildCount(Object node) {
		walker.setCurrentNode((Node) node); 
		int numkids = 0;
		Node child = walker.firstChild(); 
		while (child != null) { 
			numkids++;
			child = walker.nextSibling(); // 下一个孩子
		}
		return numkids; 
	}

	// 返回节点的指定子节点
	public Object getChild(Object parent, int index) {
		walker.setCurrentNode((Node) parent);
		Node child = walker.firstChild();
		while (index-- > 0)
			child = walker.nextSibling();
		return child;
	}

	// 返回子节点在父节点当中的位置
	public int getIndexOfChild(Object parent, Object child) {
		walker.setCurrentNode((Node) parent);
		int index = 0;
		Node c = walker.firstChild();
		while ((c != child) && (c != null)) { 
			index++;
			c = walker.nextSibling();
		}
		return index; 
	}

	// 供可修改的树使用的方法，这里没有实现
	public void valueForPathChanged(TreePath path, Object newvalue) {
	}

	// 因为这里实现的DOM Tree是不可修改的，所以它不触发任何事件
	public void addTreeModelListener(TreeModelListener l) {
	}
	public void removeTreeModelListener(TreeModelListener l) {
	}

	public static void main(String[] args) throws IOException, SAXException {
		//获得用于建立 DOM tree的Xerces parser的对象
		// 这里用的是 Apache Xerces APIs
		DOMParser parser = new org.apache.xerces.parsers.DOMParser();

		// 获得要分析的xml文件
		Reader in = new BufferedReader(new FileReader("web.xml"));
		InputSource input = new org.xml.sax.InputSource(in);

	    // 开始分析
		parser.parse(input);

		// 从分析器中获得 DOM Document
		Document document = parser.getDocument();
		DocumentTraversal traversal = (DocumentTraversal) document;

		// 过滤只含有空格的节点
		NodeFilter filter = new NodeFilter() {
			public short acceptNode(Node n) {
				if (n.getNodeType() == Node.TEXT_NODE) {
					if (((Text) n).getData().trim().length() == 0)
						return NodeFilter.FILTER_REJECT;
				}
				return NodeFilter.FILTER_ACCEPT;
			}
		};

		// 设置显示所有节点但不显示内容
		int whatToShow = NodeFilter.SHOW_ALL & ~NodeFilter.SHOW_COMMENT;

		TreeWalker walker =
			traversal.createTreeWalker(document, whatToShow, filter, false);
		JTree tree = new JTree(new DOMTreeWalker(walker));

		// 建立一个窗口和滚动条
		JFrame frame = new JFrame("DOMTreeWalker Demo");
		frame.getContentPane().add(new JScrollPane(tree));
		frame.setSize(500, 250);
		frame.setVisible(true);
	}
}
