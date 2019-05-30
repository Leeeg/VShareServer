package com.lee.vshare.service.netty.task;

import com.lee.vshare.service.netty.protobuf.NettyMessage;
import com.lee.vshare.service.util.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static com.lee.vshare.service.netty.contain.NMsgContainer.MSG_USER_HEART_BEAT;


/**
 * @title:
 * @gmail jefferyleeeg@gmail.com
 * @author:Lee
 * @date: 2018/12/25
 */
@Component
@Lazy
public class NettyTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Channel udpChannel;

    @Autowired
    Utils utils;

    //保留所有与服务器建立连接的channel对象，这边的GlobalEventExecutor在写博客的时候解释一下，看其doc
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Map<String, InetSocketAddress> udpAddr = new HashMap<>();

    public void setUdpChannel(Channel udpChannel) {
        this.udpChannel = udpChannel;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public Map<String, InetSocketAddress> getUdpAddr() {
        return udpAddr;
    }

//    @Async("asyncPoolTaskExecutor")
    public void dispenseAudio(InetSocketAddress sender, ByteBuf byteBuf) throws InterruptedException {
        logger.info("dispenseAudio started.");
        long start = System.currentTimeMillis();
        logger.info("转发Audio给 " + (udpAddr.size()) + " 人" + " formAddress = " + sender);
        if (udpAddr.size() > 0) {
            udpAddr.values().forEach(address -> {
                if (sender != address) {
                    ByteBuf buf = Unpooled.copiedBuffer(byteBuf);
                    logger.info(" send >>>>>>  toAddress ： " + address);
                    DatagramPacket dispensePacket = new DatagramPacket(buf, address);
                    udpChannel.writeAndFlush(dispensePacket);
                }
            });
        }
        long end = System.currentTimeMillis();
        logger.info("dispenseAudio finished, time elapsed: {} ms." + "\n", end - start);
    }

    @Async("asyncPoolTaskExecutor")
    public void dispenseMsg(Channel formChannel, NettyMessage.NettyMsg readNettyMsg) throws InterruptedException {
        logger.info("dispenseMsg started.");
        long start = System.currentTimeMillis();
        logger.info("转发Msg给 " + channelGroup.size() + " 人");
        channelGroup.writeAndFlush(readNettyMsg);
        long end = System.currentTimeMillis();
        logger.info("dispenseMsg finished, time elapsed: {} ms." + "\n", end - start);
    }

    @Async("asyncPoolTaskExecutor")
    public void peng(Channel channel) {
        logger.info("peng started.");
        long start = System.currentTimeMillis();
        NettyMessage.NettyMsg pengNettyMsg = NettyMessage.NettyMsg.newBuilder()
                .setMsgType(MSG_USER_HEART_BEAT)
                .build();
        channel.writeAndFlush(pengNettyMsg);
        long end = System.currentTimeMillis();
        logger.info("peng finished, time elapsed: {} ms." + "\n", end - start);
    }

    @Async("asyncPoolTaskExecutor")
    public void sendMsg(Channel channel, int msgType) {
        logger.info("sendMsg start");
        long start = System.currentTimeMillis();
        NettyMessage.NettyMsg nettyMsg = NettyMessage.NettyMsg.newBuilder()
                .setMsgType(msgType)
                .build();
        channel.writeAndFlush(nettyMsg);
        long end = System.currentTimeMillis();
        logger.info("sendMsg finished, time elapsed: {} ms." + "\n", end - start);
    }

    @Async("asyncPoolTaskExecutor")
    public void sendMsgToAll(int msgType) {
        logger.info("sendMsgToAll start");
        long start = System.currentTimeMillis();
        NettyMessage.NettyMsg nettyMsg = NettyMessage.NettyMsg.newBuilder()
                .setMsgType(msgType)
                .build();
        channelGroup.writeAndFlush(nettyMsg);
        long end = System.currentTimeMillis();
        logger.info("sendMsgToAll finished, time elapsed: {} ms." + "\n", end - start);
    }


}
