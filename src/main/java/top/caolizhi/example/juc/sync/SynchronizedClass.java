/**
 *@Description  synchronized 既保证了原子性又保证了可见性
 *@Author 宝子哥
 *@Date 2021/7/15 14:21
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SynchronizedClass implements Runnable{

	static int count = 10;

	@Override
	public void run() {
		synchronized (SynchronizedClass.class) {
			count --;
			System.out.println(Thread.currentThread().getName() + " get the lock. " + "count = " + count);
		}

	}

	public static void main(String[] args) throws InterruptedException {
		SynchronizedClass synchronizedObject = new SynchronizedClass();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedObject, "thread " + i).start();
		}
	}
}
