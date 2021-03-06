# 保障线程安全的设计技术

### 无状态对象
**对象**就是操作和数据的封装。对象所包含的数据就被称为该对象的**状态**，它包括存储在实例变量或者静态变量之中的数据。相应地，实例变量、静态变量也被称为**状态变量**。

如果一个类的同一个实例被多个线程共享并不会使得这些线程存在共享状态，那么这个类及其任意一个实例就被称为**无状态对象**。

反之，如果一个类的同一个实例被多个线程共享，会使这些线程存在共享状态，那么这个类及其任意一个实例就被称为**有状态对象**。

所谓的**无状态对象**就是这样一种办法：一个线程执行无状态对象的任意一个方法来完成某个计算的时候，该计算的瞬时状态（中间结果）仅体现在局部变量或只有当前执行的线程能够访问的对象的状态上。

因此，一个线程执行无状态对象的任何方法都不会对访问该无状态对象的其他线程产生任何干扰作用。这些线程在执行该对象的任何方法的时候都无须使用同步机制。



### 不可变对象
**不可变对象**是指一经创建其状态就保持不变的对象。

一个严格意义上的不可变对象要同时满足以下所有条件：
+ 类本身使用**final**修饰：这是为了防止通过创建子类来改变其定义的行为。
+ 所有字段都是用**final**修饰：在多线程环境下保证了被修饰字段的初始化安全，即final修饰的字段在对其他线程可见时，它必定是初始化完成的。
+ 对象在初始化过程中没有逃逸：防止其他类（如该类的内部匿名类）在对象初始化过程中修改其状态。
+ 任何字段若引用了其他状态可变的对象（如集合、数组等），则这些字段必须是**private**修饰的，并且这些字段值不能对外暴露。



### 线程特有对象：ThreadLocal&lt;T&gt;
对于一个非线程安全对象，每个线程都创建一个该对象的实例，各个线程仅访问各自创建的实例，且一个线程不能访问另外一个线程创建的对象实例。这种对象我们称之为：**线程特有对象（Thread Specific Object，TSO）**，相应的线程就被称为该线程特有对象的**持有线程**。

**ThreadLocal&lt;T&gt;**类相当于线程访问其线程特有对象的代理（Proxy），即各个线程通过这个对象可以创建并访问各自的线程特有对象，其类型参数T指定了相应线程特有对象的类型。

#### 线程特有对象可能导致的问题及其规避
+ **退化与数据错乱：**由于线程和任务之间可以是一对多的关系，即一个线程可以先后执行多个任务，因此线程特有对象就相当于一个线程所执行的多个任务之间的共享对象。如果线程对象是个有状态的对象且其状态会随着相应线程所执行的任务而改变，那么这个线程所执行的下一个任务可能“看到”来自前一个任务的数据，而这个数据可能与该任务并不匹配，从而导致数据错乱。
> **解决方案：**需要确保每个任务的处理逻辑被执行前相应的线程特有对象不受前一个任务的影响，我们可以通过在任务处理逻辑开始前为线程局部变量重新关联一个线程特有对象或者重置线程特有对象的状态来实现。

例如：
```java
public abstract class XAbstractTask implements Runnable {
    static ThreadLocal<HashMap<String, String>> configHolder = new ThreadLocal<HashMap<String, String>>() { 
        protected HashMap<String, String> initialValue() {
            return new HashMap<String, String>();
        }
    };
    
    // 任务逻辑开始前执行的方法
    protected void preRun() {
        // 清空线程特有对象实例
        configHolder.get().clear();
    }

    // 任务逻辑完毕后执行的方法
    protected void postRun() {
        // TODO:
    }
    
    // 任务逻辑所在的方法
    protected abstract void doRun();
    
    @Override
    public final void run(){
        try {
            preRun();
            doRun();
        } finally{
            postRun();
        }
    }
}
```
+ **ThreadLocal可能导致内存泄漏、伪内存泄漏：**在WEB应用中使用ThreadLocal极易导致内存泄漏、伪内存泄漏。这与ThreadLocal的内部实现机制有关。
> **ThreadLocal内部实现机制：**
> 在JAVA平台中，每个线程内部会维护一个类似HashMap的对象，我们称之为ThreadLocalMap。每个ThreadLocalMap内会包含若干Entry（条目，一个Key-Value对）。因此可以说每个线程都拥有若干这样的条目，相应的线程就被称为这些条目的主线程。Entry的Key是一个ThreadLocal实例，Value是一个线程特有对象。因此，Entry的作用相当于为其属主线程建立起一个ThreadLocal实例与一个线程特有对象之间的对应关系。由于Entry对ThreadLocal实例的引用是一个弱引用，因此它不会阻止被引用的ThreadLocal实例被垃圾收回。当一个ThreadLocal实例没有对其可达的强引用时，这个实例可以被垃圾回收，即其所在的Entry的Key会被置为null。此时，相应的Entry就成为无效条目。
> 另一方面，由于Entry的Key对线程特有对象的引用是强引用，因此如果无效条目本身有对它的可达强引用，那么无效条目也会阻止其引用的线程特有对象被垃圾回收。
> 有鉴于此，当ThreadLocalMap中有新的ThreadLocal到线程特有对象的映射关系被创建的时候，ThreadLocalMap会将无效条目清理掉，这打破了无效条目对线程特有对象的强引用，从而使相应线程特有对象能够被垃圾回收。
> 但是这个初一也有一个缺点，一个线程访问过线程局部变量之后，如果该线程有对其可达的强引用，并且该线程在相当长时间内处于非运行状态，那么该线程的ThreadLocalMap可能就不会有任何变化，因此相应的ThreadLocalMap中的无效条目也不会被清理，这就可能导致这些线程的各个Entry所引用的线程特有对象都无法被垃圾回收，即导致了伪内存泄漏。


以下示例展示了一个ThreadLocal可能导致内存泄漏的Servlet：
```java
public class ThreadLocalMemoryLeak extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final static ThreadLocal<Counter> counterHolder = new ThreadLocal<Counter>() {
        @Override
        protected Counter initialValue() {
            Counter tsoCounter = new Counter();
            return tsoCounter;
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
        try (PrintWriter pwr = resp.getWriter()) {
            pwr.printf("Thread %s, counter:%d", Thread.currentThread().getName(), counterHolder.get().getAndIncrement());
        }
    }

    void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counterHolder.get().getAndIncrement();
        // TODO: 省略其他代码
    }

    // 非线程安全
    class Counter {
        private int i = 0;

        public int getAndIncrement() {
            return i++;
        }
    }
}
```
分析这段代码，可看到，由Web应用自身定义的线程特有对象持有对线程局部变量的可达引用，并且线程又持有对其线程特有对象的可达引用。所以，只要系统中还存在对这个线程对象的可达强引用，即线程本身没有被垃圾回收掉，那么这个线程访问过的所有线程局部变量以及相应的线程特有对象都不会被垃圾回收掉。由于Tomcat中的一个工作者线程（负责调用Servlet.service方法进行请求处理，service方法最终会调用doXXX方法）可以为多个Web应用服务，因此当ThreadLocalMemoryLeak所在的Web应用被停止的时候（不是Web服务器被停止）执行过ThreadLocalMemoryLeak.service方法的工作者线程并不会被停止，故而这些线程对象不会被垃圾回收掉，进而使其所引用的所有线程局部变量及相应的线程特有对象并不会被垃圾回收掉，即导致了内存泄漏。进一步来说，此时的内存泄漏还会导致与当前Web应用相应的类加载器WebAppClassLoader所加载的所有类（以及这些类的静态变量所引用的对象）都无法被垃圾回收，而这最终可能导致Java虚拟机的非堆内存空间中的永久代（Java8中元数据空间）内存溢出。



### 并发集合
从JDK1.5开始，java.util.concurrent包中引入了一些线程安全的集合对象，它们被称为**并发集合**。这些对象通常可以作为同步集合的替代品，它们与常用的非线程安全集合对象之间的对应关系如表所示：

非线程安全对象 | 并发集合类 | 共同接口 | 遍历实现方式
:-: | :-: | :-: | :-:
ArrayList | CopyOnWriteArrayList | List | 快照
LinkedList | ConcurrentLinkedQueue | Queue | 准实时
HashSet | CopyOnWriteArrayList | Set | 快照
HashMap | ConcurrentHashMap | Map | 准实时
TreeSet | ConcurrentSkipListSet | SortedSet | 准实时
TreeMap | ConcurrentSkipListMap | SortedMap | 准实时

并发集合对象自身就支持对其进行线程安全的遍历操作。

并发集合实现线程安全的遍历通常有两种方式：
+ **快照（Snapshot）：**创建对象内部结构的一个只读副本，它反映了待遍历集合某一时刻的状态（即Iterator实例被创建的那一刻）的状态（不包括集合元素的状态）。由于对同一个并发集合进行遍历操作的每个线程会得到各自的一份快照，因此快照相当于这些线程的特有对象。所以，这种方式下进行遍历操作的线程无须加锁就可以实现线程安全。由于快照是只读的，因此这种遍历方式所返回的Iterator实例是不支持remove方法的。
+ **准实时：**遍历操作不针对待遍历对象的副本进行，也不借助锁来保障线程安全，从而使得遍历操作可以与更新操作并发进行。这种遍历方式所返回的Iterator实例可以支持remove方法。

#### 并发集合对比同步集合优势
并发集合内部通常不借助锁，而是使用CAS操作，或者对锁的使用进行了优化，比如使用粒度极小的锁。因此并发集合的可伸缩性一般比同步集合高，即：使用并发集合的程序相比于使用相应同步集合的程序而言，并发线程数的增加带来的程序的吞吐率的提升要更加显著。