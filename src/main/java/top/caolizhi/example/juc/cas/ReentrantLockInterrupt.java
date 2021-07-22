/**
 *@Description ReentrantLock
 * lockInterruptibly() 方法，可以把一直处于等待锁状态的线程打断
 *@Author 宝子哥
 *@Date 2021/7/21 15:59
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ReentrantLockInterrupt {

	/**
	 * t1 启动后会一直拿这锁，t2 启动后如果是用 lock() 方法，会一直等待 t1 释放锁，
	 * 即使调用了 interrupt(） 方法；如果是用 lockInterruptibly() 方法，t2 会因为 interrupt 而打断，
	 * 是可以被打断的加锁。
	 */
	public static void main(String[] args) {
		ReentrantLock reentrantLock = new ReentrantLock();

		Thread t1 = new Thread(() -> {
			reentrantLock.lock();
			System.out.println("t1 start");
			try {
				TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
				System.out.println("t1 end");
			} catch (InterruptedException e) {
				System.out.println("t1 interrupted");
			} finally {
			  reentrantLock.unlock();
			}
		});

		t1.start();

		Thread t2 = new Thread(() -> {
			try {
				// reentrantLock.lock();
				reentrantLock.lockInterruptibly();
				System.out.println("t2 start");
				TimeUnit.SECONDS.sleep(5);
				System.out.println("t2 end");
			} catch (InterruptedException e) {
				System.out.println("t2 interrupted");
			} finally {
			    reentrantLock.unlock();
			}
		});

		t2.start();

		ThreadUtil.ThreadSleepMills(3000);

		t2.interrupt();
	}

}
