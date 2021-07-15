/**
 *@Description 线程的 6 个状态
 * NEW
 * RUNNABLE
 * BLOCKED
 * WAITING
 * TIMED_WAITING
 * TERMINATED
 *@Author 宝子哥
 *@Date 2021/7/15 11:23
 *@Version 1.0
 **/

package top.caolizhi.example.juc.basic;

public class ThreadState {

	static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("myThread state is " + this.getState());
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		System.out.println("myThread state is " + myThread.getState());
		myThread.start();

		new Thread(() -> {
			System.out.println("myThread state is " + myThread.getState());
		}).start();

		try {
			myThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("myThread state is " + myThread.getState());

	}
}
