package 第二章.实例31;

public class Deadlock {
	public static void main(String[] args) {
		// 定义线程竞争的两个临界资源
		final Object resource1 = "resource1";
		final Object resource2 = "resource2";
		// 第一个线程，先竞争resource1，后竞争resource2
		Thread t1 = new Thread() {
		public void run() {
			// 锁定resource1
			synchronized(resource1) {
			System.out.println("Thread 1: locked resource 1");
			// 线程休眠一段时间
			try { Thread.sleep(50); }
			catch (InterruptedException e) {}
			
			// 竞争resource2
			synchronized(resource2) {
				System.out.println("Thread 1: locked resource 2");
			}
			}
		}
		};
        
		// 第二个线程，先竞争resource2，后竞争resource1
		Thread t2 = new Thread() {
		public void run() {
			// 线程锁定resource1
			synchronized(resource2) {
			System.out.println("Thread 2: locked resource 2");
			
			// 休眠片刻
			try { Thread.sleep(50); }
			catch (InterruptedException e) {}
			
			// 竞争resource2
			synchronized(resource1) {
				System.out.println("Thread 2: locked resource 1");
			}
			}
		}
		};
        
        // 启动两个线程
		t1.start(); 
		t2.start();
	}
}
