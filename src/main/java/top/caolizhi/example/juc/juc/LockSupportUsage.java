/**
 *@Description LockSupport 线程阻塞工具类， 所有的方法都是静态方法，可以让线程在任意位置阻塞
 * 弥补 wait(), notify(), notifyAll() 和 Synchronized 不足：
 * 1. wait(), notify(), notifyAll() 必须在 Synchronized 用，其他地方会报：IllegalMonitorStateException
 * 2. 锁定的对象必须一直，一个同步代码块只能有一个线程调用 wait() 或者 notify()
 *
 *
 *
 *@Author 宝子哥
 *@Date 2021/7/23 11:32
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.concurrent.locks.LockSupport;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class LockSupportUsage {

	/**
	 * 1. LockSupport 不需要 synchronized 加锁就可以实现线程的阻塞和唤醒
	 * 2. unpark() 方法可以先于 park() 方法执行，并且不会阻塞线程
	 * 3. unpart() 只会唤醒一次，如果一个线程连续调用了两次 park()，那么该线程会永远的阻塞下去
	 *
	 * park 和 unpark 是 {@link jdk.internal.misc.Unsafe Unsafe} 类提供的，用一个变量作为标志，0 和  1 之间切换
	 */
	public static void main(String[] args) {
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println(i);
				if (i == 5) {
					System.out.println(Thread.currentThread().getName() + " park");
					// 无限期阻塞当前线程
					LockSupport.park();
				}
				if (i == 8) {
					System.out.println(Thread.currentThread().getName() + " park again");
					// 无限期阻塞当前线程
					LockSupport.park();
				}
				ThreadUtil.ThreadSleepMills(1000);
			}
		}, "t1");
		t1.start();
		// ThreadUtil.ThreadSleepMills(10000);
		System.out.println("unpark the t1");
		LockSupport.unpark(t1); // 可以优先于 park() 方法执行，只能解一次 park()

		// System.out.println("unpark the t1 again");
		// LockSupport.unpark(t1); // 可以优先于 park() 方法执行，只能解一次 park()
	}
}
