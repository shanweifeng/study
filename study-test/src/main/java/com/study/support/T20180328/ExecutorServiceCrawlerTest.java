package com.study.support.T20180328;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author shanweifeng
 * @Description: 使用ExecutorService实现简单的网络爬虫（有些可能是图片 需要对图片做处理 这里暂时不做处理）
 * @Date: Created in 15:59 2018/3/28
 * @Modified By:
 */
public class ExecutorServiceCrawlerTest implements LinkHandler  {
    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    //    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ExecutorService execService;

    public ExecutorServiceCrawlerTest(String startingURL, int maxThreads) {
        this.url = startingURL;
        execService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void queueLink(String link) throws Exception {
        startNewThread(link);
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
    }

    @Override
    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }

    private void startNewThread(String link) throws Exception {
        execService.execute(new LinkFinder(link, this));
        ///execService.shutdown();
    }

    private void startCrawling() throws Exception {
        startNewThread(this.url);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new ExecutorServiceCrawlerTest("http://www.baidu.com", 64).startCrawling();
    }
}
