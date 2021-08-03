/**
 *@Description ScheduledPool 定时任务线程池，类似于一个 batch
 *@Author 宝子哥
 *@Date 2021/8/3 8:28
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledPool {

	public static void main(String[] args) {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
		scheduledThreadPool.scheduleAtFixedRate(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}, 0, 500, TimeUnit.MILLISECONDS);

	}
}
