package com.study.support.SelectorTest;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 11:21 2018/6/26
 * @Modified By:
 */
public class TelnetAcceptor implements Runnable {
    private SocketChannel socketChannel;
    private Selector selector;

    public TelnetAcceptor(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        new TelnetHandler(socketChannel,selector);
    }
}
