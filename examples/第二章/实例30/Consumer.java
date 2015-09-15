package 第二章.实例30;

// 消费者线程
public class Consumer extends Thread {
	private Buffer buffer;
	private int number;

	public Consumer(Buffer buffer, int number) {
		this.buffer = buffer;
		this.number = number;
	}

	public void run() {
		for (;;) {
			int v = buffer.get();
			System.out.println("消费者#" + number + "消费" + v);
		}
	}
}
