/**
 *@Description 最简单的 单例类
 * 饿汉式
 * 类加载到内存后，被实例化一个单例， JVM 保证线程安全
 *@Author 宝子哥
 *@Date 2021/7/19 15:04
 *@Version 1.0
 **/

package top.caolizhi.example.juc.singleton;

public class HungrySingleton {

	/**
	 * 只有我自己能够 new，其他任务法访问
	 */
	private static final HungrySingleton INSTANCE = new HungrySingleton();

	public static HungrySingleton getInstance() {
		return INSTANCE;
	}

	public static void main(String[] args) {
		HungrySingleton s1 = HungrySingleton.getInstance();
		HungrySingleton s2 = HungrySingleton.getInstance();
		System.out.println(s1 == s2);
	}
}
