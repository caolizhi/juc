/**
 *@Description Sleep Yield 和 Join 的比较
 *@Author 宝子哥
 *@Date 2021/7/15 10:11
 *@Version 1.0
 **/

package top.caolizhi.example.juc.basic;

public class SleepYieldJoin {

	/**
	 * 当前线程暂停一段时间让给别的线程去运行
	 */
	static void testSleep() {
		System.out.println("thread " + Thread.currentThread().getName() + " suspend ....");
		new Thread(() -> {
			System.out.println("thread " + Thread.currentThread().getName() + " start ...");
			for (int i = 0; i < 100; i++) {
				System.out.println("i" + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("thread " + Thread.currentThread().getName() + " end ...");
		}).start();
	}

	/**
	 * 当前线程停下来进入等待队列，系统的调度算法可能还会把刚回的这个线程再拿出来执行，
	 * 更大的可能性是把原来等待的线程拿出一个来执行
	 */
	static void testYield() {
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				System.out.println("A " + i);
				if (i % 10 == 0) {
					Thread.yield();
				}
			}
		}).start();


		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				System.out.println("**********B " + i);
				if (i % 10 == 0) {
					Thread.yield();
				}
			}
		}).start();
	}

	/**
	 * 当前线程等待 join 的线程执行完，然后再继续执行当前线程，自己 join 自己没有意义
	 */
	static void testJoin() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			System.out.println("thread " + Thread.currentThread().getName() + " start ...");
			for (int i = 0; i < 100; i++) {
				System.out.println("A" + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("thread " + Thread.currentThread().getName() + " end ...");
		});

		Thread t2 = new Thread(() -> {

			try {
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("thread " + Thread.currentThread().getName() + " start ...");
			for (int i = 0; i < 100; i++) {
				System.out.println("B" + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("thread " + Thread.currentThread().getName() + " end ...");
		});

		t1.start();
		t2.start();

	}

	public static void main(String[] args) {
		//testSleep();
		//testYield();
		try {
			testJoin();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
