/**
 *@Description synchronized 可重入锁
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有了某个对象的锁，再次申请的时候仍然会得到该对象的锁
 *@Author 宝子哥
 *@Date 2021/7/15 16:51
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class SynchronizedReentrant {

	synchronized void m1() {
		System.out.println(Thread.currentThread().getName() + " m1 start");
		ThreadUtil.ThreadSleepMills(1000);
		m2();
		System.out.println(Thread.currentThread().getName() + " m1 end");
	}

	synchronized void m2() {
		ThreadUtil.ThreadSleepMills(2000);
		System.out.println(Thread.currentThread().getName() + " m2 ");
	}

	public static void main(String[] args) {
		new SynchronizedReentrant().m1();
	}

}
