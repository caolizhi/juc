/**
 *@Description volatile 保证可见性
 *@Author 宝子哥
 *@Date 2021/7/19 9:32
 *@Version 1.0
 **/

package top.caolizhi.example.juc.vol;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class HelloVolatile {

	/*volatile*/ boolean isRunning = true;

	void m() {
		System.out.println("m start");
		while (isRunning) {

		}
		System.out.println("m end");
	}

	/**
	 * A,B 两个线程都用到一个变量，java 默认是线程中保留一份 copy， A 修改了变量值，B 未必知道
	 * 不加 volatile，线程一直在运行，不知道什么时候检测到 isRunning 是 false。
	 */
	public static void main(String[] args) {
		HelloVolatile helloVolatile = new HelloVolatile();
		new Thread(helloVolatile::m, "t").start();
		ThreadUtil.ThreadSleepMills(1000);
		helloVolatile.isRunning = false;
	}

}
