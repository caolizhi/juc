/**
 *@Description Count down 倒数，latch 门栓，5，4，3，2，1 门栓打开
 * CountDownLatch 是一个同步辅助类，让一个线程或者一组线程等待，直到其他线程特定的操作完成
 *@Author 宝子哥
 *@Date 2021/7/22 13:31
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class CountDownLatchUsage {

	static void usingCountDownLatch() throws InterruptedException {
		Thread[] threads = new Thread[100];
		CountDownLatch countDownLatch = new CountDownLatch(threads.length);

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				int result = 0;
				for (int j = 0; j < 10000; j++) {
				    result += j;
				}
				countDownLatch.countDown(); // 线程执行结束，count down 一次
			});
		}

		IntStream.range(0, threads.length).forEachOrdered(i -> threads[i].start());

		countDownLatch.await(); // 看住门，等 count down 到 0 ，实际上是等所有的线程结束

		System.out.println("end latch");
	}

	static void usingJoin() throws InterruptedException {
		Thread[] threads = new Thread[100];

		for (int i = 0; i < threads.length; i++) {
		    threads[i] = new Thread(() -> {
		    	int result = 0;
				for (int j = 0; j < 10000; j++) {
					result += j;
				}
			});
		}

		IntStream.range(0, threads.length).forEachOrdered(i -> threads[i].start());

		for (Thread thread : threads) {
			thread.join();
		}

		System.out.println("end join");
	}

	public static void main(String[] args) throws InterruptedException {
		usingCountDownLatch();
		usingJoin();
	}


}
