package 第五章.实例55;
//TreeNodeDemo.java 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

public class JTreeDemo extends JFrame {
	protected JTree m_tree;
	protected JTextField m_display;

	public JTreeDemo() {
		super("SNMP Tree [OID]");
        // 创建5个树的用户对象引用 
        Object[] nodes = new Object[5]; 
		//创建树的根节点（第一层节点） 
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new OidNode(1,"ISO"));
		DefaultMutableTreeNode parent = top;
		//创建树根节点的子节点（第二层节点） 
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new OidNode(1,"standard"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(2, "member-body"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(3, "org"));
		parent.add(node);
		parent = node;
		//创建第二层节点的子节点（第三层节点） 
		node = new DefaultMutableTreeNode(new OidNode(1, "dod"));
		parent.add(node);
		parent = node;
		//创建第三层节点的子节点（第四层节点） 
		node = new DefaultMutableTreeNode(new OidNode(1, "internet"));
		parent.add(node);
		parent = node;
		//创建第四层节点的子节点（第五层节点） 
		node = new DefaultMutableTreeNode(new OidNode(1, "directory"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(2, "mgmt"));
		parent.add(node);
		node.add(new DefaultMutableTreeNode(new OidNode(1, "mib-2")));
		node = new DefaultMutableTreeNode(new OidNode(3, "experimental"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(4, "private"));
		node.add(new DefaultMutableTreeNode(new OidNode(1, "enterprises")));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(5, "security"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(6, "snmpV2"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(7, "mail"));
		parent.add(node);

		//以树的根节点构造整个树    
        m_tree =new JTree(top) 
        { 
             //设定JTree的工具提示 
             public String getToolTipText(MouseEvent e) {
				//获取鼠标在树组件中选择位置的树的路径
				TreePath path = m_tree.getPathForLocation(e.getX(), e.getY());
				//如果鼠标没有选择树节点上，则直接返回
				if (path == null)
					return null;
				//获取当前树路径的存储的树节点对象的数组（从根节点到当前树节点）
				Object[] nodes = path.getPath();
				String oid = "";
				for (int k = 0; k < nodes.length; k++) {
					//强制转化成树节点
					DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) nodes[k];
					//从树节点中获取其用户对象（OidNode的实例）
					OidNode nd = (OidNode) treenode.getUserObject();
					//把用户对象的对象标识进行累积
					oid += "." + nd.getId();
				}
				return oid;
			}
		}; 
		m_tree.setToolTipText(m_tree.getToolTipText());
        //ToolTipManager.sharedInstance().registerComponent(m_tree); 
        
		//显示JTree组件
		m_tree.setShowsRootHandles(true);
		//使JTree具有类似Windows文件管理器的直角连接线。
		m_tree.putClientProperty("JTree.lineStyle", "Angled");
		//设置树的树节点是不可编辑的
		m_tree.setEditable(false);
		//添加监视树的鼠标事件
		m_tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tvwTreeView_mouseClicked(e);
			}
		});
		//把树组件加入到滚动面板中 
		JScrollPane s = new JScrollPane(m_tree);
		getContentPane().add(s, BorderLayout.CENTER);
		m_display = new JTextField();
		m_display.setEditable(false);
		getContentPane().add(m_display, BorderLayout.SOUTH);
		//显示框架窗口 
		setSize(400, 300);
		setVisible(true);
	}

	//处理树组件的鼠标单击事件 
	private void tvwTreeView_mouseClicked(MouseEvent e) {
		//获取鼠标在树组件中选择位置的树的路径 
		TreePath path = m_tree.getPathForLocation(e.getX(), e.getY());
		//如果鼠标没有选择树节点上，则直接返回 
		if (path == null)
			return;
		//获取当前树路径的存储的树节点对象的数组（从根节点到当前树节点） 
		Object[] nodes = path.getPath();
		String oid = "";
		for (int k = 0; k < nodes.length; k++) {
			//强制转化成树节点 
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes[k];
			//从树节点中获取其用户对象（OidNode的实例） 
			OidNode nd = (OidNode) node.getUserObject();
			//把用户对象的对象名称进行累积 
			oid += "." + nd.getName();
		}
		m_display.setText(oid);
	}
	
	public static void main(String argv[]) {
		JTreeDemo frame = new JTreeDemo();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

//树的用户对象类（UserObject)，用于封装SNMP的OID 
class OidNode {
	//对象标识 
	private int m_id;
	//对象名称 
	private String m_name;
	//构造方法 
	public OidNode(int id, String name) {
		m_id = id;
		m_name = name;
	}
	//获取对象标识 
	public int getId() {
		return m_id;
	}
	//得到对象名称 
	public String getName() {
		return m_name;
	}
	//用户对象的字符串化，这个方法重写了Object.toString() 
	public String toString() {
		return m_name;
	}
}
