package 第五章.实例64;
//  NotePad.java 
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class NotePad extends JFrame implements ActionListener {
	JTextArea textarea = new JTextArea();
	JMenuBar menubar = new JMenuBar();
	JMenu filemenu = new JMenu("File");
	JMenuItem newitem = new JMenuItem("New");
	JMenuItem openitem = new JMenuItem("Open");
	JMenuItem saveitem = new JMenuItem("Save");
	JMenuItem saveasitem = new JMenuItem("Save As");
	JMenuItem exititem = new JMenuItem("Exit");
	JMenu editmenu = new JMenu("Edit");
	JMenuItem selectitem = new JMenuItem("Select All");
	JMenuItem copyitem = new JMenuItem("Copy");
	JMenuItem cutitem = new JMenuItem("Cut");
	JMenuItem pasteitem = new JMenuItem("Paste");
	JMenu aboutmenu = new JMenu("About");
	JMenuItem info = new JMenuItem("About NotePad");
	String fileName = "NoName";  //设置默认的文件名
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipboard = toolkit.getSystemClipboard();  //剪贴板对象
	private FileDialog openFileDialog = new FileDialog(this, "Open File",
			FileDialog.LOAD);
	private FileDialog saveAsFileDialog = new FileDialog(this, "Save File As",
			FileDialog.SAVE);

	public NotePad() {
		setTitle("NotePad");
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBackground(Color.white);
		setSize(400, 300);
		filemenu.add(newitem);
		filemenu.add(openitem);
		filemenu.addSeparator();
		filemenu.add(saveitem);
		filemenu.add(saveasitem);
		filemenu.addSeparator();
		filemenu.add(exititem);
		editmenu.add(selectitem);
		editmenu.add(copyitem);
		editmenu.addSeparator();
		editmenu.add(cutitem);
		editmenu.add(pasteitem);
		menubar.add(filemenu);
		menubar.add(editmenu);
		menubar.add(aboutmenu);
		aboutmenu.add(info);
		setJMenuBar(menubar);
		getContentPane().add(textarea);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		newitem.addActionListener(this);
		openitem.addActionListener(this);
		saveitem.addActionListener(this);
		saveasitem.addActionListener(this);
		exititem.addActionListener(this);
		selectitem.addActionListener(this);
		copyitem.addActionListener(this);
		cutitem.addActionListener(this);
		pasteitem.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object eventSource = e.getSource();//利用事件源判断被单击的菜单项
		if (eventSource == newitem) {
			textarea.setText("");
		} else if (eventSource == openitem) {
			openFileDialog.show();
			fileName = openFileDialog.getDirectory() + openFileDialog.getFile();
			if (fileName != null)
				readFile(fileName);
		} else if (eventSource == saveitem) {
			if (fileName != null)
				writeFile(fileName);
		} else if (eventSource == saveasitem) {
			saveAsFileDialog.show();
			fileName = saveAsFileDialog.getDirectory()
					+ saveAsFileDialog.getFile();
			if (fileName != null)
				writeFile(fileName);
		} else if (eventSource == selectitem) {
			textarea.selectAll();
		} else if (eventSource == cutitem) {
			String text = textarea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipboard.setContents(selection, null);
			textarea.replaceRange("", textarea.getSelectionStart(), textarea
					.getSelectionEnd());
		} else if (eventSource == pasteitem) {
			Transferable contents = clipboard.getContents(this);
			if (contents == null)
				return;
			String text;
			text = "";
			try {
				text = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception exception) {
			}
			textarea.replaceRange(text, textarea.getSelectionStart(), textarea
					.getSelectionEnd());
		} else if (eventSource == exititem) {
			System.exit(0);
		}
	}

	//  读文件
	public void readFile(String fileName) {
		try {
			File file = new File(fileName);
			FileReader readIn = new FileReader(file);
			int size = (int) file.length();
			int charsRead = 0;
			char[] content = new char[size];
			while (readIn.ready())
				charsRead += readIn.read(content, charsRead, size - charsRead);
			readIn.close();
			textarea.setText(new String(content, 0, charsRead));
		} catch (IOException e) {
			System.out.println("Error Opening file");
		}
	}

	// 写文件
	public void writeFile(String fileName) {
		try {
			File file = new File(fileName);
			FileWriter writeOut = new FileWriter(file);
			writeOut.write(textarea.getText());
			writeOut.close();
		} catch (IOException e) {
			System.out.println("Error writing file");
		}
	}

	public static void main(String[] args) {
		Frame frame = new NotePad();
		frame.show();
	}
}
