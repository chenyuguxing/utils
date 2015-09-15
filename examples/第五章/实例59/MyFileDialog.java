package 第五章.实例59;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyFileDialog extends JFrame {
	MyFileDialog(String title) {
		super(title);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	}
}

public class FileDialogDemo {
	public static void main(String args[]) {
		Frame f = new MyFileDialog("File Dialog Demo!");
		f.setVisible(true);
		f.setSize(100, 100);
		FileDialog fd = new FileDialog(f, "File Dialog");
		fd.setVisible(true);
	}
}
