package com.lee.vshare.service.netty;

import com.lee.vshare.service.netty.task.AsyncTask;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: NettyServer
 * @Description: Netty服务端
 * @Version:1.0.0
 */
@Service("nettyServer")
public class NettyServer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int port = 9090; // 设置服务端端口
    private static final int udpPort = 9091; // 设置服务端端口
    private static EventLoopGroup boss = new NioEventLoopGroup(); // 通过nio方式来接收连接
    private static EventLoopGroup work = new NioEventLoopGroup(); // 通过nio方式来处理连接
    private static ServerBootstrap serverBootstrap = new ServerBootstrap();//TCP

    EventLoopGroup group = new NioEventLoopGroup(4);
    Bootstrap bootstrap = new Bootstrap();//UDP

    @Autowired
    private NettyServerFilter nettyServerFilter;

    @Autowired
    private NettyUDPHandler nettyUDPHandler;

    @Autowired
    AsyncTask asyncTask;

    public void run() {
        try {
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024) //设置TCP缓冲区
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024) //设置发送数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024) //设置接受数据缓冲大小
                    .option(ChannelOption.SO_KEEPALIVE, true) //保持连接
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    .childHandler(nettyServerFilter); // 设置过滤器

            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_SNDBUF, 1024) //设置发送数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 1024) //设置接受数据缓冲大小
                    .handler(nettyUDPHandler);


            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();// 服务器绑定端口监听
            logger.info("TCP服务端启动成功,端口是 : " + port);
            Channel channel = channelFuture.channel();

            ChannelFuture udpChannelFuture = bootstrap.bind(udpPort).sync();// 服务器绑定端口监听
            logger.info("UDP服务端启动成功,端口是 : " + udpPort);
            Channel udpChannel = udpChannelFuture.channel();
            asyncTask.setUdpChannel(udpChannel);

            channel.closeFuture().sync();// 监听服务器关闭监听
            udpChannel.closeFuture().sync();// 监听服务器关闭监听
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
            boss.shutdownGracefully();
            work.shutdownGracefully();
            group.shutdownGracefully();
        }
    }

}
