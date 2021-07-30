/**
 *@Description DelayQueue 可以实现时间上的排序，必须实现 Delayed 接口
 * 定义时间长短排序方法，时间等待越短就会越优先的得到执行。
 * put 和 take 方法真正的实现的阻塞队列
 * put 如果装满了线程阻塞住
 * take 如果空了的话线程会阻塞住
 *@Author 宝子哥
 *@Date 2021/7/30 11:23
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

public class DelayQueueTest {

	/**
	 *  本质上用了 {@link java.util.PriorityQueue PriorityQueue}，PriorityQueue 是从 AbstractQueue 继承的
	 *  PriorityQueue 的特点是，添加元素的时候并不是顺序的，而是内部进行了一个排序，按照优先级，最小的优先。
	 *  内部实现是一个二叉树，这个二叉树可以认为是堆排序里面的最小的那个堆值在最上面
	 */
	static BlockingQueue<MyTask> delayQueue = new DelayQueue<>();

	@Getter
	@Setter
	static class MyTask implements Delayed {


		String name;
		long runningTIme;

		public MyTask(String name, long runningTIme) {
			this.name = name;
			this.runningTIme = runningTIme;
		}

		@Override
		public long getDelay(TimeUnit unit) {

			return unit.convert(runningTIme - System.currentTimeMillis(), TimeUnit.MILLISECONDS);

		}

		@Override
		public int compareTo(Delayed o) {

			if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
				return -1;
			} else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
				return 1;
			} else return 0;
		}
	}


	public static void main(String[] args) throws InterruptedException {
		long now = System.currentTimeMillis();

		MyTask t1 = new MyTask("t1", now + 1000);
		MyTask t2 = new MyTask("t2", now + 2000);
		MyTask t3 = new MyTask("t3", now + 1500);
		MyTask t4 = new MyTask("t4", now + 2500);
		MyTask t5 = new MyTask("t5", now + 500);

		delayQueue.put(t1);
		delayQueue.put(t2);
		delayQueue.put(t3);
		delayQueue.put(t4);
		delayQueue.put(t5);

		System.out.println(delayQueue);

		for (int i = 0; i < 5; i++) {
			System.out.println(delayQueue.take().name);
		}
	}
}
