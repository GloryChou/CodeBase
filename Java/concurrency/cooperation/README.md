# Java线程间协作

### 等待与通知：wait/notify

● 一个线程因其执行目标动作所需的保护条件未满足而被暂停的过程就被称为**等待（wait）**

● 一个线程更新了系统的状态，使得其他线程所需的保护条件得以满足的时候唤醒那些被暂停的线程的过程就被称为**通知（notify）**

##### 使用Object.wait()实现等待：
``` java
synchronized(someObject) {
	while(保护条件不成立) {
		// 暂停当前线程
		someObject.wait();
	}

	// 执行目标动作
	doAction();
}
```

**使用时的注意事项：**
1、一个线程只有在持有一个对象的内部锁的情况下才能够调用该对象的wait方法，否则按照wait方法的实现会抛出IllegalMonitorStateException异常，因此**wait的调用总是放在相应对象所引导的临界区之中**；

2、考虑到等待线程在其被唤醒、继续运行到其再次持有相应对象的内部锁的这段时间内，由于其他线程可能抢先获得相应的内部锁并更新了相关共享变量而导致该线程所需的保护条件又再次不成立，因此Object.wait()调用返回之后我们需要再次判断此时保护条件是否成立，故而此处保护条件的判断放在while循环的判断语句中。

##### 使用Object.notify()实现通知：
``` java
synchronized(someObject) {
	// 更新等待线程的保护条件涉及的共享变量
	updateSharedState();
	// 唤醒其他线程
	someObject.notify();
}
```

通知方法包含两个要素：**更新共享变量**、**唤醒其他线程**。
与wait方法一样，**notify的调用必须放在相应对象内部锁所引导的临界区之中**。

#### wait与notify之间的协作
假设someObject为Java中任意一个类的实例。someObject.wait()会以原子操作的方式使其执行线程暂停，并使该线程**释放其持有的对应的内部锁**。

someObject.notify()可以唤醒someObject上的一个**任意的**等待线程。被唤醒的等待线程在其占用处理器继续运行的时候，需要再次申请someObject对应的内部锁。被唤醒的线程在其再次持有someObject对应的内部锁的情况下继续执行someObject.wait()中剩余的指令，直到wait方法返回。

someObject.notify()的执行线程持有的相应对象的内部锁只有在notify调用所在的临界区代码执行结束后才会被释放，notify本身并不会将这个内部锁释放。因此，为了使等待线程在其被唤醒之后能尽快再次获得相应的内部锁，我们**要尽可能地将someObject.notify()调用放在靠近临界区结束的地方**。

#### notifyAll()
Object.notify()所唤醒的仅是相应对象上的一个任意等待线程，所以这个唤醒的线程可能不是我们真正想要唤醒的那个线程。所以可以用Object.notifyAll()唤醒相应对象上的所有等待线程。

