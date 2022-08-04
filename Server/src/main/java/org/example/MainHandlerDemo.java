
package org.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

// обработчик входящих сообщений в виде строк (SimpleChannelInboundHandler<String>)
public class MainHandlerDemo extends SimpleChannelInboundHandler<String> {

    private static final List<Channel> channels = new ArrayList<>();
    private static int newClientIndex = 1;
    private String clientNme;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("клиент подключился: " + ctx);
        channels.add(ctx.channel());
        clientNme = "Client #" + newClientIndex;
        newClientIndex++;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("получено сообщение: " + s);
        String out = String.format("[%s]: %s\n", clientNme, s);
        for (Channel c : channels) {
            c.writeAndFlush(out);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // распечатывает информацию
        cause.printStackTrace();
        // закрываем  с ним подключение
        ctx.close();
    }


}

