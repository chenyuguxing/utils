package 第二章.实例30;

// 演示生产者∕消费者问题的主程序
public class ProducerConsumerProblem {
	public static void main(String[] args) {
		Buffer buffer = new Buffer();
		new Producer(buffer, 100).start();
		new Consumer(buffer, 300).start();
		new Consumer(buffer, 301).start();
	}
}
