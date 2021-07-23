# juc
learn juc

### 锁升级过程图解

![synchronized_update](images/synchronized_update.png)

### 原子类源码分析 CAS
> 以 AtomicInteger 为例。

在使用 `AtomicInteger` 类时，加 1 操作会调用方法 `incrementAndGet()`,这个方法就是 CAS 的实现。
先瞥一眼这个方法的内容， 实际上就是拿到 value 的值，然后再执行加 1 的操作。
```java
public class AtomicInteger extends Number implements java.io.Serializable {

	private static final jdk.internal.misc.Unsafe U = jdk.internal.misc.Unsafe.getUnsafe();
      /**
        * Unsafe 类直接操作内存
        * 这个 VALUE 的值就是 AtomicInteger 类 value 的内存位置地址的偏移量，
        * 通过 native 方法，拿到地址指针
	*/
	private static final long VALUE = U.objectFieldOffset(AtomicInteger.class, "value");

	private volatile int value;
	
	public final int incrementAndGet() {
		return U.getAndAddInt(this, VALUE, 1) + 1;
	}
	// 省略其他方法
}
```
再跟踪到 `getAndAddInt()` 方法中，进到了 `Unsafe` 类中，如下：
```java
public final class Unsafe {
	...
	@HotSpotIntrinsicCandidate
	public final int getAndAddInt(Object o, long offset, int delta) {
		int v;
		do {
			v = getIntVolatile(o, offset);
		} while (!weakCompareAndSetInt(o, offset, v, v + delta));
		return v;
	}
	...
}
```
这个就是 CAS 的核心实现，流程如下：
1. do 里面，先去拿 this 对象在偏移量为 offset 的值，v 取到的是 0；
2. 然后 while 中 `weakCompareAndSetInt()` 方法再去判断这个偏移量位置上是不是 0，也就是期望的应该是0，
  - 2.1 如果是 0，就把 0 + 1 = 1 写进去；
  - 2.2 如果不是 0，说明在取到 0 后，有其他线程修改了这个 offset 位置的值，比如 1
  - 2.3 然后又执行一次 do 里面的操作，拿到 v = 1，再去看看这个 offset 位置上的值是不是1，是就加上1，不是继续 do..while 操作
  - 2.4 成功了就结束。
3. 然后返回了 v，注意，这个 v 的值还是刚才取到的 0，并没有重新去取。

这里有个问题，有没有可能这样的情况出现，do 里面取到了是 0，然后比较了当前的位置确实是 0 ，然后再写的过程值
又发生了变化呢？ 答案是不可能。  
我们进到 `weakCompareAndSetObject(Object o, long offset,Object expected,Object x)` 方法
里面看一下源代码：
```java
public final class Unsafe {
    @HotSpotIntrinsicCandidate
    public final boolean weakCompareAndSetObject(Object o, long offset,
                                                 Object expected,
	                                             Object x) {
        return compareAndSetObject(o, offset, expected, x);
	}

    /**
     * 如果当前持有expected则以原子方式将 Java 变量更新为x
     * 此操作具有volatile读写的内存语义。 对应于 C11 atomic_compare_exchange_strong。
     * 返回：如果成功则为true
     */
    @HotSpotIntrinsicCandidate
    public final native boolean compareAndSetInt(Object o, long offset,
	                                        int expected,
	                                        int x);

}
```
我们可以看到注释里面 `atomic_compare_exchange_strong` 函数，这是 C11 原子的函数，反映到处理器的指令上
就是 `CMPXCHG` 指令，这条指令已经无法再分割，而这条指令执行的时候，通过锁总线来让其他核心处理器都不能访问这个地址。
简单来说，从 CPU 原语的级别来保证了 CAS 的操作。

#### CAS 的 ABA
当然 CAS 属于无锁优化，也可以说是乐观锁，但是存在 ABA 的问题，即当前线程取回的值和期望的是一致的，但是中间某个线程操作了这个值，然后又恢复了这个值
，并且不会影响当前线程的后续操作，实际上是发生了改变，但是 ABA 可以分类型来考虑。
  - 如果是基础类型，那么改变的值不影响当前线程要用的值。
  - 如果是引用类型的话，那么在某些场景下会影响结果，就好比你的女朋友和你分手又复合了，但是中间经历了别的男人。
如何解决 ABA 的问题呢，**可以加一个版本标记，任何一次修改，就会修改这个版本标记，检查的时候版本标记也要一起检查。**

### JUC 类总结  
  - ReentrantLock 比 synchronized 更灵活，更方便，lock() 和 unlock() 配对使用；
  - CountDownLatch 倒计时为 0，门栓打开，countDown() 和 await() 配对使用；
  - CycliBarrier  栅栏，循环使用， 人满，栅栏放行，定义 runnable 推倒之后的操作和 await() 使用；
  - Phaser 分阶段的栅栏，继承 Phaser 重写 onAdvance()，arriveAndAwaitAdvance() 到了注册 和 arriveAndDeregister() 走了注销
  - Semaphore 限流，acquire() 和 release() 配对使用
  - Exchanger 两个线程之间相互交换数据，定义交换类型，调用 exchange(T) 方法交换数据；

### Synchronized 和 ReentrantLock 的区别
  - Synchronized 是系统自带的，系统自动加锁，自动解锁，默认进行四种锁状态的升级， wait() 和 notify() 必须用在 synchronized 代码块中，用于多线程协调运行，必须是在有锁的对象上使用。（Lost wake-up problem）
  - ReentrantLock 需要手动加锁，手动解锁，CAS 的实现。
