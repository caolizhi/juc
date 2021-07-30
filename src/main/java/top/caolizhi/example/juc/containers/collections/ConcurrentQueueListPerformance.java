/**
 *@Description 考虑多线程单个元素的时候，多考虑考虑 Queue
 *@Author 宝子哥
 *@Date 2021/7/29 16:50
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueueListPerformance {

	static Queue<String> tickets = new ConcurrentLinkedQueue<>();

	static {
		for (int i = 0; i < 1000; i++)
			tickets.add("票编号" + i);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while (true) {
					String poll = tickets.poll();
					if (poll == null) break;
					else System.out.println("销售了 " + poll);
				}
			}).start();
		}
	}
}
