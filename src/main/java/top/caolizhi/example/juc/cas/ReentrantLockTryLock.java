/**
 *@Description ReentrantLock
 * 使用 tryLock() 方法可以尝试获得锁，如果没有获得锁也会继续往下执行，这一点不同于 synchronized
 *
 *@Author 宝子哥
 *@Date 2021/7/21 15:59
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ReentrantLockTryLock {

	ReentrantLock reentrantLock = new ReentrantLock();

	void m1() {
		try {
			reentrantLock.lock(); // 获得独占锁
			for (int i = 0; i < 10; i++) {
				ThreadUtil.ThreadSleepMills(1000);
				System.out.println(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
	}

	void m2() {
		boolean locked = false;
		try {
			// 指定 5s 后开始拿锁，尝试获得锁，不管锁定与否都会继续执行，不像 synchronized 会阻塞。
			locked = reentrantLock.tryLock(5, TimeUnit.SECONDS);
			System.out.println(Thread.currentThread().getName() + " continue executing m2, locked = " + locked);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (locked) reentrantLock.unlock();
		}
	}

	/**
	 * 如果想要拿到 this 锁，就需要缩短 m1 方法的循环次数
	 */
	public static void main(String[] args) {
		ReentrantLockTryLock tryLock = new ReentrantLockTryLock();
		new Thread(tryLock::m1, "t1").start();
		ThreadUtil.ThreadSleepMills(1000);
		new Thread(tryLock::m2, "t2").start();
	}

}
