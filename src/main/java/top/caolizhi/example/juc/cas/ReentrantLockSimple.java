/**
 *@Description ReentrantLock
 * 1. 使用 ReentrantLock 时，必须要手动释放锁，尤其是在碰到异常的时候，synchronized 是自动释放，而 ReentrantLock 必须要手动释放
 * 2. ReentrantLock 默认是用的是非公平锁，可以通过构造方法 new ReentrantLock(true) 是用公平锁
 *@Author 宝子哥
 *@Date 2021/7/21 15:59
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import java.util.concurrent.locks.ReentrantLock;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ReentrantLockSimple {

	ReentrantLock reentrantLock = new ReentrantLock();

	void m1() {
		try {
			reentrantLock.lock();
			System.out.println(Thread.currentThread().getName() + " m1 start");
			for (int i = 0; i < 10; i++) {
				ThreadUtil.ThreadSleepMills(1000);
				if (i == 2) m2();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(Thread.currentThread().getName() + " m1 end");
			reentrantLock.unlock();
		}
	}

	void m2() {
		try {
			reentrantLock.lock();
			System.out.println(Thread.currentThread().getName() + " I'm m2 ......");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
	}

	public static void main(String[] args) {
		ReentrantLockSimple reentrantSynchronized = new ReentrantLockSimple();
		new Thread(reentrantSynchronized::m1, "t1").start();
		ThreadUtil.ThreadSleepMills(1000);
		new Thread(reentrantSynchronized::m2, "t2").start();
	}

}
