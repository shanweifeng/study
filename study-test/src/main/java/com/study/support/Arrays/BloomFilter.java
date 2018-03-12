package com.study.support.Arrays;

import java.util.BitSet;

/**
 * @Author shanweifeng
 * @Description: 布隆过滤器  用于去重快速 节省空间  但是会有一定的误识率
 * @Date: Created in 17:58 2018/3/10
 * @Modified By:
 */
public class BloomFilter {
    //将数组中元素用二进制位表示  需要判断的数据通过同样的转化与其对比 如果八个二进制位都一一对应上 则基本为重复数据 但是这种情况下会存在未重复数据跟某一个数据恰好对应导致误判为重复
    //基本思想:
    //代码实现:
    private   static   final   int    DEFAULT_SIZE  =   2   <<   24 ;
    private   static   final   int [] seeds         =   new   int [] {  7 ,  11 ,  13 ,  31 ,  37 ,  61 , };

    private BitSet bits          =   new  BitSet(DEFAULT_SIZE);
    private  SimpleHash[]       func          =   new  SimpleHash[seeds.length];

    public   static   void  main(String[] args) {
        String value  =   " stone2083@yahoo.cn " ;
        BloomFilter filter  =   new  BloomFilter();
        System.out.println(filter.contains(value));
        filter.add(value);
        System.out.println(filter.contains(value));
    }

    public  BloomFilter() {
        for  ( int  i  =   0 ; i  <  seeds.length; i ++ ) {
            func[i]  =   new  SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public   void  add(String value) {
        for  (SimpleHash f : func) {
            bits.set(f.hash(value),  true );
        }
    }

    public   boolean  contains(String value) {
        if  (value  ==   null ) {
            return   false ;
        }
        boolean  ret  =   true ;
        for  (SimpleHash f : func) {
            ret  =  ret  &&  bits.get(f.hash(value));
        }
        return  ret;
    }

    public   static   class  SimpleHash {

        private   int  cap;
        private   int  seed;

        public  SimpleHash( int  cap,  int  seed) {
            this .cap  =  cap;
            this .seed  =  seed;
        }

        public   int  hash(String value) {
            int  result  =   0 ;
            int  len  =  value.length();
            for  ( int  i  =   0 ; i  <  len; i ++ ) {
                result  =  seed  *  result  +  value.charAt(i);
            }
            return  (cap  -   1 )  &  result;
        }

    }
}
