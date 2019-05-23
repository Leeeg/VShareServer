package com.lee.vshare.service.netty;

import com.lee.vshare.service.netty.protobuf.UserInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
  * 
 * @Title: HelloServerInitializer
 * @Description: Netty 服务端过滤器
 * @Version:1.0.0  
  */
@Component
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NettyServerHandler nettyServerHandler;
	
     @Override
     protected void initChannel(SocketChannel ch) throws Exception {
         ChannelPipeline ph = ch.pipeline();

         //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
         ph.addLast(new IdleStateHandler(8, 0, 0, TimeUnit.SECONDS));

         // 解码和编码，应和客户端一致
         //传输的协议 Protobuf
         ph.addLast(new ProtobufVarint32FrameDecoder());
         ph.addLast(new ProtobufDecoder(UserInfo.UserMsg.getDefaultInstance()));
         ph.addLast(new ProtobufVarint32LengthFieldPrepender());
         ph.addLast(new ProtobufEncoder());

         //分割接收到的Bytebu，根据指定的分割符
         ph.addLast("nettyServerHandler", nettyServerHandler);

         //业务逻辑实现类
     }
 }
