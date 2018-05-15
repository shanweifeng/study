package com.study.support.ThreadAdvance;

import com.sun.xml.internal.ws.util.QNameMap;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 15:36 2018/5/5
 * @Modified By:
 */
public class Test {
    public static void main(String[] args)
    {//题目见当前文件夹txt.md
        synchronousQueueTest();
        //executeTest();
        //forkJoinTest();
    }

    public static void synchronousQueueTest()
    {//修改后
        SynchronousQueue<String> queue=new SynchronousQueue();
        new Thread(()->{
            try {
                //System.out.println(queue.poll(1, TimeUnit.SECONDS));
                //System.out.println(queue.poll()); //如果没有处于正在写入的操作  会立即返回失败
                System.out.println(queue.take());//会出现阻塞 直到有元素插入到queue中
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            //if(queue.offer("S1",1,TimeUnit.SECONDS))
           /* if(queue.offer("S1"))//如果当前没有正在等待获取的操作 会立即返回faield
            {
                System.out.println("scucess");
            }else
            {
                System.out.println("faield");
            }*/
            queue.put("S1");//会出现阻塞，知道有操作将queue中的元素取出
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//使用变长线程池
    private static int deep = 3;
    private static ConcurrentHashMap<String,Long> result = new ConcurrentHashMap<>();
    public static void executeTest()
    {
        String path="D:\\workSpace\\study\\study-test\\src\\main\\java\\com\\study\\support\\Arrays";//搜寻路径
        String key="public";//需要查询的关键字
        //executorsMethod(new File(path),key);
        forkJoinTest(path,key);
        //searchFiles( path, key);
    }

    public static void searchFiles(String path,String key)
    {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            List<Future<Pair>> futureList = Files.walk(new File(path).toPath(), 3)
                    .filter(s->(!s.toFile().isDirectory() && s.toString().endsWith(".java")))
                    .map(s->new Callable<Pair>() {
                        @Override
                        public Pair call() throws Exception {
                            long count = Files.lines(s).map(l -> l.split(key).length-1)
                                    .collect(Collectors.toList()).stream().mapToLong(l->(long)l).sum();
                            return new Pair<Path, Long>(s, count);
                        }})
                    .map(c->executorService.submit((Callable<Pair>) c)).collect(Collectors.toList());
            Supplier<Stream<Pair>> streamSupplier = () -> futureList.stream().map(f-> {
                try {
                    return f.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
            System.out.println("class总共出现："+streamSupplier.get().mapToLong(f->(Long) f.getValue()).sum()+"次,");
            streamSupplier.get().sorted((a,b)-> Long.compare((Long)((Pair)b).getValue(), (Long)
                    ((Pair)a).getValue())).forEach(System.out::println);
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executorsMethod(File file,String key)
    {
        List<Path> filePath = new ArrayList<>();
        if(file.isDirectory())
        {
            for(File f:file.listFiles())
            {
                recursionFile(f,filePath);
            }
        }
        else
        {
            filePath.add(file.toPath());
        }
        //遍历后获取所有文件path
        List<Future<Pair<Path,Long>>> future = filePath.stream().map(f->executorService.submit(()->
                new Pair<>(f,Files.lines(f).mapToLong(fl->fl.split(key).length-1).sum()))).collect(Collectors.toList());
        printResult(future,key);
    }
    private static void recursionFile(File file,List<Path> list)
    {
        if(file.isDirectory())
        {
            recursionFile(file,list);
        }
        list.add(file.toPath());
    }

    private static void printResult(List<Future<Pair<Path,Long>>> future,String key)
    {
        Supplier<Stream<Pair>> supplier = ()->future.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
        System.out.println(key+"总共出现:"+supplier.get().mapToLong(f->(Long)f.getValue()).sum());
        supplier.get().forEach(f->{
            System.out.println(f.getKey()+":"+f.getValue());
        });
    }

    public static void forkJoinTest(String path,String key)
    {
        SearchFilesTask task = new SearchFilesTask(new File(path),key);
        List<Pair<Path,Long>> result = task.invoke();
        printResult(result.stream().map(pair->executorService.submit(()->pair)).collect(Collectors.toList()),key);
    }
}

class SearchFilesTask extends RecursiveTask<List<Pair<Path,Long>>>
{
    private File file;
    private String key;
    private Integer maxDeep;

    public SearchFilesTask(File file,String key)
    {
        this.file = file;
        this.key = key;
    }

    public SearchFilesTask(File file,String key,Integer maxDeep)
    {
        this.file = file;
        this.key = key;
        this.maxDeep = maxDeep;
    }

    @Override
    protected List<Pair<Path, Long>> compute() {
        List<Pair<Path, Long>> result = new ArrayList();
        if(file.isDirectory())
        {
            List<SearchFilesTask> tasks = Arrays.stream(file.listFiles()).map(f->new SearchFilesTask(f,key)).collect(Collectors.toList());
            invokeAll(tasks);
            tasks.stream().forEach(task->{
                try {
                    result.addAll(task.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
        else
        {
            result.add(getFileCountInfo(file));
        }
        return result;
    }

    private Pair<Path,Long> getFileCountInfo(File file)
    {
        try {
            return new Pair<Path,Long>(file.toPath(),Files.lines(file.toPath()).mapToLong(f->f.split(key).length-1).sum());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}