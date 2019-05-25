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
import java.util.function.Consumer;

import static com.lee.vshare.service.netty.contain.NMsgContainer.MSG_USER_HEART_BEAT;
import static com.lee.vshare.service.netty.contain.NMsgContainer.MSG_USER_MESSAGE_SUCCESS;


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

    public void setUdpChannel(Channel udpChannel) {
        this.udpChannel = udpChannel;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    @Async("asyncPoolTaskExecutor")
    public void dispenseAudio(String formAddress, ByteBuf byteBuf) throws InterruptedException {
        logger.info("dispenseAudio started.");
        long start = System.currentTimeMillis();
        logger.info("转发Audio给 " + channelGroup.size() + " 人");
        channelGroup.forEach(channel -> {
            String toAddress = channel.remoteAddress().toString();
            if (!formAddress.equals(toAddress)) {
                DatagramPacket dispensePacket = new DatagramPacket(byteBuf, new InetSocketAddress(toAddress, 9091));
                udpChannel.writeAndFlush(dispensePacket);
            }
        });
        long end = System.currentTimeMillis();
        logger.info("dispenseAudio finished, time elapsed: {} ms." + "\n", end - start);
    }

    @Async("asyncPoolTaskExecutor")
    public void dispenseMsg(Channel formChannel, NettyMessage.NettyMsg readNettyMsg) throws InterruptedException {
        logger.info("dispenseMsg started.");
        long start = System.currentTimeMillis();
        logger.info("转发Msg给 " + channelGroup.size() + " 人");
        channelGroup.forEach(channel -> {
            if (formChannel != channel) {
                channel.writeAndFlush(readNettyMsg);
            } else {
                NettyMessage.NettyMsg pengNettyMsg = NettyMessage.NettyMsg.newBuilder()
                        .setMsgType(MSG_USER_MESSAGE_SUCCESS)
                        .setMsgContent("success")
                        .build();
                channel.writeAndFlush(pengNettyMsg);
            }
        });
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

        channelGroup.forEach(channel1 -> {
            String host = utils.getIpFromChannel(channel);
            System.out.println("发送udp : " + host);
            DatagramPacket dispensePacket = new DatagramPacket(Unpooled.buffer(8),
                    new InetSocketAddress("192.168.32.195", 9091));
            udpChannel.writeAndFlush(dispensePacket);
        });

    }

}
