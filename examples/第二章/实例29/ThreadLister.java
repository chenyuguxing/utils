package 第二章.实例29;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class ThreadLister {
	// 显示一个线程的信息
	private static void printThreadInfo(
		PrintWriter out,
		Thread t,
		String indent) {
		if (t == null)
			return;
		out.println(
			indent
				+ "Thread: "
				+ t.getName()
				+ "  Priority: "
				+ t.getPriority()
				+ (t.isDaemon() ? " Daemon" : "")
				+ (t.isAlive() ? "" : " Not Alive"));
	}

	// 显示线程组的信息和它里面的线程或线程组的信息
	private static void printGroupInfo(
		PrintWriter out,
		ThreadGroup g,
		String indent) {
		if (g == null)
			return;
		int num_threads = g.activeCount();
		int num_groups = g.activeGroupCount();
		Thread[] threads = new Thread[num_threads];
		ThreadGroup[] groups = new ThreadGroup[num_groups];
        // 将线程组的所有活动线程拷贝到指定数组，不包含活动子组
		g.enumerate(threads, false);
		g.enumerate(groups, false);

		out.println(
			indent
				+ "Thread Group: "
				+ g.getName()
				+ "  Max Priority: "
				+ g.getMaxPriority()
				+ (g.isDaemon() ? " Daemon" : ""));

		for (int i = 0; i < num_threads; i++)
			printThreadInfo(out, threads[i], indent + "    ");
		for (int i = 0; i < num_groups; i++)
			printGroupInfo(out, groups[i], indent + "    ");
	}

	// 寻找根线程组并把它的信息都显示出来
	public static void listAllThreads(PrintWriter out) {
		ThreadGroup current_thread_group;
		ThreadGroup root_thread_group;
		ThreadGroup parent;

		// 获得当前线程组
		current_thread_group = Thread.currentThread().getThreadGroup();

		// 寻找根线程组
		root_thread_group = current_thread_group;
		parent = root_thread_group.getParent();
		while (parent != null) {
			root_thread_group = parent;
			parent = parent.getParent();
		}

		// 显示它的列表信息
		printGroupInfo(out, root_thread_group, "");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ThreadLister Demo");
		JTextArea textarea = new JTextArea();
		frame.getContentPane().add(
			new JScrollPane(textarea),
			BorderLayout.CENTER);
		frame.setSize(500, 400);
		frame.setVisible(true);

		// 用StringWriter流获取线程列表字符串信息
		StringWriter sout = new StringWriter(); // 用于获取列表
		PrintWriter out = new PrintWriter(sout); 
		ThreadLister.listAllThreads(out); // 将列表置于流中
		out.close();
		String threadListing = sout.toString(); // 取得线程列表信息字符串

		// 将线程列表信息在窗口上显示出来
		textarea.setText(threadListing);
	}
}
