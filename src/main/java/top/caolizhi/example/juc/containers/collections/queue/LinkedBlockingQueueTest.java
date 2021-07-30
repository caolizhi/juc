/**
 *@Description LinkedBlockingQueueTest 阻塞队列 ，默认是无界的
 * put 和 take 方法真正的实现的阻塞队列
 * put 如果装满了线程阻塞住
 * take 如果空了的话线程会阻塞住
 *
 *@Author 宝子哥
 *@Date 2021/7/30 11:07
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class LinkedBlockingQueueTest {

	public static void main(String[] args) {

		BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					blockingQueue.put("a" + i); // 如果满了，就会等待
					//blockingQueue.add("a" + i); // 如果满了，就会报异常 Exception in thread "t1" java.lang.IllegalStateException: Queue full
					ThreadUtil.ThreadSleepMills(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();

		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				for (; ; ) {
					try {
						String take = blockingQueue.take(); // 如果空了，就会等待
						System.out.println(Thread.currentThread().getName() + " take -" + take);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}, "c" + i);
		}

	}

}
