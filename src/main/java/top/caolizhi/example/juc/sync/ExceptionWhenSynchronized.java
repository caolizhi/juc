/**
 *@Description 程序在执行过程中，如果出现异常，默认情况下锁会被释放，
 * 其他其他线程会进入同步代码，
 * 有可能会访问到异常的数据
 *@Author 宝子哥
 *@Date 2021/7/16 9:51
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ExceptionWhenSynchronized {

	int count = 0;

	synchronized void m() {
		System.out.println(Thread.currentThread().getName() + " start ");
		while (true) {
			count ++;

			System.out.println(Thread.currentThread().getName() + " count = " + count);

			ThreadUtil.ThreadSleepMills(1000);
			if (count == 5) {
				int i = 1/0; // 异常，锁释放，若不想释放可加 Catch
				System.out.println(i);
			}
		}
	}

	/**
	 * 在 t1 线程 count 到 5 的时候，产生异常，然后锁释放，被 t2 拿到。
	 */
	public static void main(String[] args) {
		ExceptionWhenSynchronized exceptionWhenSynchronized = new ExceptionWhenSynchronized();
		Runnable m = exceptionWhenSynchronized::m;
		new Thread(m, "t1").start();
		new Thread(m, "t2").start();
	}

}
