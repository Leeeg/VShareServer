package com.lee.vshare.service.util;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-5-25
 * @Time 上午10:38
 */
@Component
@Lazy
public class Utils {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getIpFromChannel(Channel channel){
        InetSocketAddress ipSocket = (InetSocketAddress) channel.remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        logger.info("getIpFromChannel : ip = " + ip);
        return ip;
    }

}
