1 解释为什么下面放入会失败
	SynchronousQueue<String> queue=new SynchronousQueue();
	if(queue.offer("S1"))
	{
		System.out.println("scucess");
	}else
	{
		System.out.println("faield");
	}
	这是一个插入操作必须等待另一个线程的删除操作的阻塞队列，反之删除操作也需要等待另一个线程的插入操作。 SynchronousQueue 没有任何的中间存储。
    根据API中SynchronousQueue 方法offer的解释:Insert the  specified element into this queue,if another thread is waiting to receive it. 如果有另一个线程正在等待接收它(或者在插入元素的等待时间内)，那么将指定元素插入到这个队列中。

    获取队列中的元素：
    Poll: Retrieves and removes the head of this queue,if another thread is currently making an element available.如果有其他线程正在往队列中放入元素（或者在指定等待获取时间内放入元素），则该方法将获取并删除队列头部元素。

    题目中SynchronousQueue队列只有一个offer()放入元素的方法调用，没有获取元素方法的调用，导致元素放入失败.

2  用线程池框架或者fork-jion框架实现一个并发的文件内容查找接口：
    public SearchResult searchInFiles(String key);
    查询指定目录下的所有txt或者java文件（建议查找Java工程文件）
    目录递归最多为4层，即从根目录开始，最多3层子目录中的文件搜索
    每个文件中如果出现关键字，则关键字的次数+1，并且将此文件的路径也保持到List中
    文件中出现关键字最多次的文件排名第一，以此类推：
    屏幕最后输出：
    xxx总共出现N次，
    其中 2次出现在yyy文件中
         3次出现在xxx文件中


加分题
1 用fork-jion框架实现第二课第四题的编程计算，把握分割任务的粒度。

第二课第四题：随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
   然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
         wx, 200万，10人
         lt, 180万，8人