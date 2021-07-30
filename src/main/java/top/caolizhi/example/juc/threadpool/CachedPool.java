/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/7/30 16:14
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class CachedPool {

	/**
	 *  任务队列是 SynchronousQueue，是一个容量为空的 Queue，来一个东西必须有一个线程执行，不然提交任务的线程在这里阻塞
	 *  生产者多个和消费者多个就用 TransferQueue，
	 *  一个新的任务就必须马上执行，没有线程就 new 出一个线程
	 *  线程没有上限
	 */
	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		System.out.println(cachedThreadPool);

		for (int i = 0; i < 2; i++) {
			cachedThreadPool.execute(() -> {
				ThreadUtil.ThreadSleepMills(500);
				System.out.println(Thread.currentThread().getName());
			});
		}
		System.out.println(cachedThreadPool);

		ThreadUtil.ThreadSleepMills(80000);

		System.out.println(cachedThreadPool);
	}
}
