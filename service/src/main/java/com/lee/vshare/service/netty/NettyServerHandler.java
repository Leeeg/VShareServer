package com.lee.vshare.service.netty;

import com.lee.vshare.service.netty.protobuf.NettyMessage;
import com.lee.vshare.service.netty.task.AsyncTask;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.lee.vshare.service.netty.contain.NMsgContainer.*;

/**
 * @Title: NettyServerHandler
 * @Description: 服务端业务逻辑
 * @Version:1.0.0
 */
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AsyncTask asyncTask;

    /**
     * 表示服务端与客户端连接建立
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("新连接的客户端地址 : " + channel.remoteAddress());
        /**
         * 调用channelGroup的writeAndFlush相当于channelGroup中的每个channel都writeAndFlush
         * 先去广播，再将自己加入到channelGroup中
         */
        NettyMessage.NettyMsg addNettyMsg = NettyMessage.NettyMsg.newBuilder()
                .setMsgType(MSG_USER_UP_LINE)
                .setMsgContent(channel.remoteAddress() + " 上线")
                .build();
        asyncTask.getChannelGroup().writeAndFlush(addNettyMsg);
        asyncTask.getChannelGroup().add(channel);
        super.handlerAdded(ctx);
    }

    /**
     * 有连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //验证一下每次客户端断开连接，连接自动地从channelGroup中删除调。
        logger.info("有客户端离开 剩余在线 : " + asyncTask.getChannelGroup().size());
        NettyMessage.NettyMsg addNettyMsg = NettyMessage.NettyMsg.newBuilder()
                .setMsgType(MSG_USER_DOWN_LINE)
                .setMsgContent(channel.remoteAddress() + " 下线")
                .build();
        asyncTask.getChannelGroup().writeAndFlush(addNettyMsg);
        //当客户端和服务端断开连接的时候，下面的那段代码netty会自动调用，所以不需要人为的去调用它
        //NettyServer.getChannelGroup().remove(channel);
        super.handlerRemoved(ctx);
    }

    /**
     * 连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info(channel.remoteAddress() + " 上线");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info(channel.remoteAddress() + " 下线");
        super.channelInactive(ctx);
    }

    /**
     * 超时处理
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        Channel channel = ctx.channel();
        logger.info(channel.remoteAddress() + "超时");
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.READER_IDLE.equals(event.state())) { // 如果读通道处于空闲状态，说明没有接收到心跳命令
                logger.info("peng >>> ");
                NettyMessage.NettyMsg nettyMsg = NettyMessage.NettyMsg.newBuilder()
                        .setMsgType(MSG_USER_TIME_OUT)
                        .setMsgContent("time out")
                        .build();
                ctx.writeAndFlush(nettyMsg);

//                asyncTask.getChannelGroup().remove(channel);
//                channel.close();
            }
        }
        super.channelInactive(ctx);
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        logger.info(channel.remoteAddress() + " 出现异常 " + cause);
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        logger.info("收到 " + channel.remoteAddress() + "发来的消息");
        try {
            if (msg instanceof NettyMessage.NettyMsg) {
                NettyMessage.NettyMsg readNettyMsg = (NettyMessage.NettyMsg) msg;
                logger.info("内容 " + readNettyMsg.toString());
                if (readNettyMsg.getMsgType() == MSG_USER_HEART_BEAT) {
                    System.out.println("客户端发送的心跳  remoteAddress : " + channel.remoteAddress() + " localAddress : " + channel.localAddress());
                    asyncTask.peng(channel);
                } else if (readNettyMsg.getMsgType() == MSG_USER_BUSINESS) {
                    asyncTask.dispenseMsg(channel, readNettyMsg);
                } else {
                    System.out.println("未知命令!");
                }
            } else {
                System.out.println("无效消息!");
            }

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
