package com.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class Network {
    private SocketChannel channel;

    public Network() {
        new Thread(()-> {

            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                // выполнение преднастройки нашего клиента
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                            }
                        });

                // конфигурируем на каком порту нужно запустить клиент
                ChannelFuture future = b.connect("localhost", 8189).sync();
                //ожидание закрытие (это блокирующая операция)
                future.channel().closeFuture().sync();

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                 workerGroup.shutdownGracefully();
            }

        }).start();
    }

    public void sendMessage(){
       //todo 39.00
    }

}
