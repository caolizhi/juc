/**
 *@Description SynchronousQueue 容量为 0， 不是来存放数据的，专门用来线程间传递内容的
 * put 和 take 方法真正的实现的阻塞队列
 * put 如果装满了线程阻塞住
 * take 如果空了的话线程会阻塞住
 *
 * 可以用在线程池的任务调度上
 *@Author 宝子哥
 *@Date 2021/7/30 13:12
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class SynchronousQueueTest {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();

		new Thread(() ->{
			try {
				System.out.println(synchronousQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		System.out.println(synchronousQueue.size());

		ThreadUtil.ThreadSleepMills(3000);

		synchronousQueue.put("aaa");

		System.out.println(synchronousQueue.size());
	}

}
