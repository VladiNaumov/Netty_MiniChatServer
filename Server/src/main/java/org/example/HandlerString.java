
package org.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

// обработчик входящих сообщений в виде строк (SimpleChannelInboundHandler<String>)
public class HandlerString extends SimpleChannelInboundHandler<String> {

    private static final List<Channel> channels = new ArrayList<>();
    private static int newClientIndex = 1;
    private String clientNme;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("клиент подключился: " + ctx);
        channels.add(ctx.channel());
        clientNme = "Client #" + newClientIndex;
        newClientIndex++;
        //рассылка сообщений от имени сервера
        broadcastMessage("SERVER: ", "подключился новый клиент " + clientNme);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("получено сообщение: " + s);
        // для отправки служебных сообщений
        if(s.startsWith("/")){
            // переименование ника пользователя
            if(s.startsWith("/changename")) { // //changename myname1
                String newNickname = s.split("\\s", 2)[1];
                //сообщение о смене ника
                broadcastMessage("SERVER: ", "клиент " + clientNme + " сменил имя " + newNickname);
                clientNme = newNickname;
            }
            return;
        }
       broadcastMessage(clientNme, s);

    }

    // рассылка сообщений
    public void broadcastMessage(String clientNme, String massage){
        String out = String.format("[%s]: %s\n", clientNme, massage);
        for (Channel c : channels) {
            c.writeAndFlush(out);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client " + clientNme + " отвалился ");
        // каналы удалить этого человека из списка
        channels.remove(ctx.channel());
        // рассылка сообщений
        broadcastMessage("SERVER: ", "клиент " + clientNme + " вышел из сети ");
        // закрываем  с ним подключение
        ctx.close();
    }



}

