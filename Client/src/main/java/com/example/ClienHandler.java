package com.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClienHandler extends SimpleChannelInboundHandler<String> {
    private CallBack onMassegeReceivedCallback;

    public ClienHandler(CallBack onMassegeReceivedCallback) {
        this.onMassegeReceivedCallback = onMassegeReceivedCallback;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if(onMassegeReceivedCallback != null){
            onMassegeReceivedCallback.callBack(s);
        }
    }
}
