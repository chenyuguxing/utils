package 第二章.实例30;

// 生产者与消费者共享的缓冲区，必须实现读、写的同步
public class Buffer {
	private int contents;
	private boolean available = false;

	public synchronized int get() {
		while (! available) {
			try {
				this.wait();
			} catch (InterruptedException exc) {}
		}
		int value = contents;
		// 消费者取出内容，改变存取控制available
		available = false;
		System.out.println("取出" + contents);
		this.notifyAll();
		return value;
	}

	public synchronized void put(int value) {
		while (available) {
			try {
				this.wait();
			} catch (InterruptedException exc) {}
		}
		contents = value;
		available = true;
		System.out.println("放入" + contents);
		this.notifyAll();
	}
}
