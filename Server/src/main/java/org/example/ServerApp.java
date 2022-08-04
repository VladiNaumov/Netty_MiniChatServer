package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class ServerApp {

    public static void main(String[] args) {

        // отвечает за пподключающихся клиентов
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
        // выполнение преднастройки нашего сервера
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //подключение класса new MainHandler() для преобразования байтов в строку мы подключаем (new StringDecoder(), new StringEncoder())
                         //socketChannel.pipeline().addLast(new MainHandler());
                        socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new MainHandlerDemo());
                    }
                });

            // конфигурируем на каком порту нужно запустить сервер
            ChannelFuture future = b.bind("localhost",8189).sync();
            //ожидание закрытие (это блокирующая операция)
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
