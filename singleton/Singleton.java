/*
 
 Author:     tangz, tangpersonal@163.com
 Date:       Mar 01, 2017
 Name:       单例模式
 
 概念：确保一个类只有一个实例，并且自行实例化并向整个系统提供全局访问点getInstance()，以便使用该实例
 特点：只有一个自己创建的实例，外部可以直接访问，不需要实例化该类的对象
 主要解决：一个全局使用的类频繁地创建与销毁，节省系统资源
 关键代码：构造函数私有化
 优点：
 1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如学院首页页面：只需要一个实例）。 
 2、避免对资源的多重占用（比如文件操作：只许一个操作对象即可）。
 缺点：
 1、不能为接口，静态方法getInstance()不能被继承重写（所以被extend意义不大）；
 2、与单一职责原则冲突，因为自己创造对象及其生命周期；
 3、一个类应该只关心内部逻辑，而不关心外面怎么样来实例化；
 4、高耦合，难以测试。
 应用场景：I/O 操作；与数据库的连接；计数器；实际生活中只能有一个的情况（一个主席）等

 这里介绍最常用三种单例模式：饿汉式、懒汉式（双重校验锁）、登记式/静态内部类
 其他还有枚举方式等
 
 */

package com.tang.dp.singleton;

public class Singleton {

	/*
	 * 
	 * 饿汉式
	 * 
	 * 线程安全，基于 classloder 机制避免了多线程的同步问题
	 * 
	 * 不可延迟加载
	 * 
	 * 优点：没有加锁，执行效率会提高。
	 * 
	 * 缺点：类加载时就初始化，可能会造成内存浪费。
	 * 
	 */	
//	private static Singleton instance = new Singleton();
//
//	private Singleton() {
//	}
//
//	public static Singleton getInstance() {
//		return instance;
//	}
//
//	public void otherMethod() {
//		System.out.println("饿汉式");
//	}
	 

	/*
	 * 
	 * 懒汉式：双重校验锁（DCL，即 double-checked locking）
	 * 
	 * 线程安全
	 * 
	 * 可延迟加载，也可以防止反射调用实例化
	 * 
	 * 优点：在多线程情况下安全。
	 * 
	 * 缺点：执行效率相对较低。
	 * 
	 */
	private static volatile Singleton instance;

	private Singleton() {
		// 防止通过反射调用实例化
		if (instance != null) {
			throw new IllegalStateException("Already initialized.");
		}
	}

	/* 
	 这里两重判断，理由：未实例化之前，同时两个线程进入第一层if； 
	 第一个线程加锁进入第二层if并创建实例，第二个进程等待，此时已经创建好了实例； 
	 然后第二个进程进来加锁，但此时已经有实例，不能再new。所以不能少第二层if，否则第二个进程也会执行new，导致第二个进程再次创建一个实例
	 */
	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}

	public void otherMethod() {
		System.out.println("懒汉式：双重校验锁（DCL，即 double-checked locking）");
	}
	 

	/*
	 * 
	 * 登记式/静态内部类
	 * 
	 * 线程安全
	 * 
	 * 这种方式能达到双检锁方式一样的功效，延迟加载。静态域使用延迟初始化时使用，而双检锁方式可在实例域需要延迟初始化时使用；
	 * 另一方面：如果实例化 instance 很消耗资源，所以想让它延迟加载，而又不希望在 Singleton 类加载时就实例化，可使用它
	 * 
	 * 可延迟加载
	 * 
	 */

//	private Singleton() {
//	}
//	
//	public static Singleton getInstance() {
//		return SingletonHolder.INSTANCE;
//	}
//
//	// 静态内部类
//	private static class SingletonHolder {
//		private static final Singleton INSTANCE = new Singleton();
//	}
//
//	public void otherMethod() {
//		System.out.println("登记式/静态内部类");
//	}
}
