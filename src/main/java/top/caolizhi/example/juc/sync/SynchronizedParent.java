/**
 *@Description 给父类加锁，子类调用父类，也是可重入
 *@Author 宝子哥
 *@Date 2021/7/15 17:07
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class SynchronizedParent {

	synchronized void m1() {
		System.out.println("m start");
		ThreadUtil.ThreadSleepMills(1);
		System.out.println("m end");
	}

	public static void main(String[] args) {
		new child().m1();
	}
}

class child extends SynchronizedParent {
	@Override
	synchronized void m1() {
		System.out.println("child start");
		super.m1();
		System.out.println("child end");
	}
}
