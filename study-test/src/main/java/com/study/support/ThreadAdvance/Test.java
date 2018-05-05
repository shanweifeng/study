package com.study.support.ThreadAdvance;

import com.sun.xml.internal.ws.util.QNameMap;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 15:36 2018/5/5
 * @Modified By:
 */
public class Test {
    public static void main(String[] args)
    {//题目见当前文件夹txt.md
        //synchronousQueueTest();
        executorsTest();
        forkJoinTest();
    }

    public static void synchronousQueueTest()
    {//修改后
        SynchronousQueue<String> queue=new SynchronousQueue();
        new Thread(()->{
            try {
                System.out.println(queue.poll(1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            if(queue.offer("S1",1,TimeUnit.SECONDS))
            {
                System.out.println("scucess");
            }else
            {
                System.out.println("faield");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ExecutorService executorService = Executors.newCachedThreadPool();//使用变长线程池
    private static ConcurrentHashMap<String,Integer> result = new ConcurrentHashMap<>();
    public static void executorsTest()
    {
        String path="D:\\workSpace\\study\\study-test\\src\\main\\java\\com\\study\\support";//搜寻路径
        String key="static";//需要查询的关键字
        executorsMethod(new File(path),key);
        result.entrySet().stream().sorted((x1,x2)->x2.getValue()>x1.getValue()?1:-1).limit(10).forEach(x->{
            System.out.println("查询关键字:"+x.getValue()+"-->"+x.getKey());
        });
    }
    public static void executorsMethod(File file,String key)
    {
        if(file.isDirectory())
        {
            List<Future> future = Arrays.stream(file.listFiles()).map(x->executorService.submit(()->{
                    if(x.isDirectory())
                    {
                        executorsMethod(x,key);
                    }
                    else
                    {
                        countWord(x,key);
                    }
                })).collect(Collectors.toList());
            Supplier supplier = ()->future.stream().map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            supplier.get();
        }
        else
        {//文件
            countWord(file,key);
        }
    }

    public static void countWord(File file,String key)
    {

        Pattern pattern = Pattern.compile(key);
        try {
            Files.lines(Paths.get(file.getAbsolutePath())).mapToLong(l->{
                long count = 0;
                while(pattern.matcher(l).find())
                {
                    count++;
                }
                return count;
            }).sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void forkJoinTest()
    {

    }
}
