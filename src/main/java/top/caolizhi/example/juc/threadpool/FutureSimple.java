/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/7/30 15:15
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class FutureSimple {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
			ThreadUtil.ThreadSleepMills(1000);
			return 1000;
		});

		new Thread(futureTask).start();

		System.out.println(futureTask.get());
	}
}
