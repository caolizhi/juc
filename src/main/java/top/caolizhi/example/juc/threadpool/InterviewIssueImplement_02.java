/**
 *@Description 线程 1 打印 1 到 26，线程 2 打印 A 到 Z，要求用线程顺序打印 A1B2C3D4....Z26
 *
 * 用 notify ， wait， synchronized 实现
 *
 *@Author 宝子哥
 *@Date 2021/7/30 13:52
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

/**
 * 可以用 ReentrantLock lock() 代替 synchronized，
 * 然后 lock.newCondition 拿到 Condition 实例，调用 await() 和 signal() 来替换 wait() 和 notify()
 */
public class InterviewIssueImplement_02 {

	Object obj = new Object();
	String[] alphabet = {"A", "B", "C"};
	int[] numbers = {1, 2, 3};

	void m() {
		synchronized (obj) {
			for (int i = 0; i < numbers.length; i++) {
				System.out.print(numbers[i]);
				obj.notify();  // 如果不是 obj.notify() 而是直接 notify() 会报错 java.lang.IllegalMonitorStateException
				               // 所以 notify 和 wait 必须跟锁对象一起使用
				try {
					obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			obj.notify();  // 必须，否则一直有一个线程是在 wait 状态
		}
	}

	void n() {
		synchronized (obj) {
			for (int i = 0; i < alphabet.length; i++) {
				System.out.print(alphabet[i]);
				obj.notify();  // notify 只是通知可以来参与锁的竞争了，但是还并没有释放锁
				try {
					obj.wait(); // 释放锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			obj.notify();
		}
	}

	/**
	 * 使用 synchronized，wait，notify 实现
	 */
	public static void main(String[] args) throws InterruptedException {

		InterviewIssueImplement_02 implement02 = new InterviewIssueImplement_02();

		Thread t1 = new Thread(implement02::m);
		Thread t2 = new Thread(implement02::n);

		t1.start();
		t2.start();



	}
}
