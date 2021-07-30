/**
 *@Description 多线程的大多使用 ConcurrentHashMap，提高的效率主要在读上面，
 * 插入数据的时候，做了各种各样的判断，本来是链表的，到了 8 之后，变成了红黑树结构，
 * 然后也有很多 CAS 的判断，插入数据的时候效率要低一些。
 *@Author 宝子哥
 *@Date 2021/7/29 16:06
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.map;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2147
 * 1000000
 * ========
 * 207
 */
public class ConcurrentHashMapPerformance {

	static ConcurrentHashMap<UUID, UUID> hash = new ConcurrentHashMap<>();

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
