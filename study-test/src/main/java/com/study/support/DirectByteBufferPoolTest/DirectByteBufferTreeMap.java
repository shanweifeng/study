package com.study.support.DirectByteBufferPoolTest;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * @Author shanweifeng
 * @Description: TreeMap 特性？ 树中有列表主要是防止已经分配1024 又来一个请求分配1024？
 * @Date: Created in 15:17 2018/6/21
 * @Modified By:
 */
public class DirectByteBufferTreeMap extends TreeMap<Integer,List<ByteBuffer>>{
    public void addByValue(ByteBuffer buffer) {
        if(containsKey(buffer.capacity())){
            ceilingEntry(buffer.capacity()).getValue().add(buffer);
        } else {
            List<ByteBuffer> list = new ArrayList<>();
            list.add(buffer);
            put(buffer.capacity(),list);
        }
    }

    public void removeByValue(ByteBuffer buffer) throws Exception{
        if(containsKey(buffer.capacity()) && ceilingEntry(buffer.capacity()).getValue().remove(buffer)) {
            if (ceilingEntry(buffer.capacity()).getValue().size() <= 0){
                remove(buffer.capacity());
            }
            return ;
        }
        throw new Exception("ByteBuffer not exsit");
    }

    public ByteBuffer getByKey(int cap) throws Exception {
        Map.Entry<Integer,List<ByteBuffer>> freeEntry = tailMap(cap,true).firstEntry();
        if (Objects.isNull(freeEntry) || freeEntry.getValue().size() <= 0){
            return null;
        }
        return freeEntry.getValue().get(0);
    }
}
