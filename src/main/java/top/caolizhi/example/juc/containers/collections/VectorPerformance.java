/**
 *@Description Vector 全程加锁
 *@Author 宝子哥
 *@Date 2021/7/29 16:50
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections;

import java.util.List;
import java.util.Vector;

public class VectorPerformance {

	static List<String> tickets = new Vector<>();

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
