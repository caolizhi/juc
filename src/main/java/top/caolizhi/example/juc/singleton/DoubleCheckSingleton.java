/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/7/19 16:31
 *@Version 1.0
 **/

package top.caolizhi.example.juc.singleton;

public class DoubleCheckSingleton {

	private /*volatile*/ static DoubleCheckSingleton INSTANCE;

	public static DoubleCheckSingleton getInstance() {
		if (INSTANCE == null) { // 线程 A 执行到这，切换到 B线程也执行到这，判断为空，加锁，释放锁，
							    // 然后线程 A 又执行一遍加锁，释放锁，所以需要在 synchronized 代码块，再次判断是否为空
			synchronized (DoubleCheckSingleton.class) {
				if (INSTANCE == null) {
					INSTANCE = new DoubleCheckSingleton();  // 会发生指令重排，所有需要加 volatile
				}
			}
		}
		return INSTANCE;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			new Thread(() -> System.out.println(DoubleCheckSingleton.getInstance().hashCode()), "i" + i).start();
		}
	}
}
