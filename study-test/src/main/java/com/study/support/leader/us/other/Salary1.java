package com.study.support.leader.us.other;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/9 0009.
 */
public class Salary1 {
    private static byte[] name = new byte[5];
    private static Random rand = new Random(System.currentTimeMillis());
    private static final int dictSize = 26 * 26;
    private static final int alphaSize = 26;
    private static void randName(Random rand) {
        for (int i = 0; i < 5; i++) {
            name[i] = (byte)(rand.nextInt(alphaSize) + 97);
        }
    }
    private static void gen(String fileName, int number) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            for (int i = 0; i < number; i++) {
                int salary = rand.nextInt(10) + 1;
                int bonus  = rand.nextInt(6);
                randName(rand);
                file.write(name);
                file.writeBytes(",");
                file.writeBytes(Integer.toString(salary));
                file.writeBytes(",");
                file.writeBytes(Integer.toString(bonus));
                file.writeBytes("\n");
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] prefix = new byte[5];
    private static short encode() {
        byte high = (byte) (prefix[1] - 97);
        byte low  = (byte) (prefix[0] - 97);
        return (short) ((high << 4) + (high << 3) + (high << 1)  + low);
    }
    private static byte[] decode(int index) {
        byte[] ret = new byte[2];
        ret[0] = (byte) (index % 26 + 97);
        ret[1] = (byte) (index / 26 + 97);
        return ret;
    }

    private static ByteBuffer dict = ByteBuffer.allocateDirect(dictSize * 8);
    private static void group(String fileName) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "r");
            FileChannel fch = file.getChannel();
            MappedByteBuffer buffer = fch.map(FileChannel.MapMode.READ_ONLY,0, file.length());
            while (buffer.position() < buffer.limit()) {
                buffer.get(prefix);
                byte salary = buffer.get();
                byte bonus = buffer.get();
                short index = encode();
                int yearSalary = ((salary << 3) + (salary << 2) + salary + bonus);
                //int yearSalary = salary*13 + bonus;
                int current = index << 3;
                int oldTotal  = dict.getInt(current);
                int oldCount  = dict.getInt(current + 4);
                dict.putInt(current, oldTotal + yearSalary);
                dict.putInt(current + 4, oldCount + 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PriorityQueue<ByteBuffer> queue = new PriorityQueue<>(10,
                (o1, o2) -> Integer.compare(o2.getInt(0), o1.getInt(0)));

        /*for(int i = 0; i < dictSize; i++) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10);
            int current = i << 3;
            buffer.putInt(dict.getInt(current));
            buffer.putInt(dict.getInt(current+ 4));
            buffer.put(decode(i));
            buffer.position(0);
            queue.add(buffer);
        }
        for(int i = 0; i < 10; i++) {
            ByteBuffer buffer  = queue.poll();
            byte[] prefix = new byte[2];
            int total = buffer.getInt();
            int count = buffer.getInt();
            buffer.get(prefix);

            StringBuilder bb = new StringBuilder();
            bb.append(new String(prefix));
            bb.append(',');
            bb.append(total);
            bb.append(',');
            bb.append(count);
            System.out.println(bb.toString());
        }*/
    }

    private static byte Shift() {
        byte a = 2;
        byte ret = (byte)((a<<4) + (a<<3) + (a<<1));
        return ret;
    }

    private static byte Mul() {
        byte a = 2;
        byte ret = (byte)(a*26);
        return ret;
    }

    private static long RunBench(String fileName) {
        long start = 0;
        long end = 0;
        long interval = 0;
        long minInterval = 0;
        for(int i=0;i<100;i++) {
            start = System.nanoTime();
            group(fileName);
            end = System.nanoTime();
            interval = end-start;
            if (minInterval==0) {
                minInterval = interval;
            } else {
                minInterval = minInterval<interval?minInterval:interval;
            }
        }
        return minInterval/1000;
    }

    public static void main(String[] args) {
        //long t0 = RunBench("byteSalary.txt");
        gen("byteSalary.txt", 10*1000*1000);
        //group("byteSalary.txt");
        //System.out.println("Best Time Usage: "+RunBench("byteSalary.txt")+"us");
    }
}
