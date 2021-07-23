/**
 *@Description Phaser 示例的 Person 类
 *@Author 宝子哥
 *@Date 2021/7/22 14:28
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc.domain;

import java.util.concurrent.Phaser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import top.caolizhi.example.juc.utils.ThreadUtil;

@AllArgsConstructor
@NoArgsConstructor
public class Person implements Runnable {

	String name;
	Phaser phaser;

	public void arrive() {
		ThreadUtil.ThreadSleepMills(1000);
		System.out.printf("%s 到达现场 \n", name);
		phaser.arriveAndAwaitAdvance();
	}

	public void eat() {
		ThreadUtil.ThreadSleepMills(1000);
		System.out.printf("%s 吃饭 \n", name);
		phaser.arriveAndAwaitAdvance();
	}

	public void leave() {
		ThreadUtil.ThreadSleepMills(1000);
		System.out.printf("%s 准备离开 \n", name);
		phaser.arriveAndAwaitAdvance();
	}

	public void hug() {
		if (name.equals("新娘") || name.equals("新郎")) {
			ThreadUtil.ThreadSleepMills(1000);
			System.out.printf("%s 洞房 \n", name);
			phaser.arriveAndDeregister();
		} else {
			ThreadUtil.ThreadSleepMills(1000);
			System.out.printf("%s 拥抱 \n", name);
			phaser.arriveAndDeregister();
		}
	}

	@Override
	public void run() {
		arrive();
		eat();
		leave();
		hug();
	}
}
