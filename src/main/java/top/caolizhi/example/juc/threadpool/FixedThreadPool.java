/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/7/30 16:45
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;

public class FixedThreadPool {

	static class MyTask implements Callable<List<Integer>> {

		int starPos, endPos;

		public MyTask(int starPos, int endPos) {
			this.starPos = starPos;
			this.endPos = endPos;
		}

		@Override
		public List<Integer> call() throws Exception {
			return getPrimeNumber(starPos, endPos);
		};
	}

	private static List<Integer> getPrimeNumber(int start, int end) {
		List<Integer> list = Lists.newArrayList();
		for (int i = start; i < end; i++) {
			if (isPrime(i)) {
				list.add(i);
			}
		}
		return list;
	}

	private static boolean isPrime(int num) {
		for (int i = 2; i < num / 2; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		long start = System.currentTimeMillis();

		getPrimeNumber(1, 200000);

		long end = System.currentTimeMillis();

		System.out.println(end - start);

		final int cpuCoreNum = 4;

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(cpuCoreNum);

		MyTask t1 = new MyTask(1, 80000);
		MyTask t2 = new MyTask(80001, 130000);
		MyTask t3 = new MyTask(130001, 170000);
		MyTask t4 = new MyTask(170001, 200000);

		Future<List<Integer>> f1 = fixedThreadPool.submit(t1);
		Future<List<Integer>> f2 = fixedThreadPool.submit(t2);
		Future<List<Integer>> f3 = fixedThreadPool.submit(t3);
		Future<List<Integer>> f4 = fixedThreadPool.submit(t4);

		start = System.currentTimeMillis();
		f1.get();
		f2.get();
		f3.get();
		f4.get();
		end = System.currentTimeMillis();

		System.out.println(end - start);

		fixedThreadPool.shutdown();

	}


}
