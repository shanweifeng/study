package com.study.support.T20180319JavaIo;

import java.util.Random;

/**
 * @Author shanweifeng
 * @Description: //使用字节存储数据 然后还原数据
 * @Date: Created in 16:27 2018/3/19
 * @Modified By:
 */
public class SalaryByByte {
    //将字符 数字存储为byte字节
    //4  随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，
    // 每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
    //然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，
    // 最后做排序和分组，输出年薪总额最高的10组：
    //wx, 200万，10人
    //lt, 180万，8人
    //生成对象用于存储到文件
    static Random random = new Random();
    public static void main(String[] args){
        String line = System.lineSeparator();
        /*if ("\r\n".equals(line)) {
            System.out.println("windows");
        } else if ("\n".equals(line)) {
            System.out.println("Mac");
        }else  if ("\r".equals(line)) {
            System.out.println("linux/unix");
        }*/
//1、名字长度不确定 使用byte长度  salary大小不确定
    }
}
