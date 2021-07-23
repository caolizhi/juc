/**
 *@Description 交换器
 * 两个线程之间的交换数据
 *@Author 宝子哥
 *@Date 2021/7/23 10:42
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.concurrent.Exchanger;

public class ExchangerUsage {

	public static void main(String[] args) {
		Exchanger<String> stringExchanger = new Exchanger<>();

		new Thread(() -> {
			String s = "T1";
			try {
				s = stringExchanger.exchange(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " " + s);
		}, "t1").start();

		new Thread(() -> {
			String s = "T2";
			try {
				s = stringExchanger.exchange(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " " + s);
		}, "t2").start();
	}
}
