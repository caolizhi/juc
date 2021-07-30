/**
 *@Description
 * put 和 take 方法真正的实现的阻塞队列
 * put 如果装满了线程阻塞住
 * take 如果空了的话线程会阻塞住
 *@Author 宝子哥
 *@Date 2021/7/30 13:09
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQueueTest {

	public static void main(String[] args) {
		BlockingQueue<String> priorityQueue = new PriorityBlockingQueue<>();

		priorityQueue.add("e");
		priorityQueue.add("c");
		priorityQueue.add("d");
		priorityQueue.add("b");
		priorityQueue.add("a");

		for (int i = 0; i < 5; i++) {
			System.out.println(priorityQueue.poll());
		}
	}

}
