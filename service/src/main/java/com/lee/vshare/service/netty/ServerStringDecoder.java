package com.lee.vshare.service.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-5-23
 * @Time 下午1:09
 */
public class ServerStringDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final Charset charset;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServerStringDecoder() {
        this(Charset.defaultCharset());
    }

    public ServerStringDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        logger.info("byteBuf  >>>> " + byteBuf.readableBytes());
        list.add(byteBuf.toString(this.charset));
    }
}
