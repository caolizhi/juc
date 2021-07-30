/**
 *@Description
 *@Author 宝子哥
 *@Date 2021/7/30 10:21
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueue {

	public static void main(String[] args) {
		Queue<String> linkedQueue = new ConcurrentLinkedQueue<>();

		for (int i = 0; i < 10; i++) {
			linkedQueue.offer("a" + i); // 添加数据，返回 Boolean，add 如果加不进去会报异常
		}

		System.out.println(linkedQueue);
		System.out.println(linkedQueue.size());

		System.out.println(linkedQueue.poll()); // 取数据并 remove 掉
		System.out.println(linkedQueue.size());

		System.out.println(linkedQueue.peek()); // 取数据，但不会 remove 掉
		System.out.println(linkedQueue.size());
	}
}
