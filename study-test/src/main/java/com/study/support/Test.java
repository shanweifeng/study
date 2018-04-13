package com.study.support;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 14:10 2018/3/19
 * @Modified By:
 */
public class Test {
    public static void main(String[] args){//负数二进制表示 对应正数的二进制取反然后加1
        /*System.out.println("2^7-1:"+ (byte)(((1<<7)&0xff)-1)+" -2^7-1:"+ (byte)((-1<<7)&0xff));
        System.out.println("2^15-1:"+ (short)(((1<<15)&0xffff)-1)+ "  -2^15-1:"+ (short)((-1<<15)&0xffff));
        System.out.println("2^31-1:"+ ((1<<31)-1)+" -2^31-1:"+ (-1<<31));
        System.out.println("2^63-1:"+ ((1l<<63)-1)+" -2^63-1:"+ (1l<<63));
        System.out.println((int)'a');
        System.out.println((int)'A');
        System.out.println((int)'1');
        System.out.println(new Random().nextInt(1));*/
        /*char s = 'a';
        String bit = Integer.toBinaryString(s);
        System.out.println("bit = " + bit);
        String str = "中国";
        System.out.println(str+":"+new String(str.getBytes(Charset.forName("gbk")),Charset.forName("gbk")));

        long l = Integer.MIN_VALUE;
        int i =Integer.MIN_VALUE;
        System.out.println(l == i);

        List<Integer> sl = new ArrayList<>();
        sl.add(1);
        sl.add(1);
        //sl.add(2);
        sl.add(3);
        List<Integer> sl1 = new ArrayList<>();
        sl1.add(2);
        sl1.add(1);

        System.out.println(sl.containsAll(sl1));
        System.out.println(sl.removeAll(sl1));
        System.out.println(sl.size());*/
        System.out.println(Long.MAX_VALUE);
    }
}
