/**
 *@Description 模拟银行账户
 * 有些业务加锁，有些业务不加锁，这样行不行？
 *
 * 如果能容忍数据不一样，可以这样做，否则容易产生脏读
 *
 *@Author 宝子哥
 *@Date 2021/7/15 16:35
 *@Version 1.0
 **/

package top.caolizhi.example.juc.sync;

import top.caolizhi.example.juc.utils.ThreadUtil;

public class Account {

	String name;
	double balance;

	synchronized void set(String name, double balance) {
		this.name = name;

		ThreadUtil.ThreadSleepMills(2000);

		this.balance = balance;

	}

	// 不加锁容易产生脏读
	public /*synchronized*/ double getBalance(String name) {
		return this.balance;
	}

	/**
	 *  首先设置名字和金额，
	 *  在设置名字的时候，sleep 2s，然后再设置金额
	 *  如果不加锁拿到的就是 balance 的初始值，因为锁未释放，需要设置的值未同步到内存
	 *  再 sleep 2s，那么锁已经释放，此时拿到的就是最新的。
	 */
	public static void main(String[] args) {
		Account account = new Account();

		new Thread(() -> account.set("clz", 100.0)).start();

		ThreadUtil.ThreadSleepMills(1000);

		System.out.println(account.getBalance("clz"));

		ThreadUtil.ThreadSleepMills(2000);

		System.out.println(account.getBalance("clz"));
	}
}
