/**
 *@Description Executors.newSingleThreadExecutor() 产生一个单线程的线程池
 *@Author 宝子哥
 *@Date 2021/7/30 16:12
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPool {

	public static void main(String[] args) {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++) {
			int finalI = i;
			singleThreadExecutor.execute(() -> {
				System.out.println(finalI + " " + Thread.currentThread().getName());
			});
		}
		singleThreadExecutor.shutdown();
	}
}
