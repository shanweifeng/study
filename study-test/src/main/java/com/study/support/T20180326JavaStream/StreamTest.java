package com.study.support.T20180326JavaStream;

import java.util.stream.Stream;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 10:13 2018/3/26
 * @Modified By:
 */
public class StreamTest {

    public static void main(String[] args){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                }).sorted()
                .forEach(s -> System.out.println("forEach: " + s));
    }
}
