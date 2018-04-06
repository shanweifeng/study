1 解释下你所理解的Happens-before含义和JVM里的几个Happens-before约定
    下面是happens-before原则规则：
    程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；
    锁定规则：一个unLock操作先行发生于后面对同一个锁额lock操作；
    volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作；
    传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C；
    线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作；
    线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生；
    线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行；
    对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始；

    程序次序规则：一段代码在单线程中执行的结果是有序的。注意是执行结果，因为虚拟机、处理器会对指令进行重排序（重排序后面会详细介绍）。虽然重排序了，但是并不会影响程序的执行结果，所以程序最终执行的结果与顺序执行的结果是一致的。故而这个规则只对单线程有效，在多线程环境下无法保证正确性。
    锁定规则：这个规则比较好理解，无论是在单线程环境还是多线程环境，一个锁处于被锁定状态，那么必须先执行unlock操作后面才能进行lock操作。
    volatile变量规则：这是一条比较重要的规则，它标志着volatile保证了线程可见性。通俗点讲就是如果一个线程先去写一个volatile变量，然后一个线程去读这个变量，那么这个写操作一定是happens-before读操作的。
    传递规则：提现了happens-before原则具有传递性，即A happens-before B , B happens-before C，那么A happens-before C
    线程启动规则：假定线程A在执行过程中，通过执行ThreadB.start()来启动线程B，那么线程A对共享变量的修改在接下来线程B开始执行后确保对线程B可见。
    线程终结规则：假定线程A在执行的过程中，通过制定ThreadB.join()等待线程B终止，那么线程B在终止之前对共享变量的修改在线程A等待返回后可见。
    上面八条是原生Java满足Happens-before关系的规则，但是我们可以对他们进行推导出其他满足happens-before的规则：
        将一个元素放入一个线程安全的队列的操作Happens-Before从队列中取出这个元素的操作
        将一个元素放入一个线程安全容器的操作Happens-Before从容器中取出这个元素的操作
        在CountDownLatch上的倒数操作Happens-Before CountDownLatch#await()操作
        释放Semaphore许可的操作Happens-Before获得许可操作
        Future表示的任务的所有操作Happens-Before Future#get()操作
        向Executor提交一个Runnable或Callable的操作Happens-Before任务开始执行操作
    这里再说一遍happens-before的概念：如果两个操作不存在上述（前面8条 + 后面6条）任一一个happens-before规则，那么这两个操作就没有顺序的保障，JVM可以对这两个操作进行重排序。如果操作A happens-before操作B，那么操作A在内存上所做的操作对操作B都是可见的。

volatile 与final是在1.5中重新修改了语义的，在构造方法中初始化的final变量(前提没有this引用泄露)在其他线程中是可见的。
参见:
http://ifeve.com/easy-happens-before/
http://www.iteye.com/topic/260515
http://cmsblogs.com/?p=2122  http://cmsblogs.com/?p=2102
https://blog.csdn.net/ns_code/article/details/17348313等文章
2 不依赖任何的同步机制（syncronized ,lock），有几种方式能实现多个线程共享变量之间的happens-before方式

3 编程验证normal var ,volaitle，synchronize,atomicLong ,LongAdder，这几种做法实现的计数器方法，在多线程情况下的性能，准确度
详情见程序
    class MyCounter
    {
          private long value;//根据需要进行替换
          public void incr();
          public long getCurValue();//得到最后结果
    }
 启动10个线程一起执行，每个线程调用incr() 100万次，
所有线程结束后，打印 getCurValue()的结果，分析程序的结果 并作出解释。 用Stream和函数式编程实现则加分！

选做题：

 1 自己画出Java内存模型并解释各个区，以JDK8为例，每个区的控制参数也给出。
 2 解释为什么会有数组的Atomic类型的对象
