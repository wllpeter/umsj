package com.tuniu.bi.umsj.base.executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 异步执行器
 *
 * @author zhangwei21
 */
@Component
public class AsyncExecutorService {

    @Value("${executorPool.executor-max-pool-size}")
    private int executorMaxPoolSize;

    @Value("${executorPool.executor-core-size}")
    private int executorCoreSize;

    @Value("${executorPool.keep-alive-seconds}")
    private int keepAliveSeconds;

    @Value("${executorPool.queue-capacity}")
    private int queueCapacity;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = new ThreadPoolExecutor(executorCoreSize, executorMaxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new LinkedBlockingDeque<>(queueCapacity));
    }

    /**
     * 提交异步任务(无返回值)
     */
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    /**
     * 提交异步任务(有返回值)
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }
}
