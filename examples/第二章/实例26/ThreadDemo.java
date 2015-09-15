package 第二章.实例26;

public class ThreadDemo extends Thread {

	// 覆写run方法
	public void run() {
		for (int i = 0; i < 5; i++)
			compute();
	}

	// main方法产生另外两个线程和原来解释器的初始线程加在一起共三个一起执行
	public static void main(String[] args) {
		// 创建第一个线程
		ThreadDemo thread1 = new ThreadDemo();

		// 创建第二个线程
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; i++)
					compute();
			}
		});
		// 设置这两个线程的属性
		if (args.length >= 1)
			thread1.setPriority(Integer.parseInt(args[0]));
		if (args.length >= 2)
			thread2.setPriority(Integer.parseInt(args[1]));
		// 启动线程
		thread1.start();
		thread2.start();
		// 这个main()方法是由Java解释器产生的初始线程来运行的
		for (int i = 0; i < 5; i++)
			compute();
	}

	// ThreadLocal对象有一个属性值可以get()和set()的，可以维护不同的线程的不同属性值 
	// 用于记录每个线程调用compute方法的次数
	static ThreadLocal numcalls = new ThreadLocal();

	// 供线程调用的方法
	static synchronized void compute() {
		// 计算被当前线程调用的次数
		Integer n = (Integer) numcalls.get();
		if (n == null)
			n = new Integer(1);
		else
			n = new Integer(n.intValue() + 1);
		numcalls.set(n);

		// 显示线程的名称和它的调用次数
		System.out.println(Thread.currentThread().getName() + ": " + n);

		// 模拟需要大量计算的线程
		for (int i = 0, j = 0; i < 1000000; i++)
			j += i;
		try {
			// 线程休眠一段时间
			Thread.sleep((int) (Math.random() * 100 + 1));
		} catch (InterruptedException e) {
		}

		// 当前线程暂停并允许其它线程执行，因此不会有饥饿状态的线程
		Thread.yield();
	}
}
