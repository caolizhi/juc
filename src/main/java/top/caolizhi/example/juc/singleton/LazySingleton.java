/**
 *@Description 最简单的 单例类
 * 懒汉式
 * 如果是 null 才会去加载示例
 *@Author 宝子哥
 *@Date 2021/7/19 15:04
 *@Version 1.0
 **/

package top.caolizhi.example.juc.singleton;

public class LazySingleton {

	/**
	 * 只有我自己能够 new，其他任务法访问
	 */
	private static LazySingleton INSTANCE;

	public static LazySingleton getInstance() {
		if (INSTANCE == null) {
			return INSTANCE = new LazySingleton();
		}
		return INSTANCE;
	}

	public static void main(String[] args) {
		LazySingleton s1 = LazySingleton.getInstance();
		LazySingleton s2 = LazySingleton.getInstance();
		System.out.println(s1 == s2);
	}
}
