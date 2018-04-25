package com.study.support.JavaMemoryModel;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

/**
 * @Author shanweifeng
 * @Description: 编程验证normal var ,volaitle，synchronize,atomicLong ,LongAdder，这几种做法实现的计数器方法，在多线程情况下的性能，准确度
 * @Date: Created in 14:08 2018/4/6
 * @Modified By:
 */
public class MyCounterTest {
    static Integer i=0;
    static List<Integer> l = new ArrayList<>();
    public static void main(String[] args){
        /*CountInterface mc = new MyCounter();
        countMethod(mc);
        mc = new VolatileCount();
        countMethod(mc);
        mc = new SynchronizedCount();
        countMethod(mc);
        mc = new AtomicCount();
        countMethod(mc);
        mc = new AdderCount();
        countMethod(mc);*/

        new Thread(()->{
            synchronized (MyCounterTest.class)
            {
                try {
                    l.wait();
                    for(;i<10;i++)
                    {
                        System.out.println("class:"+Thread.currentThread()+" --> "+i);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        },"class").start();
        MyCounterTest t = new MyCounterTest();
        new Thread(()->{
            synchronized (t)
            {
                try {
                    l.notify();
                    /*for(;i<10;i++)
                    {
                        System.out.println("object:"+Thread.currentThread()+" --> "+i);
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"object").start();
        //启动10个线程一起执行，每个线程调用incr() 100万次， 所有线程结束后，打印 getCurValue()的结果，分析程序的结果 并作出解释。 用Stream和函数式编程实现则加分！
    }
    private static void countMethod( CountInterface mc){
        Instant start = Instant.now();
        IntStream.range(0,10).boxed().map(i->{
            Thread t = new Thread(()->{
                IntStream.range(0,100_0000).forEach(num->mc.increment());});
            t.start();
            return t;
        }).collect(Collectors.toList()).forEach(t->{
            try {
                t.join();//等待线程终止
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mc+";cost "+ Duration.between(start,Instant.now()).toMillis());
    }
}

interface CountInterface{
    void increment();
    long getCurValue();
}

class MyCounter implements CountInterface{
    private long value;//根据需要进行替换
    @Override
    public void increment(){
        value++;
    }
    @Override
    public long getCurValue(){//得到最后结果
        return value;
    }
    @Override
    public String toString()
    {
        return "normal count:"+this.getCurValue();
    }
}

class VolatileCount implements CountInterface{
    private volatile long value;//根据需要进行替换
    @Override
    public void increment() {
        value++;
    }

    @Override
    public long getCurValue() {
        return value;
    }
    @Override
    public String toString()
    {
        return "volatile count:"+this.getCurValue();
    }
}

class SynchronizedCount implements CountInterface{
    private long value;
    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public long getCurValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return "synchronized count:"+this.getCurValue();
    }
}

class AdderCount implements CountInterface{
    private LongAdder value = new LongAdder();

    @Override
    public void increment() {
        value.increment();
    }

    @Override
    public long getCurValue() {
        return value.longValue();
    }

    @Override
    public String toString()
    {
        return "LongAdder count:"+this.getCurValue();
    }
}

class AtomicCount implements CountInterface{
    private AtomicLong value = new AtomicLong();
    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public long getCurValue() {
        return value.longValue();
    }
    @Override
    public String toString()
    {
        return "AtomicLong count:"+this.getCurValue();
    }
}