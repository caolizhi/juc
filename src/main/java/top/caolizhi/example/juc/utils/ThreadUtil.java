/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/7/15 16:05
 *@Version 1.0
 **/

package top.caolizhi.example.juc.utils;

public class ThreadUtil {

	public static void ThreadSleepMills(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
