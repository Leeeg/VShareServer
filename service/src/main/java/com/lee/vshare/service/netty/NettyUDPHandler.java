package com.lee.vshare.service.netty;

import com.lee.vshare.service.netty.task.NettyTask;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Title: NettyServerHandler
 * @Description: 服务端业务逻辑
 * @Version:1.0.0
 */
@Component
@ChannelHandler.Sharable
public class NettyUDPHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NettyTask nettyTask;

    /**
     * 表示服务端与客户端连接建立
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerAdded");
        super.handlerAdded(ctx);
    }

    /**
     * 有连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("handlerRemoved");
        super.handlerRemoved(ctx);
    }

    /**
     * 连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive");
        super.channelInactive(ctx);
    }

    /**
     * 超时处理
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        logger.info("userEventTriggered");
        super.channelInactive(ctx);
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exceptionCaught  出现异常 : " + cause);
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 业务逻辑处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        InetSocketAddress sender = datagramPacket.sender();
        logger.info("channelRead0 : sender ： " + sender);
        ByteBuf byteBuf = datagramPacket.copy().content();
        logger.info("byteBuf : " + byteBuf.readableBytes());

        if (byteBuf.readableBytes() == 2){
            String id = new String(ByteBufUtil.getBytes(byteBuf));
            logger.info("id : " + id);
            logger.info("sender : " + sender);
            nettyTask.getUdpAddr().put(id, sender);
        } else {
            nettyTask.dispenseAudio(sender, byteBuf);
        }
    }

}
