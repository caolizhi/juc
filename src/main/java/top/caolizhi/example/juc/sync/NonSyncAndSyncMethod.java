/**
 *@Description 同步方法和非同步放可以同时调用
 *@Author 宝子哥
 *@Date 2021/7/15 16:01
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class NonSyncAndSyncMethod {

	// 同步方法
	synchronized void m1() {
		System.out.println(Thread.currentThread().getName() + " m1 start ...");
		ThreadUtil.ThreadSleepMills(10000);
		System.out.println(Thread.currentThread().getName() + " m1 end");
	}

	// 非同步方法
	void m2() {
		ThreadUtil.ThreadSleepMills(5000);
		System.out.println(Thread.currentThread().getName() + " m2 ");
	}

	public static void main(String[] args) {
		NonSyncAndSyncMethod nonSyncAndSyncMethod = new NonSyncAndSyncMethod();

		new Thread(nonSyncAndSyncMethod::m1, "t1").start();
		new Thread(nonSyncAndSyncMethod::m2, "t2").start();
	}

}
