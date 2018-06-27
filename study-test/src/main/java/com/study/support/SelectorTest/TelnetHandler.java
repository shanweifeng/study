package com.study.support.SelectorTest;

import com.sun.xml.internal.bind.v2.model.runtime.RuntimeBuiltinLeafInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 11:38 2018/6/26
 * @Modified By:
 */
public class TelnetHandler implements Runnable {
    private SelectionKey selectionKey;
    private SocketChannel socketChannel;
    private Selector selector;
    private ByteBuffer readByteBuffer = ByteBuffer.allocateDirect(1024);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocateDirect(1024);
    private int lastReadPosition = 0;
    private int lastWritePosition = 0;
    final long MAX_SEND_BUFFER_LENGTH = 100;
    boolean isDownLoading = false;
    String downloadingFileName;

    public TelnetHandler(SocketChannel socketChannel,Selector selector){
        this.socketChannel = socketChannel;
        this.selector = selector;
        try {
            socketChannel.configureBlocking(Boolean.FALSE);
            selectionKey = socketChannel.register(selector,0);
            selectionKey.attach(this);
            selectionKey.interestOps(SelectionKey.OP_READ);
            writeByteBuffer.put(new String("here").getBytes());
            writeByteBuffer.flip();
        } catch (IOException e) {
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (selectionKey.isReadable())
        {
            doRead();
        }else{
            doWrite();
        }
    }

    public void doRead(){
        String cmd = null;
        try {
            socketChannel.read(readByteBuffer);
            int endPosition = readByteBuffer.position();
           // for()
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doWrite(){

    }
}
