package com.study.support.DirectByteBufferPoolTest;

import java.nio.ByteBuffer;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 17:59 2018/6/20
 * @Modified By:
 */
public class DirectByteBufferTest {

    public static void main(String[] args) throws Exception{
        DirectByteBufferPool pool = new DirectByteBufferPool(50*3);
        System.out.println("程序开始，剩余空间:"+pool.getRemain());
        ByteBuffer buffer1 = pool.allocate(60);
        System.out.println("分配了60，剩余空间:"+pool.getRemain());
        ByteBuffer buffer2 = pool.allocate(80);
        System.out.println("分配了80，剩余空间:"+pool.getRemain());
        //ByteBuffer buffer0 = pool.allocate(20);
        pool.free(buffer1);
        pool.free(buffer2);
        System.out.println("回收了60+80，剩余空间:"+pool.getRemain());
        buffer1 = pool.allocate(10);
        buffer2 = pool.allocate(60);
        System.out.println("再次分配了10+60，剩余空间:"+pool.getRemain());
        try {
            ByteBuffer buffer3 = pool.allocate(40);
            System.out.println("分配了40，剩余空间:"+pool.getRemain());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
