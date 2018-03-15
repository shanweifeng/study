package com.study.support.leader.us;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Created by Administrator on 2018/3/15.
 * 高端基础 第二节Java IO
 */
public class JavaIO {
    /*1  得到 String s="中国" 这个字符串的utf-8编码，gbk编码，iso-8859-1编码的字符串，看看各自有多少字节，同时解释为什么以utf-8编码得到的byte[]无法用gbk的方式“还原”为原来的字符串
2 分别用大头和小头模式将整数 a=10240写入到文件中（4个字节），并且再正确读出来，打印到屏幕上，同时截图UltraEdit里的二进制字节序列，做对比说明
3 整理全套的Java IO类图并用PPT讲解说明
4  随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
    然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
    wx, 200万，10人
    lt, 180万，8人
         ....
    加分题

1：用装饰者模式实现如下的功能：
    要求用户输入一段文字，比如 Hello Me，然后屏幕输出几个选项
       1 ：加密
       2 ：反转字符串
       3：转成大写
       4：转成小写
       5：扩展或者剪裁到10个字符，不足部分用！补充
       6:用户输入 任意组合，比如 1，3 表示先执行1的逻辑，再执行3的逻辑

    根据用户输入的选择，进行处理后，输出结果

2: 用FileChannel的方式实现第四题，注意编码转换问题，并对比性能*/

    static int bigEndian = 0;//java 中默认大头
    static int littileEndian = 1;
    private static byte[] intToByte(int num,int endian) throws Exception{
        System.out.println(num);
        byte[] result = new byte[4];
        result[0] = (byte)(num&0xFF);
        result[1] = (byte)((num>>8)&0xFF);
        result[2] = (byte)((num>>16)&0xFF);
        result[3] = (byte)((num>>24)&0xFF);
        if(littileEndian == endian){
            return result;
        }else if(bigEndian == endian){
            //逆置
            byte temp = 0;
            for(int i=0,j=result.length-1;i<j;i++,j--){
                temp = result[i];
                result[i] = result[j];
                result[j] = temp;
            }
            return result;
        }else {
            throw new Exception("字节序异常");
        }
    }

    private static int byteArrayToInt(byte[] num,int endian)throws Exception{
        int result = 0;
        if(bigEndian == endian){
            result = (num[0]<<24)&0xff+(num[1]<<16)&0xff+(num[2]<<8)&0xff+num[3]&0xff;
        }else if(littileEndian == endian){
            result = (num[3]<<24)&0xff+(num[2]<<16)&0xff+(num[1]<<8)&0xff+num[0]&0xff;
        }else{
            throw new Exception("字节序异常");
        }
        return 0;
    }

    public static void main(String[] agrs) throws Exception{
        //1  得到 String s="中国" 这个字符串的utf-8编码，gbk编码，iso-8859-1编码的字符串，
        // 看看各自有多少字节，同时解释为什么以utf-8编码得到的byte[]无法用gbk的方式“还原”为原来的字符串
        String str = "中国";
        //System.out.println("系默认编码:"+System.getProperty("file.encoding"));
        //System.out.println(Charset.defaultCharset());
        //System.out.println(str+"->UTF8:"+str.getBytes(Charset.forName("utf8")));
        //System.out.println(str+"->GBK:"+str.getBytes(Charset.forName("GBK")));
        //System.out.println(str+"->iso8859-1:"+str.getBytes(Charset.forName("iso8859-1")));


        //2 分别用大头和小头模式将整数 a=10240写入到文件中（4个字节），
        // 并且再正确读出来，打印到屏幕上，同时截图UltraEdit里的二进制字节序列，做对比说明
        int a = 10240;
        String path = "d://be.txt";
        String pathLittile = "d://li.txt";
        writeFile(intToByte(a,bigEndian),path);
        writeFile(intToByte(a,littileEndian),pathLittile);
        System.out.println(a+" 大头文件读取:"+readFileContext(path));
        System.out.println(a+" 小偷文件读取:"+readFileContext(pathLittile));
        //3 整理全套的Java IO类图并用PPT讲解说明
        //4  随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，
        // 每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
        //然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，
        // 最后做排序和分组，输出年薪总额最高的10组：
        //wx, 200万，10人
        //lt, 180万，8人
         /*加分题

        1：用装饰者模式实现如下的功能：
        要求用户输入一段文字，比如 Hello Me，然后屏幕输出几个选项
        1 ：加密
        2 ：反转字符串
        3：转成大写
        4：转成小写
        5：扩展或者剪裁到10个字符，不足部分用！补充
        6:用户输入 任意组合，比如 1，3 表示先执行1的逻辑，再执行3的逻辑

        根据用户输入的选择，进行处理后，输出结果

        2: 用FileChannel的方式实现第四题，注意编码转换问题，并对比性能*/

    }

    private static byte[] readFileContext(String path){
        byte[] num = new byte[4];
        FileInputStream in = null;
        try{
            in = new FileInputStream(new File(""));
            in.read(num);
        }catch (Exception e){

        }finally {
            if (Objects.nonNull(in)){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static void writeFile(byte[] num,String path){
        for (byte b:num){
            System.out.print(" "+b);
        }
        System.out.println("");
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(new File(path));//判断文件是否存在
            out.write(num);
            out.flush();
        }catch (Exception e){
            System.out.println("写异常");
        }finally {
            if (Objects.nonNull(out)){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
