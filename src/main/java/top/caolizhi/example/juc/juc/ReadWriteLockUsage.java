/**
 *@Description 读写锁，读锁是共享锁，写锁是排它锁
 *@Author 宝子哥
 *@Date 2021/7/22 14:51
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ReadWriteLockUsage {

	int value;
	void read(Lock lock) {
		try {
			lock.lock();
			ThreadUtil.ThreadSleepMills(1000);
			System.out.println("read over !");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	void write(Lock lock, int v) {
		try {
			lock.lock();
			ThreadUtil.ThreadSleepMills(1000);
			value = v;
			System.out.println("write over !");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		ReentrantLock reentrantLock = new ReentrantLock();
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		Lock readLock = readWriteLock.readLock();
		Lock writeLock = readWriteLock.writeLock();

		ReadWriteLockUsage readWriteLockUsage = new ReadWriteLockUsage();

		// ReentrantLock 的方式
		// Runnable readR = () -> readWriteLockUsage.read(reentrantLock);
		// Runnable writeR = () -> readWriteLockUsage.write(reentrantLock, new Random().nextInt());

		// ReadWriteLock 的方式
		Runnable readR = () -> readWriteLockUsage.read(readLock);
		Runnable writeR = () -> readWriteLockUsage.write(writeLock, new Random().nextInt());

		for (int i = 0; i < 18; i++) {
			new Thread(readR).start();
		}

		for (int i = 0; i < 2; i++) {
			new Thread(writeR).start();
		}



	}
}
