package com.cyber.authing.log.task;

import com.cyber.authing.log.aspect.ActivityLogAspect;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ActivityLogTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Integer COMMIT_COUNT = 50;

    @PostConstruct
    public void init() {
        cacheTask();
    }

    private void cacheTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("saveOperateLog"));
        executor.scheduleAtFixedRate(() -> {
            try {
                saveLog();
            } catch (Exception exception) {
                logger.error("refresh user project catch exception", exception);
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void saveLog() throws Exception {
        logger.info("Begin batch insert activity log...");
        int count = 0;
        while (!ActivityLogAspect.queue.isEmpty()) {
            //TODO 入库
        }
        logger.info("Batch insert activity log...save record count:{}", count);
    }
}
