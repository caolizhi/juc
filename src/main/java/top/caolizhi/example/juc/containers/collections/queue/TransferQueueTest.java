/**
 *@Description TransferQueue 传递，可以给线程传递任务，不像 SynchronousQueue 只能传递一个
 * TransferQueue 做成列表可以传递好多个
 *@Author 宝子哥
 *@Date 2021/7/30 13:20
 *@Version 1.0
 **/

package top.caolizhi.example.juc.containers.collections.queue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class TransferQueueTest {

	public static void main(String[] args) throws InterruptedException {
		TransferQueue<String> transferQueue = new LinkedTransferQueue<>();

		Thread t = new Thread(() -> {
			try {
				System.out.println(transferQueue.take());
				ThreadUtil.ThreadSleepMills(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		transferQueue.transfer("aaa"); // transfer 会等到 queue 里面的数据被消费掉，才会继续执行，一直阻塞在这里
		// transferQueue.put("aaa");  // put 方法直接丢到 queue 中就会继续执行下面的代码

		t.start();

		System.out.println(transferQueue.size());

	}

}
