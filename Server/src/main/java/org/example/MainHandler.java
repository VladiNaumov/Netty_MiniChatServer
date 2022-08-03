package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// обработчик входящих  сообщений (ChannelInboundHandlerAdapter)
public class MainHandler extends ChannelInboundHandlerAdapter {


    // метод отвечает за событие
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      System.out.println("клиент подключился: ");
    }

    // метод отвечает за чтение информации когда клиент прислал вам какую то информацию чтение происходит в байтах
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        while (buf.readableBytes() > 0){
            // чтение происходит посимвольно
            System.out.println((char)buf.readByte());
        }
        buf.release();
    }

    // метод информирует об исключении
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // распечатывает информацию
        cause.printStackTrace();
        // закрываем  с ним подключение
        ctx.close();
    }
}
