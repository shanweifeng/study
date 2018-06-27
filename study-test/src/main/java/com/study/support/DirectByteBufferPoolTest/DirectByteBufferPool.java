package com.study.support.DirectByteBufferPoolTest;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 18:02 2018/6/20
 * @Modified By:
 */
public class DirectByteBufferPool {
    // 最大可分配空间
    private final int MAX_CAPACITY = 1024 * 1024;
    // 当前分配空间
    private int limitCapacity = 0;
    // 数据库仓库
    private DirectByteBufferRepertory  repertory;

    public DirectByteBufferPool(){
        this.limitCapacity = MAX_CAPACITY;
        this.repertory = new DirectByteBufferRepertory(this.limitCapacity);
    }

    public DirectByteBufferPool(int capacity){
        if(capacity < MAX_CAPACITY){
            this.limitCapacity = capacity;
        }else{
            this.limitCapacity = MAX_CAPACITY;
        }
        repertory = new DirectByteBufferRepertory(this.limitCapacity);
    }

    public ByteBuffer allocate(int capacity) throws Exception {
        if(capacity > this.limitCapacity){
            throw new Exception("out of range");
        }
        ByteBuffer byteBuffer = repertory.getByteBuffer(capacity);
        if(Objects.isNull(byteBuffer)){
            throw new Exception(" allocate failure");
        }
        //this.limitCapacity -= byteBuffer.capacity();
        return byteBuffer;
    }

    public int getRemain(){
        return repertory.getRemainCap();
    }

    public void free(ByteBuffer byteBuffer) throws Exception {
        //this.limitCapacity += byteBuffer.capacity();
        repertory.retrieve(byteBuffer);
    }
}
