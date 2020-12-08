package cn.shaikuba.mock.config;

import cn.shaikuba.mock.common.thread.PrefixThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Ray.Xu
 * @classname ExecutorConfig
 * @description TODO
 * @date 9/14/2020 5:31 PM
 */
@Slf4j
@Configuration
public class ExecutorConfig implements AsyncConfigurer {

    @Bean
    public ThreadFactory callbackThreadFactory() {
        return new PrefixThreadFactory("Mock-Callback");
    }

    @Bean
    public ThreadPoolTaskExecutor callbackTaskExecutor(@Qualifier("callbackThreadFactory") ThreadFactory threadFactory) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setRejectedExecutionHandler((r, e) ->
                threadFactory.newThread(r)
                        .start()
        );

        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(400);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(5);

        //executor.setThreadNamePrefix("Batch-Deduct-Result-Generator-");
        executor.setThreadPriority(Thread.NORM_PRIORITY);
        executor.initialize();

        return executor;

    }

    @Override
    public Executor getAsyncExecutor() {
        return callbackTaskExecutor(callbackThreadFactory());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(String.format("Execute task error: %s", method), ex);
    }
        
}
