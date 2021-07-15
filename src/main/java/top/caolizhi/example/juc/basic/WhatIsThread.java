/**
 *@Description 线程 T1 和 main 线程交替执行，程序中有两条不同的执行路径在交叉执行
 *@Author 宝子哥
 *@Date 2021/7/15 9:15
 *@Version 1.0
 **/

package top.caolizhi.example.juc.basic;

public class WhatIsThread {

	private static class T1 extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread");
			}
		}
	}

	public static void main(String[] args) {
		T1 t1 = new T1();
		t1.start();
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("main");
		}
	}
}
