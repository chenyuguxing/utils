package 第二章.实例30;

// 生产者线程
public class Producer extends Thread {
	private Buffer buffer;
	private int number;

	public Producer(Buffer buffer, int number) {
		this.buffer = buffer;
		this.number = number;
	}

	public void run() {
		for (int i = 0;;) {
			buffer.put(i);
			System.out.println("生产者#" + number + "生产 " + i++);
			try {
				Thread.sleep((int) (Math.random() * 100));
			} catch (InterruptedException exc) {}
		}
	}
}
