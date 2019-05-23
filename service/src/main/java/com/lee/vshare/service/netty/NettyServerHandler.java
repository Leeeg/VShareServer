package com.lee.vshare.service.netty;

import com.lee.vshare.service.netty.protobuf.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Title: NettyServerHandler
 * @Description: 服务端业务逻辑
 * @Version:1.0.0
 */
@Service("nettyServerHandler")
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //保留所有与服务器建立连接的channel对象，这边的GlobalEventExecutor在写博客的时候解释一下，看其doc
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 表示服务端与客户端连接建立
     *
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
        channelGroup.writeAndFlush(" 【服务器】 -" + channel.remoteAddress() + " 加入\n");
        channelGroup.add(channel);
        super.handlerAdded(ctx);
    }

    /**
     * 有连接断开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(" 【服务器】 -" + channel.remoteAddress() + " 离开\n");

        //验证一下每次客户端断开连接，连接自动地从channelGroup中删除调。
        logger.info("channelGroup : " + channelGroup.size());
        //当客户端和服务端断开连接的时候，下面的那段代码netty会自动调用，所以不需要人为的去调用它
        //channelGroup.remove(channel);
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
                UserInfo.UserMsg userMsg = UserInfo.UserMsg.newBuilder().setId(1).setAge(18).setName("lee").setState(0).build();
                ctx.writeAndFlush(userMsg);

                channelGroup.remove(channel);
                channel.close();
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
        logger.info("收到" + channel.remoteAddress() + " 发送的的消息 : " + msg);

        try {
            if (msg instanceof UserInfo.UserMsg) {
                UserInfo.UserMsg userState = (UserInfo.UserMsg) msg;
                if (userState.getState() == 1) {
                    System.out.println("客户端业务处理成功!");
                } else if(userState.getState() == 2){
                    System.out.println("接受到客户端发送的心跳!  remoteAddress : " + ctx.channel().remoteAddress() + " localAddress : " + ctx.channel().localAddress());
                    UserInfo.UserMsg userMsg = UserInfo.UserMsg.newBuilder().setId(1).setAge(18).setName("Lee").setState(0).build();
                    ctx.writeAndFlush(userMsg);
                }else{
                    System.out.println("未知命令!");
                }
            }

//            channelGroup.forEach(ch -> {
//                if (channel != ch) {
//                    ch.writeAndFlush(channel.remoteAddress() + " 发送的消息:" + msg + " \n");
//                } else {
//                    ch.writeAndFlush(" 【自己】" + msg + " \n");
//                }
//            });

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
