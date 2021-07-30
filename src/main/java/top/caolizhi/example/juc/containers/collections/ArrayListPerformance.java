/**
 *@Description ArrayList 线程不安全
 *@Author 宝子哥
 *@Date 2021/7/29 16:50
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections;

import java.util.List;

import com.google.common.collect.Lists;

public class ArrayListPerformance {

	static List<String> tickets = Lists.newArrayList();

	static {
		for (int i = 0; i < 10000; i++)
			tickets.add("票编号" + i);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while (tickets.size() > 0) {
					System.out.println("销售了 " + tickets.remove(0));
				}
			}).start();
		}
	}
}
