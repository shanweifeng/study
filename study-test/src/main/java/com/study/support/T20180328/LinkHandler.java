package com.study.support.T20180328;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 16:03 2018/3/28
 * @Modified By:
 */
public interface LinkHandler {
    /**
     * Places the link in the queue
     * @param link
     * @throws Exception
     */
    void queueLink(String link) throws Exception;

    /**
     * Returns the number of visited links
     * @return
     */
    int size();

    /**
     * Checks if the link was already visited
     * @param link
     * @return
     */
    boolean visited(String link);

    /**
     * Marks this link as visited
     * @param link
     */
    void addVisited(String link);
}
