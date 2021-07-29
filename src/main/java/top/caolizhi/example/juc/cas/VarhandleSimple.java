/**
 *@Description {@link java.lang.invoke.VarHandle VarHandle}
 * VarHandle 定义了很多原子操作，也可以用来反射操作变量，反射需要检查，而 VarHandle 不需要，可以理解为直接操作二进制码
 *@Author 宝子哥
 *@Date 2021/7/29 8:55
 *@Version 1.0
 **/
package top.caolizhi.example.juc.cas;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarhandleSimple {

	int x = 8;
	static VarHandle varHandle;

	static {
		try {
			varHandle = MethodHandles.lookup().findVarHandle(VarhandleSimple.class, "x", int.class);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		VarhandleSimple varhandleSimple = new VarhandleSimple();
		System.out.println((int)varHandle.get(varhandleSimple));
		varHandle.set(varhandleSimple,9);
		System.out.println(varhandleSimple.x);
		varHandle.compareAndSet(varhandleSimple, 9, 10);
		System.out.println(varhandleSimple.x);
		varHandle.getAndAdd(varhandleSimple, 10);
		System.out.println(varhandleSimple.x);
	}
}
