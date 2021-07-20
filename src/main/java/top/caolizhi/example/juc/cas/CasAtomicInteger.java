/**
 *@Description juc 中的 AtomicXxxxx 都是 CAS 的实现类又称原子类
 *@Author 宝子哥
 *@Date 2021/7/20 8:23
 *@Version 1.0
 **/

package top.caolizhi.example.juc.cas;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

public class CasAtomicInteger {

	AtomicInteger count = new AtomicInteger();

	void m() {
		for (int i = 0; i < 10000; i++) {
			count.incrementAndGet();
		}
	}

	public static void main(String[] args) {
		CasAtomicInteger casAtomicInteger = new CasAtomicInteger();


		List<Thread> threads = Lists.newArrayList();

		for (int i = 0; i < 10; i++) {
		    threads.add(new Thread(casAtomicInteger::m ,"t" + i));
		}

		threads.forEach(Thread::start);

		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		System.out.println(casAtomicInteger.count);
	}
}
