/**
 *@Description LinkedList 加锁效率都不够高
 *@Author 宝子哥
 *@Date 2021/7/29 16:50
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections;

import java.util.LinkedList;
import java.util.List;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class LinkedListPerformance {

	static List<String> tickets = new LinkedList<>();

	static {
		for (int i = 0; i < 10000; i++)
			tickets.add("票编号" + i);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while (true) {
					synchronized (tickets) {
						if (tickets.size() <= 0) {
							break;
						}
						ThreadUtil.ThreadSleepMills(10000);
						System.out.println("销售了 " + tickets.remove(0));
					}
				}
			}).start();
		}
	}
}
