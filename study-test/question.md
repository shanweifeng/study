#question
https://github.com/aliyun/aliyun-openapi-java-sdk 阿里云sdk
https://github.com/hedengcheng/tech 阿里数据库何登成  http://hedengcheng.com/
https://www.ibm.com/developerworks/cn/java/j-java-streams-2-brian-goetz/index.html IBMjava8 stream等新特性解说
code project:https://www.codeproject.com/Articles/7335/An-extensible-math-expression-parser-with-plug-ins
https://www.codeproject.com/
http://dubbo.io/books/dubbo-user-book/references/xml/dubbo-consumer.html dubbo

https://mp.weixin.qq.com/s?__biz=MzI4NTkzODY0Mg==&mid=2247483676&idx=1&sn=548bf14c1fc00abd668cd3d50ee15890&scene=21#wechat_redirect 大数据学习路线
https://blog.csdn.net/prestigeding/article/details/70247486 源码分析mycat1.6之网络篇----前后端连接交互设计(mycat命令处理流程)
https://blog.csdn.net/prestigeding/article/details/78885420 源码分析RocketMQ消息消费机制----消费者拉取消息机制  丁威
https://mp.weixin.qq.com/s?__biz=MjM5NjQ5MTI5OA==&mid=2651747755&idx=1&sn=fe6288c9ce334ad88d89d16fbfe5f045&chksm=bd12ace68a6525f0086730111d9e0ca949fdc578f3cc8f2e7bebfd1873a55793ffa7b6eb84d5&mpshare=1&scene=23&srcid=0322agwptFDqfObDx5b2uxGe#rd
每天数百亿用户行为数据，美团点评怎么实现秒级转化分析？
https://www.cnblogs.com/yy-cxd/p/6553624.html一分钟看懂Docker的网络模式和跨主机通信
https://software.intel.com/zh-cn/articles/32067/ 《英特尔多核/多线程技术》
https://mbd.baidu.com/newspage/data/landingshare?pageType=1&isBdboxFrom=1&context=%7B%22nid%22%3A%22news_10608812638576692966%22%2C%22sourceFrom%22%3A%22bjh%22%7D
付费内容体系：一份组织内容的三层模型

spring 事物 mysql事物

https://github.com/weberhuang/Interview-Notebook

http://cmsblogs.com/?p=2122  java并发

MaxScale：实现MySQL读写分离与负载均衡的中间件利器
DCL(Double Check Lock

ffmpeg和mencoder


## github使用 https://github.com/cssmagic/blog/issues/49

## https://docs.oracle.com/javase/7/docs/ java文档

## java 中集合问题

## java中IO

## java 中算法

# 分布式任务调度文档https://qq254963746.gitbooks.io/lts/content/use/quick-start.html

##heepClient连接被重用之后的坑 :
1、如果被请求端主动关闭了连接请求，而此时调用端没有来得及关闭连接，再次使用时如果再次拿到当前连接
	则请求到目标服务器时会提示The target server failed to response

2、如果请求端因为各种原因中断了请求到目标服务器的请求，如果目标服务器此时有数据response回去，则会提示clientException异常

##传播特性(7中) :
事务的第一个方面是传播行为。传播行为定义关于客户端和被调用方法的事务边界。Spring定义了7中传播行为。

传播行为	意义
PROPAGATION_MANDATORY	表示该方法必须运行在一个事务中。如果当前没有事务正在发生，将抛出一个异常
PROPAGATION_NESTED	表示如果当前正有一个事务在进行中，则该方法应当运行在一个嵌套式事务中。被嵌套的事务可以独立于封装事务进行提交或回滚。如果封装事务不存在，行为就像PROPAGATION_REQUIRES一样。
PROPAGATION_NEVER	表示当前的方法不应该在一个事务中运行。如果一个事务正在进行，则会抛出一个异常。
PROPAGATION_NOT_SUPPORTED	表示该方法不应该在一个事务中运行。如果一个现有事务正在进行中，它将在该方法的运行期间被挂起。
PROPAGATION_SUPPORTS	表示当前方法不需要事务性上下文，但是如果有一个事务已经在运行的话，它也可以在这个事务里运行。
PROPAGATION_REQUIRES_NEW	表示当前方法必须在它自己的事务里运行。一个新的事务将被启动，而且如果有一个现有事务在运行的话，则将在这个方法运行期间被挂起。
PROPAGATION_REQUIRES	表示当前方法必须在一个事务中运行。如果一个现有事务正在进行中，该方法将在那个事务中运行，否则就要开始一个新事务。
传播规则回答了这样一个问题，就是一个新的事务应该被启动还是被挂起，或者是一个方法是否应该在事务性上下文中运行。

##隔离级别(4中(+默认使用后端数据看的事物隔离级别)TransactionDefinition ):
- 结合hdc中的数据库信息重读数据库

声明式事务的第二个方面是隔离级别。隔离级别定义一个事务可能受其他并发事务活动活动影响的程度。另一种考虑一个事务的隔离级别的方式，是把它想象为那个事务对于事物处理数据的自私程度。

在一个典型的应用程序中，多个事务同时运行，经常会为了完成他们的工作而操作同一个数据。并发虽然是必需的，但是会导致一下问题：

脏读（Dirty read）-- 脏读发生在一个事务读取了被另一个事务改写但尚未提交的数据时。如果这些改变在稍后被回滚了，那么第一个事务读取的数据就会是无效的。
不可重复读（Nonrepeatable read）-- 不可重复读发生在一个事务执行相同的查询两次或两次以上，但每次查询结果都不相同时。这通常是由于另一个并发事务在两次查询之间更新了数据。
幻影读（Phantom reads）-- 幻影读和不可重复读相似。当一个事务（T1）读取几行记录后，另一个并发事务（T2）插入了一些记录时，幻影读就发生了。在后来的查询中，第一个事务（T1）就会发现一些原来没有的额外记录。
在理想状态下，事务之间将完全隔离，从而可以防止这些问题发生。然而，完全隔离会影响性能，因为隔离经常牵扯到锁定在数据库中的记录（而且有时是锁定完整的数据表）。侵占性的锁定会阻碍并发，要求事务相互等待来完成工作。

考虑到完全隔离会影响性能，而且并不是所有应用程序都要求完全隔离，所以有时可以在事务隔离方面灵活处理。因此，就会有好几个隔离级别。

隔离级别	含义
ISOLATION_DEFAULT	使用后端数据库默认的隔离级别。
ISOLATION_READ_UNCOMMITTED	允许读取尚未提交的更改。可能导致脏读、幻影读或不可重复读。
ISOLATION_READ_COMMITTED	允许从已经提交的并发事务读取。可防止脏读，但幻影读和不可重复读仍可能会发生。
ISOLATION_REPEATABLE_READ	对相同字段的多次读取的结果是一致的，除非数据被当前事务本身改变。可防止脏读和不可重复读，但幻影读仍可能发生。
ISOLATION_SERIALIZABLE	完全服从ACID的隔离级别，确保不发生脏读、不可重复读和幻影读。这在所有隔离级别中也是最慢的，因为它通常是通过完全锁定当前事务所涉及的数据表来完成的。
只读
声明式事务的第三个特性是它是否是一个只读事务。如果一个事务只对后端数据库执行读操作，那么该数据库就可能利用那个事务的只读特性，采取某些优化 措施。通过把一个事务声明为只读，可以给后端数据库一个机会来应用那些它认为合适的优化措施。由于只读的优化措施是在一个事务启动时由后端数据库实施的， 因此，只有对于那些具有可能启动一个新事务的传播行为（PROPAGATION_REQUIRES_NEW、PROPAGATION_REQUIRED、 ROPAGATION_NESTED）的方法来说，将事务声明为只读才有意义。

此外，如果使用Hibernate作为持久化机制，那么把一个事务声明为只读，将使Hibernate的flush模式被设置为FLUSH_NEVER。这就告诉Hibernate避免和数据库进行不必要的对象同步，从而把所有更新延迟到事务的结束。

事务超时
为了使一个应用程序很好地执行，它的事务不能运行太长时间。因此，声明式事务的下一个特性就是它的超时。

假设事务的运行时间变得格外的长，由于事务可能涉及对后端数据库的锁定，所以长时间运行的事务会不必要地占用数据库资源。这时就可以声明一个事务在特定秒数后自动回滚，不必等它自己结束。

由于超时时钟在一个事务启动的时候开始的，因此，只有对于那些具有可能启动一个新事务的传播行为（PROPAGATION_REQUIRES_NEW、PROPAGATION_REQUIRED、ROPAGATION_NESTED）的方法来说，声明事务超时才有意义。

回滚规则
事务五边形的对后一个边是一组规则，它们定义哪些异常引起回滚，哪些不引起。在默认设置下，事务只在出现运行时异常（runtime exception）时回滚，而在出现受检查异常（checked exception）时不回滚（这一行为和EJB中的回滚行为是一致的）。

不过，也可以声明在出现特定受检查异常时像运行时异常一样回滚。同样，也可以声明一个事务在出现特定的异常时不回滚，即使那些异常是运行时一场。

