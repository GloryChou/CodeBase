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



### Thread.join()
Thread.join()可以使当前线程等待目标线程结束之后才继续运行。

##### Thread.join()使用范例：
``` java
public static void main(String[] args)
 throws InterruptedException {
	Thread t = new Thread(() -> {
		// 实现run方法
	});
	t.start();
    t.join();
}
```

join()必须放在start()之后执行，因为join()的实现原理实际是：检测到目标线程处于alive状态的时候会调用wait方法来暂停当前线程，直到目标线程终止。



### Java条件变量

**java.util.concurent.looks.Condition**接口可以作为wait/notify的替代品来实现等待/通知，它为解决**过早唤醒**问题提供了支持，并解决了Object.wait(long)不能区分其返回是否是由等待超时而导致的问题。
> **过早唤醒：**当存在多个线程且这些线程的保护条件并不一致时，由于线程被唤醒具有随机性，这就会导致某些保护条件尚未成立的线程被唤醒，继而由于保护条件尚未成立该线程再次进入等待状态，白白浪费了上下文切换的开销。

Condition接口定义的await、signal和signalAll分别相当于wait、notify和notifyAll。

Condition实例必须通过显式锁实例的newCondition()方法来创建。

wait/notify要求其执行线程持有这些方法所属对象的内部锁，类似地，Condition的await/signal也要求其执行线程持有该Condition实例的显式锁。

##### Condition使用范例：
``` java
class ConditionUsage {
	private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void aGuaredMethod()
     throws InterruptedException {
        lock.lock();
        try {
            while (保护条件) {
                condition.await();
            }

            // 执行目标动作
            // xxx
        } finally {
            lock.unlock();
        }
    }

    public void anNotificationMethod()
     throws InterruptedException {
        lock.lock();
        try {
            // 更新共享变量
            // xxx

            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
```

##### Condition解决过早唤醒的问题
同步对象内部维护多个Condition实例：cond1、cond2、......，用以区分保护条件不同的线程同步控制。

##### Condition解决Object.wait(long)是否等待超时的问题
Object.wait(long)既无返回值也不会抛出异常，所以我们无法区分其返回是由其他线程通知了还是由于等待超时。

Condition.awaitUntil(Date deadline)可以解决这一问题。其唯一参数deadline表示等待的最后期限，过了这个**时间点**就算等待超时。当它返回值为true时，就表示进行的等待尚未达到最后期限，即此线程是被其他线程执行了signal/signalAll唤醒的。如果它调用返回false，就表示是由等待超时引起的停止等待。




### 倒计时协调器：CountDownLatch

CountDownLatch可以用来实现一个（或者多个）线程等待其他线程完成一组特定的操作之后才继续运行。这组特定的操作被称为**先决操作**。

#### CountDownLatch实现原理

CountDownLatch内部会维护一个用于表示未完成的先决操作数量的计数器。
**CountDownLatch.countDown()**每被执行一次就会使相应实例的计数器值减1。
**CountDownLatch.await()**相当于一个受保护的方法，其保护条件为“计数器值为0”，此时代表所有先决操作已执行完毕。

当计数器值不为0时，CountDownLatch.await()的执行线程会被暂停，这些线程就被称为相应CountDownLatch上的等待线程。CountDownLatch.countDown()相当于一个通知方法，它会在计数器值达到0的时候唤醒实例上的所有等待线程。

> **TIPS：**当计数器的值达到0之后，该计数器的值就不再发生变化。此时，调用CountDownLatch.countDown()并不会导致异常的抛出，并且后续执行CountDownLatch.await()的线程也不会被暂停。因此，CountDownLatch的使用时**一次性**的：一个CountDownLatch实例只能够实现一次等待和唤醒。

##### CountDownLatch示例：
``` java
public class CountDownLatchExample {
    private static final CountDownLatch latch = 
     new CountDownLatch(4);
    private static int data;

    public static void main(String[] args)
     throws InterruptedException {
        Thread workerThread = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                data = i;
                latch.countDown();
                // 暂停一段时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });
        workerThread.start();
        latch.await();
        System.out.println(
         String.format("It's done. data=%d", data));
    }
}
```
该程序的输出总是如下：It's done. data=4



### 栅栏（CyclicBarrier）
有时候多个线程可能需要**相互等待对方执行到代码中的某个地方（集合点）**，这时这些线程才能够继续执行。JDK1.5开始引入的java.util.concurrent.CyclicBarrier可以用来实现这种等待。

#### CyclicBarrier实现原理
使用CyclicBarrier实现等待的线程被称为**参与方（Party）**。参与方只要执行**CyclicBarrier.await()**就可以实现等待。

CyclicBarrier内部维护了一个显示锁，这使得其总是可以在所有参与方中区分出最后一个执行CyclicBarrier.await()的线程，该线程被称为**最后一个线程**。

除最后一个线程外的任何参与方执行CyclicBarrier.await()都会导致该线程被暂停。最后一个线程执行CyclicBarrier.await()会使得使用相应CyclicBarrier实例的其他所有参与方被唤醒。

CyclicBarrier内部使用了一个条件变量trip来实现等待/通知。CyclicBarrier内部实现了使用分代（Generation）的概念用于表示CyclicBarrier实例是可以重复使用的。除最后一个线程外的任何一个参与方都相当于一个等待线程，这些线程所使用的保护条件是“当前分代内，尚未执行await方法的参与方个数（parties）为0”。当前分代的初始状态是parties，其初始值等于参与方总数（通过构造器中的parties参数指定）。CyclicBarrier.await()每被执行一次会使相应实例的parties值减少1。最后一个线程相当于通知线程，他执行CyclicBarrier.await()会使相应实例的parties值变为0，此时该线程会先执行barrierAction.run()，然后 再执行trip.signalAll()来唤醒所有的等待线程。接着，开始下一个分代，即使得parties的值又重新恢复为其初始值。



### 阻塞队列
从传输通道中存入数据或取出数据时，相应的线程可能因为传输通道中没有数据或者其存储空间已满而挂起，这种传输通道的运作方式称为**阻塞式**。

一般而言，一个方法或者操作如果能够导致其执行线程被挂起，那么我们就称相应的的方法/操作为**阻塞方法**或者**阻塞操作**。

常见的阻塞方法包括：InputStream.read()、ReentrantLock.lock()、申请内部锁等。

JDK1.5中引入的接口java.util.concurrent.BlockingQueue定义了一种线程安全的队列——阻塞队列。

阻塞队列按照其存储空间的容量是否受限制来划分，可分为**有界队列（Bounded Queue）**和**无界队列（Unbounded Queue）**。有界队列的存储容量是由应用程序指定的，无解队列的最大存储容量为Integer.MAX_VALUE（$2^{31} - 1$）。

往队列中存入一个元素的操作被称为**put操作**，从队列中取出一个元素的操作被称为**take操作**。

#### ArrayBlockingQueue
ArrayBlockingQueue内部使用一个数组作为其存储空间，而数组的存储空间是预先分配的，因此ArrayBlockingQueue的put/take操作本身并不会增加垃圾回收的负担。ArrayBlockingQueue的缺点是其内部在实现put/take操作的时候使用的是同一个显示锁，从而导致锁的高争用，进而导致较多的上下文切换。

#### LinkedBlockingQueue
LinkedBlockingQueue既能实现无界队列，也能实现有界队列。LinkedBlockingQueue的其中一个构造函数允许我们创建队列的时候指定队列容量。LinkedBlockingQueue的优点是其内部在实现put/take操作的时候分别使用了两个显示锁（putLock/takeLock），这降低了锁的争用。LinkedBlockingQueue的内部存储空间是一个链表，而链表节点所需的存储空间是动态分配的，因此LinkedBlockingQueue的缺点是它可能增加垃圾回收的负担。此外，由于put/take操作使用的是两个锁，LinkedBlockingQueue维护其队列的当前长度（Size）时无法使用一个普通的int型变量，而是使用了原子变量AtomicInteger，这也会导致额外的开销。

#### SynchronousQueue
SynchronousQueue可以看成一种特殊的有界队列。其内部并不维护用户存储队列元素的存储空间。



### 信号量：Semaphore
信号量(Semaphore)，有时被称为信号灯，是在多线程环境下使用的一种设施，是可以用来保证两个或多个关键代码段不被并发调用。在进入一个关键代码段之前，线程必须获取一个信号量；一旦该关键代码段完成了，那么该线程必须释放信号量。其它想进入该关键代码段的线程必须等待直到第一个线程释放信号量。

JDK1.5中引入的标准类库java.util.concurrent.Semaphore可以用来实现信号量控制。Semaphore.acquire()/release()分别用于申请信号量和释放信号量。

>**TIPS：**
> + acquire()和release()总是配对使用；
> + release()调用总是应该放在一个finally块中，以避免程序执行发生异常时当前申请的信号量无法释放；
> + 创建Semaphore实例时，如果构造器中参数permits值为1，那么所创建的Semaphore实例相当于一个互斥锁。与其他互斥锁不同的是：由于一个线程可以在未执行过Semaphore.acquire()的情况下执行相应的Semaphore.release()，因此这种互斥锁允许一个线程释放另外一个线程锁持有的锁；
> + 默认情况下，Semaphore采用的是非公平性调度策略。



### 管道：PipedOutputStream/PipedInputStream
PipedOutputStream/PipedInputStream分别是OutputStream/InputStream的一个子类，它们可以用来实现线程间的直接输出和输入。

所谓的“直接”是指从应用代码的角度来看，一个线程的输出可作为另外一个线程的输入，而不必借用文件、数据库、网络连接等其他数据交换中介。

PipedOutputStream相当于生产者，其生产的产品是字节形式的数据；PipedInputStream相当于消费者，内部使用byte型数组维护了一个循环缓冲区（Cirular Buffer），这个缓冲区相当于传输通道。

在PipedOutputStream/PipedInputStream进行输入、输出操作前，PipedOutputStream实例和PipedInputStream实例需要建立起关联（Connect）。两者之间可以通过调用各自实例的connect方法实现关联，也可以通过在创建相应实例的时候将对方的实例指定为自己的构造器参数来实现。



### 双缓冲：Exchanger
缓冲（Buffering）是一种常用的数据传递技术。缓冲区相当于数据源（Source）与数据使用方（Sink）之间的数据容器。

在多线程环境下，有时候我们会使用两个（或者更多）缓冲区来实现数据从数据源到数据使用方的移动。其中一个缓冲区填充来自数据源的数据后可以被数据使用方进行“消费”，而另外一个空的（或者是已经取完数据的）缓冲区则用来填充来自数据源的新的数据。

数据源与数据使用方通过轮次访问缓存区，实现了数据生成与消费的并发。这种缓冲技术就被称为**双缓冲**。

JDK1.5中引入的标准类库java.util.concurrent.Exchanger可以用来实现双缓冲。