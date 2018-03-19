package com.study.support;

import java.math.BigDecimal;
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
        System.out.println("2^7-1:"+ (byte)(((1<<7)&0xff)-1)+" -2^7-1:"+ (byte)((-1<<7)&0xff));
        System.out.println("2^15-1:"+ (short)(((1<<15)&0xffff)-1)+ "  -2^15-1:"+ (short)((-1<<15)&0xffff));
        System.out.println("2^31-1:"+ ((1<<31)-1)+" -2^31-1:"+ (-1<<31));
        System.out.println("2^63-1:"+ ((1l<<63)-1)+" -2^63-1:"+ (1l<<63));
        System.out.println((int)'a');
        System.out.println((int)'A');
        System.out.println((int)'1');
        System.out.println(new Random().nextInt(1));
    }
}
