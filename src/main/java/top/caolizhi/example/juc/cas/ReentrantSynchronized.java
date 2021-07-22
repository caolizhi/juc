/**
 *@Description 可重入锁，synchronized 是可重入锁
 *@Author 宝子哥
 *@Date 2021/7/21 15:59
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ReentrantSynchronized {

	synchronized void m1() {
		System.out.println(Thread.currentThread().getName() + " m1 start");
		for (int i = 0; i < 10; i++) {
			ThreadUtil.ThreadSleepMills(1000);
			if (i == 2) m2();
		}
	}

	synchronized void m2() {
		System.out.println(Thread.currentThread().getName() + " I'm m2 ......");
	}

	/**
	 * 线程 t1 进入到 m1 的时候，锁定了 this，t2 必须等 this 锁释放。
	 * 而 t1 只有执行完 m1 的时候，m2 才能执行
	 *
	 */
	public static void main(String[] args) {
		ReentrantSynchronized reentrantSynchronized = new ReentrantSynchronized();
		new Thread(reentrantSynchronized::m1, "t1").start();
		ThreadUtil.ThreadSleepMills(1000);
		new Thread(reentrantSynchronized::m2, "t2").start();
	}

}
