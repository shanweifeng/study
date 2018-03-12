package com.study.support;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 14:59 2018/3/9
 * @Modified By:
 */
public class App {

    public static final int NUM_SLOTS=1024*1024*8;
    public static final int NUM_HASH=8;
    private BigInteger bitmap = new BigInteger("0");
    static String base = "1234567890-=!@#$%^&*()_+qwertyuiopasdfghjklzxcvbnm[]<,>.?/:;}]{[";
    public static void main(String[] args) {
        //布隆过滤器 测试代码
        App bf = new App();
        int number = new Random().nextInt(10)+10000;
       /* ArrayList<String> contents = new ArrayList<>(number+5);
        contents.add("sldkjelsjf");
        contents.add("ggl;ker;gekr");
        contents.add("wieoneomfwe");
        contents.add("sldkjelsvrnlkjf");
        contents.add("ksldkflefwefwefe");*/
        //生成million级数据
        int temp = 5;
        StringBuffer st=new StringBuffer();
        /*for(int i = 0;i<number;i++){
            temp = new Random().nextInt(20);
            st.delete(0,st.length());
            for (int j = 0 ;j<temp;j++)
            {
                st.append(base.charAt(new Random().nextInt(base.length())));
            }
            contents.add(st.toString());
        }
        System.out.println("contents.size:"+contents.size());
        long start = System.currentTimeMillis();
        for(int i=0;i<contents.size();i++){
            bf.addElement(contents.get(i));
        }
        System.out.println(bf.check("sldkjelsvrnlkjf"));
        System.out.println(bf.check("sldkjelsvrnkjf"));
        System.out.println("time:" + (System.currentTimeMillis()- start));*/
        ArrayList<String> ta = new ArrayList<>(number+5);
        ta.add("sldkjelsjf");
        ta.add("ggl;ker;gekr");
        ta.add("wieoneomfwe");
        ta.add("sldkjelsvrnlkjf");
        ta.add("ksldkflefwefwefe");
        //生成million级数据
        for(int i = 0;i<number;i++){
            temp = new Random().nextInt(20);
            st.delete(0,st.length());
            for (int j = 0 ;j<temp;j++)
            {
                st.append(base.charAt(new Random().nextInt(base.length())));
            }
            ta.add(st.toString());
        }
        long start = System.currentTimeMillis();
        if(ta.contains("sldkjelsjf"))
            System.out.println(true);
        else
            System.out.println(false);
        if(ta.contains("sldkjelsjf11"))
            System.out.println(true);
        else
            System.out.println(false);
        System.out.println("time:" + (System.currentTimeMillis()- start));
    }

    private int getHash(String message,int n){
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            message  = message +String.valueOf(n);
            byte[] bytes = message.getBytes();
            md5.update(bytes);
            BigInteger bi = new BigInteger(md5.digest());

            return Math.abs(bi.intValue())%NUM_SLOTS;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void addElement(String message){
        for(int i=0;i<NUM_HASH;i++){
            int hashcode = getHash(message,i);
            if(!bitmap.testBit(hashcode)){
                bitmap = bitmap.or(new BigInteger("1").shiftLeft(hashcode));
            }
        }

    }

    public boolean check(String message){
        for(int i=0;i<NUM_HASH;i++){
            int hashcode = getHash(message,i);
            if(!this.bitmap.testBit(hashcode)){
                return false;
            }
        }
        return true;
    }
}
