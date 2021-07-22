/**
 *@Description 通过 CAS 的操作来执行 i++，而不用 synchronized
 *@Author 宝子哥
 *@Date 2021/7/20 15:27
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicVsSyncVsLongAdder {

	static long count1 = 0L;
	static AtomicLong count2 = new AtomicLong();
	static LongAdder count3 = new LongAdder();

	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[1000];
		// 1000个线程，每个线程增加到 100w。比较三种方式的时间
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				for (int k = 0; k < 1_000_000; k++) {
					count2.incrementAndGet();
				}
			});
		}

		long start = System.currentTimeMillis();

		for(Thread thread : threads) thread.start();
		for(Thread thread : threads) thread.join();

		long end = System.currentTimeMillis();

		System.out.println("Atomic: " + count2.get() + " time " + (end-start));

		/***************************************************/

		Object o = new Object();


		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				for (int k = 0; k < 1_000_000; k++) {
					synchronized (o) {
						count1 ++;
					}
				}
			});
		}

		start = System.currentTimeMillis();

		for(Thread thread : threads) thread.start();
		for(Thread thread : threads) thread.join();

		end = System.currentTimeMillis();

		System.out.println("Sync: " + count1 + " time " + (end-start));

		/***************************************************/

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				for (int k = 0; k < 1_000_000; k++) {
					count3.increment();
				}
			});
		}

		start = System.currentTimeMillis();

		for(Thread thread : threads) thread.start();
		for(Thread thread : threads) thread.join();

		end = System.currentTimeMillis();

		System.out.println("LongAdder: " + count3.longValue() + " time " + (end-start));

	}


}
