/**
 *@Description 指令重排序 instruction reorder
 *@Author 宝子哥
 *@Date 2021/7/15 13:39
 *@Version 1.0
 **/

package top.caolizhi.example.juc.reorder;

public class PossibleReordering {

	static int x = 0, y = 0;
	static int a = 0, b = 0;


	private static void instructionReorder() throws InterruptedException {
		for (int i = 0; ; i++) {
			Thread one = new Thread(new Runnable() {
				@Override
				public void run() {
					a = 1;
					x = b;
				}
			});

			Thread other = new Thread(new Runnable() {
				@Override
				public void run() {
					b = 1;
					y = a;
				}
			});

			one.start();
			other.start();
			one.join();
			other.join();

			/**
			 *   线程 one  ----> x = b (0)————————重排序—————————> a = 1
			 *   线程 other --------------> b = 1 ---> y = a (0)
			 */
			if (x == 0 && y == 0) {
				System.out.println("第" + i + "次 (" + x + ", " + y + ")");
			}
		}




	}

	public static void main(String[] args) throws InterruptedException {
		instructionReorder();
	}

}
