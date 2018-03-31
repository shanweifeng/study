package com.study.support.T20180326JavaStream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 10:13 2018/3/26
 * @Modified By:
 */
public class StreamTest {

    public static void main(String[] args) throws Exception {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .parallel()
                .filter(s -> {
                    System.out.println(Thread.currentThread().getName()+"-filter: " + s);
                    return true;
                })
                .sorted()
                .forEach(s -> {
                    System.out.println(Thread.currentThread().getName()+"-forEach: " + s);
                });
        /*IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        System.out.println(Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::sum));
        //System.out.println(Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min));
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).filter(x->x>0).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);*/
    }
}
