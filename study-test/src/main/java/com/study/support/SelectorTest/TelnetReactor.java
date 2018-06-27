package com.study.support.SelectorTest;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 10:52 2018/6/26
 * @Modified By:
 */
public class TelnetReactor implements Runnable {
    private int port;
    public TelnetReactor(int port){
        this.port = port;
    }
    @Override
    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(Boolean.FALSE);
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true)
            {
                Set<SelectionKey> selectionKeys = null;
                if(selector.select() <= 0){
                    continue;
                }
                selectionKeys = selector.selectedKeys();
                for(SelectionKey selectionKey:selectionKeys){
                    if(selectionKey.isAcceptable()){
                        new TelnetAcceptor(serverSocketChannel.accept(),selector).run();
                    }else{
                        ((TelnetHandler)selectionKey.attachment()).run();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
}
