1 解释下你所理解的Happens-before含义和JVM里的几个Happens-before约定
2 不依赖任何的同步机制（syncronized ,lock），有几种方式能实现多个线程共享变量之间的happens-before方式
3 编程验证normal var ,volaitle，synchronize,atomicLong ,LongAdder，这几种做法实现的计数器方法，在多线程情况下的性能，准确度

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
