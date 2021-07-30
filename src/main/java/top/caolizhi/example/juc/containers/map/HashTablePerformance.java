/**
 *@Description HashTable 每一个方法都加了锁
 *@Author 宝子哥
 *@Date 2021/7/29 16:06
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.map;

import java.util.Hashtable;
import java.util.UUID;

/**
 * 5485
 * 1000000
 * =========
 * 2325
 */
public class HashTablePerformance {

	static Hashtable<UUID, UUID> hash = new Hashtable<>();

	static int count = 1_000_000;

	static int threadCount = 100;

	static UUID[] keys = new UUID[count];
	static UUID[] values = new UUID[count];

	static {
		for (int i = 0; i < count; i++) {
			keys[i] = UUID.randomUUID();
			values[i] = UUID.randomUUID();
		}
	}

	static class myThread extends Thread {

		int start;
		int gap = count/threadCount;

		public myThread(int start) {
			this.start = start;
		}

		@Override
		public void run() {

			for (int i = 0; i < start + gap; i++) {
				hash.put(keys[i], values[i]);
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();

		Thread[] threads = new Thread[threadCount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new myThread(i * (count/threadCount));
		}

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

		long end = System.currentTimeMillis();

		System.out.println(end -start);
		System.out.println(hash.size());

		System.out.println("======================================================================");

		start = System.currentTimeMillis();

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				for (int j = 0; j < 1_000_000; j++) {
					hash.get(keys[10]);
				}
			});
		}

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

		end = System.currentTimeMillis();

		System.out.println(end - start);

	}

}
