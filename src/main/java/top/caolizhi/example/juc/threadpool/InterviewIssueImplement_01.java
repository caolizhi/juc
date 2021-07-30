/**
 *@Description 线程 1 打印 1 到 26，线程 2 打印 A 到 Z，要求用线程顺序打印 A1B2C3D4....Z26

 *@Author 宝子哥
 *@Date 2021/7/30 13:52
 *@Version 1.0
 **/

package top.caolizhi.example.juc.threadpool;

import java.util.concurrent.locks.LockSupport;

public class InterviewIssueImplement_01 {

	static Thread t1 = null;
	static Thread t2 = null;

	/**
	 * 使用 LockSupport 实现
	 */
	public static void main(String[] args) throws InterruptedException {


		String[] alphabet = {"A", "B", "C"};
		int[] numbers = {1, 2, 3};

		t1 = new Thread(() -> {
			for (int i = 0; i < numbers.length; i++) {
				System.out.print(numbers[i]);
				LockSupport.unpark(t2);
				LockSupport.park();
			}
		});

		t2 = new Thread(() -> {
			for (int i = 0; i < alphabet.length; i++) {
				LockSupport.park();
				System.out.print(alphabet[i]);
				LockSupport.unpark(t1);
			}
		});

		t1.start();
		t2.start();

	}
}
