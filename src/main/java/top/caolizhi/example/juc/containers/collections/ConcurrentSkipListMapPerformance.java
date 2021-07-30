/**
 *@Description ConcurrentSkipListMap 有序的，而 ConcurrentHashMap 是无序的
 *@Author 宝子哥
 *@Date 2021/7/30 9:25
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentSkipListMapPerformance {

	public static void main(String[] args) throws InterruptedException {
		Map<String, String> map  = new ConcurrentSkipListMap<String, String>();
		// Map<String, String> map  = new ConcurrentHashMap<String, String>();
		Random random = new Random();
		Thread[] threads = new Thread[100];
		CountDownLatch countDownLatch = new CountDownLatch(threads.length);
		long start = System.currentTimeMillis();

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				for (int j = 0; j< 100; j++) {
					String key = "a" + random.nextInt(1000000000);
					if (map.containsKey(key)) {
						System.out.println("key: "+ key +" 存在！！！");
						continue;
					}
					map.put(key, key);
				}
				countDownLatch.countDown();
			},"t" + 1);
		}

		Arrays.stream(threads).forEach(Thread::start);


		countDownLatch.await();

		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(map.size());
	}

}
