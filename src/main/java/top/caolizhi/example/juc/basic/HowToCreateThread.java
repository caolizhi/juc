/**
 *@Description 创建线程的5种方式
 *@Author 宝子哥
 *@Date 2021/7/15 9:23
 *@Version 1.0
 **/

package top.caolizhi.example.juc.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class HowToCreateThread {

	private static class extendThread extends Thread {
		@Override
		public void run() {
			System.out.println("1. extend Thread class, then override the run() method.");
		}
	}

	private static class implementRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("2. implement Runnable class, then override the run() method.");
		}
	}

	static class implementCallable implements Callable<String> {
		@Override
		public String call() throws Exception {
			return "3. implement Callable class, then override the call() method.";
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("5 methods to create a thread:");
		new extendThread().start();

		new Thread(new implementRunnable()).start();

		FutureTask futureTask = new FutureTask(new implementCallable());
		new Thread(futureTask).start();
		System.out.println(futureTask.get());

		new Thread(() -> {
			System.out.println("4. lambda expression to be a anonymous class");
		}).start();

		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(() -> {
			System.out.println("5. use thread pool to create a thread");
		});
		executorService.shutdown();

	}


}
