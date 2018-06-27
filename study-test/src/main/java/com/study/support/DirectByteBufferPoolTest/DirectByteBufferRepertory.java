package com.study.support.DirectByteBufferPoolTest;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * @Author shanweifeng
 * @Description: DirectByteBuffer 缓存池 数据仓库？  真正存放数据结构的地方
 * @Date: Created in 18:04 2018/6/20
 * @Modified By:
 */
public class DirectByteBufferRepertory {
    // 最大可分配空间
    int totalCap = 0;
    // 已分配空间
    int useCap = 0;
    // 读写锁
    static Object lock = new Object();
    // 空闲集合
    DirectByteBufferTreeMap freeByteBufferSet = new DirectByteBufferTreeMap();
    // 正在使用集合
    DirectByteBufferTreeMap useByteBufferSet = new DirectByteBufferTreeMap();

    public DirectByteBufferRepertory(int capacity){
        this.totalCap = capacity;
    }

    public int getRemainCap(){
        synchronized (lock){
            return this.totalCap - this.useCap;
        }
    }

    public ByteBuffer getByteBuffer(int cap) throws Exception {
        synchronized (lock) {
            if(this.totalCap - this.useCap - cap <= 0){
                return null;
            }
            ByteBuffer buffer = freeByteBufferSet.getByKey(cap);
            if(Objects.isNull(buffer)){
                buffer = ByteBuffer.allocate(cap);
                useByteBufferSet.addByValue(buffer);
            }else{
                freeByteBufferSet.removeByValue(buffer);
                useByteBufferSet.addByValue(buffer);
            }
            this.useCap += buffer.capacity();
            return buffer;
        }
    }

    public void retrieve(ByteBuffer buffer) throws Exception{
        synchronized (lock){
            this.useCap -= buffer.capacity();
            useByteBufferSet.removeByValue(buffer);
            freeByteBufferSet.addByValue(buffer);
            this.useCap -= buffer.capacity();
        }
    }
}
