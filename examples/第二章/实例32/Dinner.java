package 第二章.实例32;

class Lock {
	protected boolean locked; //用来进行进程之间的同步

	public Lock() {
		locked = false;
	}

	// 同步方法,每个进程取筷子之前要locked=false才行
	public synchronized void lock() throws InterruptedException {
		while (locked)
			wait(); //等待locked=false
		locked = true; //加锁,防止别的进程取同一支筷子
	}

	public synchronized void unlock() { //解锁
		locked = false;
		notify(); //唤醒别的进程
	}
}

class Fork { //Fork表示就餐用的筷子
	public char id; //每支筷子的标识符

	private Lock lock = new Lock(); //lock用于同步

	public void pickup() throws InterruptedException {
		lock.lock(); //取筷子后加锁
	}

	public void putdown() throws InterruptedException {
		lock.unlock(); //放筷子后解锁
	}

	public Fork(int j) { //构造函数传进每支筷子的标识符
		Integer i = new Integer(j);

		id = i.toString().charAt(0);
	}
}

//Philosopher代表哲学家,是Thread的子类,可以单独运行一个线程
class Philosopher extends Thread {
	public char state = 't';
	//state代表哲学家的状态,初始化为思考状态                              

	private Fork L, R; //L,R分别表示左右筷子

	// 构造函数,传进当前哲学家用的左右筷子编号
	public Philosopher(Fork left, Fork right) {
		super();
		L = left;
		R = right;
	}

	protected void think() throws InterruptedException {
		sleep((long) (Math.random() * 7.0));
	}
	protected void eat() throws InterruptedException {
		sleep((long) (Math.random() * 7.0));
	}

	// run函数开始一个线程,代表当前哲学家的活动 
	public void run() {
		int i;
		try {
			for (i = 0; i < 100; i++) { //每个哲学家循环一百次后结束
				state = 't';
				think(); //思考
				state = 'w';
				sleep(1);
				L.pickup(); //取左筷子
				state = 'w';
				sleep(1);
				R.pickup(); //取右筷子
				state = 'e';
				eat(); //就餐
				L.putdown(); //放下左筷子
				R.putdown(); //放下右筷子
			}
			state = 'd'; //当前线程结束,当前哲学家停止一切活动
		} catch (InterruptedException e) {
		}
	}
}

public class Dinner {
	static Fork[] fork = new Fork[5]; //五支筷子
	static Philosopher[] philo = new Philosopher[5]; //五个哲学家

	public static void main(String[] args) {
		int i, j = 0, k = 0;
		boolean goOn;

		for (i = 0; i < 5; i++) { //初始化每支筷子
			fork[i] = new Fork(i);
		}
		for (i = 0; i < 4; i++) { //初试化每个哲学家
			philo[i] = new Philosopher(fork[i], fork[(i + 1) % 5]);
		}

		// 最后一个哲学家是用第一支和最后一支筷子
		philo[4] = new Philosopher(fork[0], fork[4]);

		for (i = 0; i < 5; i++) { //开始每个线程(哲学家开始活动).
			philo[i].start();
		}

		int newPrio = Thread.currentThread().getPriority() + 1;
		Thread.currentThread().setPriority(newPrio);
		goOn = true;
		while (goOn) {
			for (i = 0; i < 5; i++) {
				System.out.print(philo[i].state); //打印每个线程(哲学家)的状态
			}
			if (++j % 5 == 0)
				System.out.println();
			else
				System.out.print(' ');

			goOn = false;
			for (i = 0; i < 5; i++) { //如果所有线程都结束,就停止循环.
				goOn |= philo[i].state != 'd'; //diner[i].state = 'd'表示线程i已结束
			}
			try {
				Thread.sleep(51); //让当前线程休眠
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
