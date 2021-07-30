/**
 *@Description 线程 1 打印 1 到 26，线程 2 打印 A 到 Z，要求用线程顺序打印 A1B2C3D4....Z26
 * 可以用 ReentrantLock lock() 代替 synchronized，
 * 然后 lock.newCondition 拿到 Condition 实例，调用 await() 和 signal() 来替换 wait() 和 notify()
 *@Author 宝子哥
 *@Date 2021/7/30 13:52
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CAS 实现
 */
public class InterviewIssueImplement_03 {

	static Thread t1 = null;
	static Thread t2 = null;

	public static void main(String[] args) throws InterruptedException {

		String[] alphabet = {"A", "B", "C"};
		int[] numbers = {1, 2, 3};

		ReentrantLock reentrantLock = new ReentrantLock();
		Condition condition = reentrantLock.newCondition();



		t1 = new Thread(() -> {
			reentrantLock.lock();
			for (int i = 0; i < numbers.length; i++) {
				System.out.print(numbers[i]);
				condition.signal();
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			condition.signal();
			reentrantLock.unlock();
		});


		t2 = new Thread(() -> {
			reentrantLock.lock();
			for (int i = 0; i < alphabet.length; i++) {
				System.out.print(alphabet[i]);
				condition.signal();
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			condition.signal();
			reentrantLock.unlock();
		});

		t1.start();
		t2.start();

	}
}
