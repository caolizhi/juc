/**
 *@Description 阶段，按照不同的阶段对线程进行执行，就是它本身维护着一个阶段这样的成员变量，
 * 每个阶段的任务可以多个线程并发执行，但是必须要上一个阶段的任务都是完成了才可以执行下一个阶段的任务。
 *@Author 宝子哥
 *@Date 2021/7/22 14:17
 *@Version 1.0
 **/

package top.caolizhi.example.juc.juc;

import java.util.concurrent.Phaser;

import top.caolizhi.example.juc.juc.domain.Person;

public class PhaserUsage {

	static class MarriagePhaser extends Phaser {

		@Override
		protected boolean onAdvance(int phase, int registeredParties) {
			switch (phase) {
				case 0:
					System.out.println("所有人都到齐了 ！" + registeredParties);
					return false;
				case 1 :
					System.out.println("所有人都吃完了 ！" + registeredParties);
					return false;
				case 2 :
					System.out.println("所有人都离开了 ！" + registeredParties);
					return false;
				case 3 :
					System.out.println("婚礼结束，新郎新娘拥抱 ~ " + registeredParties);
					return true;
				default:
					return true;
			}
		}
	}

	public static void main(String[] args) {
		MarriagePhaser marriagePhaser = new MarriagePhaser();
		marriagePhaser.bulkRegister(7);
		for (int i = 0; i < 5; i++) {
			new Thread(new Person("person" + i, marriagePhaser)).start();
		}
		new Thread(new Person("新郎", marriagePhaser)).start();
		new Thread(new Person("新娘", marriagePhaser)).start();
	}

}
