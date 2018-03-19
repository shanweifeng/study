package com.study.support.leader.us.other;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Study4 {
    char[] numbersAndLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    Random randGen = new Random();

    //@Test
    public void testa() throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(new File("C:\\tmp\\test.data")));
        bufferedOutputStream.write(new String("").getBytes());
        int count = 10000000;
        int step = 6;
        byte[] bytes = new byte[6 * count];
        for (int i = 0; i < count; i++) {
            int baseSalary = (byte) (Math.random() * 95) + 5;
            int bonus = (int) (Math.random() * 10);
            int start = i * step;
            bytes[start + 0] = (byte) baseSalary;
            bytes[start + 1] = (byte) bonus;
            bytes[start + 2] = (byte) numbersAndLetters[randGen.nextInt(26)];
            bytes[start + 3] = (byte) numbersAndLetters[randGen.nextInt(26)];
            bytes[start + 4] = (byte) numbersAndLetters[randGen.nextInt(26)];
            bytes[start + 5] = (byte) numbersAndLetters[randGen.nextInt(26)];
        }
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.close();
        
        System.out.println("写入时间：" + (System.currentTimeMillis() - currentTimeMillis));
        currentTimeMillis = System.currentTimeMillis();
        bytes = new byte[6 * count];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(new File("C:\\tmp\\test.data")));
        bufferedInputStream.read(bytes);
        bufferedInputStream.close();
        final int size = 26;
        final int buffer = 97;
        int[][] userTotal = new int[size][size];
        int[][] userCount = new int[size][size];
        System.out.println("读取文件时间：" + (System.currentTimeMillis() - currentTimeMillis));
        currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            int start = i * step;
            int x = bytes[start + 2] - buffer;
            int y = bytes[start + 3] - buffer;
            userTotal[x][y] = userTotal[x][y] + (bytes[start + 0] * 13) + bytes[start + 1];
            userCount[x][y] = userCount[x][y] + 1;
        }
        System.out.println("加载数据：" + (System.currentTimeMillis() - currentTimeMillis));
        currentTimeMillis = System.currentTimeMillis();
        Result[] list = new Result[size * size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                char a = (char) ((byte) x + buffer);
                char b = (char) ((byte) y + buffer);
                Result result = new Result();
                result.nameIndex[0] = a;
                result.nameIndex[1] = b;
                result.count = userCount[x][y];
                result.total = userTotal[x][y];
                list[size * x + y] = result;
            }
        }
        Arrays.parallelSort(list, (r1, r2) -> {
            return r2.total - r1.total;
        });
        System.out.println("统计排序时间：" + (System.currentTimeMillis() - currentTimeMillis));
        currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Result result = list[i];
            System.out.println("nameIndex-->:" + result.nameIndex[0] + result.nameIndex[1] + "--->:total:"
                    + result.total + "--->:count:" + result.count);
        }
    }

    public final char[] randomString(int length) {
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; ++i) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(26)];
        }
        return randBuffer;
    }

    class Result {
        char[] nameIndex = new char[2];
        int total;
        int count;
    }
}
