package com.lee.vshare.common.TaskExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @title:
 * @gmail jefferyleeeg@gmail.com
 * @author:Lee
 * @date: 2018/12/25
 */
@Component
public class AsyncTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("asyncPoolTaskExecutor")
    public Future<String> doTask1() throws InterruptedException{
        logger.info("Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();

        logger.info("Task1 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<>("Task1 accomplished!");
    }

}
