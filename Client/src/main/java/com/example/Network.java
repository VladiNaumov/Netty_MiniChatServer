package com.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Network {
    private SocketChannel channel;

    public Network(CallBack onMassegeReceivedCallback) {

       Thread t = new Thread(()-> {

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
                                // для преобразования байтов в строку
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new ClienHandler(onMassegeReceivedCallback));

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

        });
        // делаем поток демоном
        t.setDaemon(true);
        t.start();

    }


    public void sendMessage(String str){
       channel.writeAndFlush(str);
    }

}
