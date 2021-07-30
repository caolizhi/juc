/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/7/30 15:52
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class ThreadPoolSimple {

	static class Task implements Runnable {

		int i;

		public Task(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " Task" + i);
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return "Task{i=" + i + "}";
		}
	}

	public static void main(String[] args) {
		// 自定义线程池
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			2,
			4,
			60,
			TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(4),
			Executors.defaultThreadFactory(),
			new ThreadPoolExecutor.CallerRunsPolicy()
		);
		for (int i = 0; i < 8; i++) {
			threadPoolExecutor.execute(new Task(i));
		}

		System.out.println(threadPoolExecutor.getQueue());
		threadPoolExecutor.execute(new Task(100));  // main 线程调用
		System.out.println("============== " + threadPoolExecutor.getQueue());

		ThreadUtil.ThreadSleepMills(5000);
		threadPoolExecutor.shutdown();
	}
}
