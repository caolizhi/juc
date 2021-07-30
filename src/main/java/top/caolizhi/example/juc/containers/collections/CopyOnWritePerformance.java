/**
 *@Description {@link java.util.concurrent.CopyOnWriteArrayList CopyOnWriteArrayList }
 * 写时复制
 * 当写的时候把老的复制出来，拷贝的时候扩展出一个新元素位置，然后再添加新的元素添加到最后的那个位置上，
 * 把指向老的引用指向新的，写的时候不加锁
 *@Author 宝子哥
 *@Date 2021/7/30 10:09
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWritePerformance {

	public static void main(String[] args) throws InterruptedException {
		CopyOnWriteArrayList<Object> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
		Random random = new Random();
		Thread[] threads = new Thread[100];
		for (int i = 0; i < threads.length; i++) {
			Runnable task = new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 1000; j++) {
						copyOnWriteArrayList.add("a" + random.nextInt(10000));
					}
				}
			};
			threads[i] = new Thread(task);
		}

		long start = System.currentTimeMillis();

		for (Thread thread : threads)
			thread.start();
		for (Thread thread : threads)
			thread.join();

		long end = System.currentTimeMillis();

		System.out.println(end - start);

		System.out.println(copyOnWriteArrayList.size());

	}
}
