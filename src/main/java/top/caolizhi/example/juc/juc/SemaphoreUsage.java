/**
 *@Description 信号量
 * 可以往里面传一个数字，permits 是允许的线程数量，想象有几盏信号灯，
 * 一个灯里面闪烁数组表示到底允许几个来参考我这个信号灯
 * 主要目的是用来限流
 *
 * 默认是非公平的锁
 *@Author 宝子哥
 *@Date 2021/7/23 10:10
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.concurrent.Semaphore;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class SemaphoreUsage {

	public static void main(String[] args) {
		// 允许一个线程同时执行，T1 或 T2 轮流输出
		// Semaphore semaphore = new Semaphore(1);
		// T1，T2 同时输出，默认非公平锁
		// Semaphore semaphore = new Semaphore(2);
		// 公平锁
		Semaphore semaphore = new Semaphore(2, true);
		new Thread(() -> {
			try {
				semaphore.acquire();
				System.out.println("T1 running ..");
				ThreadUtil.ThreadSleepMills(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("T1 stopping ..");
			    semaphore.release();
			}
		}).start();

		new Thread(() -> {
			try {
				semaphore.acquire();
				System.out.println("T2 running ..");
				ThreadUtil.ThreadSleepMills(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("T2 stopping ..");
				semaphore.release();
			}
		}).start();

	}

}
