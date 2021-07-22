/**
 *@Description ReentrantLock 默认是非公平锁，可以通过构造参数实现公平锁
 *@Author 宝子哥
 *@Date 2021/7/22 11:04
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockFairLock {

	ReentrantLock reentrantLock = new ReentrantLock(true);

	void m() {
		for (int i = 0; i < 5; i++) {
			try {
				reentrantLock.lock();
				System.out.println(Thread.currentThread().getName() + "获得锁。");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reentrantLock.unlock();
			}
		}
	}

	/**
	 * 使用默认 非公平锁 的输出如下：非公平锁会抢锁
	 * 			t1获得锁。
	 * 			t1获得锁。
	 * 			t1获得锁。
	 * 			t1获得锁。
	 * 			t1获得锁。
	 * 			t2获得锁。
 	 * 			t2获得锁。
	 * 			t2获得锁。
	 * 			t2获得锁。
	 * 			t2获得锁。
	 * ==============================
	 * 使用 公平锁 的输出如下：公平锁肯定是交替执行
	 * 			t1获得锁。
	 * 			t2获得锁。
	 * 			t1获得锁。
	 * 			t2获得锁。
	 * 			t1获得锁。
	 * 			t2获得锁。
	 * 			t1获得锁。
	 * 			t2获得锁。
	 * 			t1获得锁。
	 * 			t2获得锁。
	 */
	public static void main(String[] args) {
		ReentrantLockFairLock reentrantLockFairLock = new ReentrantLockFairLock();
		Thread t1 = new Thread(reentrantLockFairLock::m, "t1");
		Thread t2 = new Thread(reentrantLockFairLock::m, "t2");

		t1.start();
		t2.start();

	}
}
