package com.study.support.streamExtends;

import java.util.stream.IntStream;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 16:55 2018/4/2
 * @Modified By:
 */
public class IntStreamTest {
    public static void main(String[] args){
        // 包括0-9
        IntStream.rangeClosed('0', '9').forEach(System.out::print);
        // 不包括9
        IntStream.range('0', '9').forEach(System.out::print);


    }
}
