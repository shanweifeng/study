package com.study.support.T20180328;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author shanweifeng
 * @Description: ForkJoinPool实现简单的爬虫任务
 * @Date: Created in 15:59 2018/3/28
 * @Modified By:
 */
public class ForkJoinCrawler implements LinkHandler{
    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    //    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<>());
    private String url;
    private ForkJoinPool mainPool;

    public ForkJoinCrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
    }

    private void startCrawling() {
        mainPool.invoke(new LinkFinderAction(this.url, this));
    }

    @Override
    public void queueLink(String link) throws Exception {

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new ForkJoinCrawler("http://www.baidu.com", 64).startCrawling();
    }
}
