/**
 *@Description ArrayBockingQueue 是有界的，必须制定大小
 * put 和 take 方法真正的实现的阻塞队列
 * put 如果装满了线程阻塞住
 * take 如果空了的话线程会阻塞住
 *@Author 宝子哥
 *@Date 2021/7/30 11:17
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBockingQueueTest {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);

		for (int i = 0; i < 10; i++) {
			arrayBlockingQueue.put("a" + i);
		}

		// arrayBlockingQueue.put("aaa");
		// arrayBlockingQueue.add("aaa");
		boolean aaa = arrayBlockingQueue.offer("aaa", 1, TimeUnit.SECONDS);
		System.out.println(aaa);

		System.out.println(arrayBlockingQueue);
	}
}
