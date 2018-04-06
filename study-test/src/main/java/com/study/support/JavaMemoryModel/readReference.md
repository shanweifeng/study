堆区:
1.存储的全部是对象，每个对象都包含一个与之对应的class的信息。(class的目的是得到操作指令)
2.jvm只有一个堆区(heap)被所有线程共享，堆中不存放基本类型和对象引用，只存放对象本身
栈区:
1.每个线程包含一个栈区，栈中只保存基础数据类型的对象和自定义对象的引用(不是对象)，对象都存放在堆区中
2.每个栈中的数据(原始类型和对象引用)都是私有的，其他栈不能访问。
3.栈分为3个部分：基本类型变量区、执行环境上下文、操作指令区(存放操作指令)。
方法区:
1.又叫静态区，跟堆一样，被所有的线程共享。方法区包含所有的class和static变量。
2.方法区中包含的都是在整个程序中永远唯一的元素，如class，static变量。




JDK中关于fences与Volatile的内容
http://hg.openjdk.java.net/jdk7/jdk7/hotspot/file/4fc084dac61e/src/os_cpu/linux_x86/vm/orderAccess_linux_x86.inline.hpp
// Implementation of class OrderAccess.

inline void OrderAccess::loadload()   { acquire(); }
inline void OrderAccess::storestore() { release(); }
inline void OrderAccess::loadstore()  { acquire(); }
inline void OrderAccess::storeload()  { fence(); }

我们看到storeload对应是fence()，采用的是lock addl的指令
inline void OrderAccess::fence() {
  if (os::is_MP()) {
    // always use locked addl since mfence is sometimes expensive
#ifdef AMD64
    __asm__ volatile ("lock; addl $0,0(%%rsp)" : : : "cc", "memory");
#else
    __asm__ volatile ("lock; addl $0,0(%%esp)" : : : "cc", "memory");
#endif
  }
}

loadload与loadstore对应的是acquire()
inline void OrderAccess::acquire() {
  volatile intptr_t local_dummy;
#ifdef AMD64
  __asm__ volatile ("movq 0(%%rsp), %0" : "=r" (local_dummy) : : "memory");
#else
  __asm__ volatile ("movl 0(%%esp),%0" : "=r" (local_dummy) : : "memory");
#endif // AMD64
}
上述代码解释如下：
在栈里做了一个加载本地局部变量的操作，然后：
“memory” 告诉编译器，汇编代码中显式使用内存地址/全局变量访问了内存，执行该段汇编之后，所有之前的寄存器需要重新加载。

storetore对应的release()
inline void OrderAccess::release() {
  // Avoid hitting the same cache-line from
  // different threads.
  volatile jint local_dummy = 0;
}


movl %0,%%eax "  :"=m" (a)
        :"m" (b)