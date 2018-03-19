package com.study.support.T20180319JavaIo;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 16:27 2018/3/19
 * @Modified By:
 */
public class SalaryFileChannelTest {
    //使用fileChannel 管道读写文件 RandomAccessFile
    //4  随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，
    // 每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
    //然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，
    // 最后做排序和分组，输出年薪总额最高的10组：
    //wx, 200万，10人
    //lt, 180万，8人
    //生成对象用于存储到文件
}
