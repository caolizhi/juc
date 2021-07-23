/**
 *@Description CyclicBarrier 循环栅栏，也是一个同步辅助工具类
 * 意思是有一个栅栏拦住了，什么时候人满了就把栅栏推倒，然后再把栅栏立起来，下一波人又满了再推倒，如此往复
 * 基于 ReentrantLock 实现的工具类
 *@Author 宝子哥
 *@Date 2021/7/22 13:51
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierUsage {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> {
			System.out.println("人满了，推倒！");
		});

		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

}
