/**
 *@Description  Synchronized 不适合对 String , Integer 等类型加锁，大部分情况下锁住的不是同一个 String 对象
 *@Author 宝子哥
 *@Date 2021/7/15 14:21
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import lombok.NoArgsConstructor;
import top.caolizhi.example.juc.utils.ThreadUtil;

@NoArgsConstructor
public class SynchronizedString {

	static int count = 10;

	public void run(String abc) {
		synchronized (abc.intern()) {
			ThreadUtil.ThreadSleepMills(1000);
			count --;
			System.out.println(Thread.currentThread().getName() + " get the lock. " + " count = " + count);
		}

	}

	public static void main(String[] args) throws InterruptedException {
		SynchronizedString synchronizedObject = new SynchronizedString();
		StringBuilder sb = new StringBuilder();
		sb.append("a");
		for (int i = 0; i < 10; i++) {
			new Thread(() -> synchronizedObject.run(sb.toString()), "thread " + i).start();
		}
	}
}
