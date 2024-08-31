package com.yupi.springbootinit.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @version v1.0
 * @author OldGj 2024/8/30
 * @apiNote ThreadPoolExecutor 配置类
 */
@Configuration
public class ThreadPoolExecutorConfig {

    /**
     * 线程池配置
     * @return
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactory() {
            int count = 1;

            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("线程" + count);
                count++;
                return thread;
            }
        };
        /*
            线程池参数：
                corePoolSize：核心线程数=》我们的系统应该能同时工作的线程数
                maximumPoolSize：极限情况下，线程池中的最大线程数
                keepAliveTime：非核心线程数在空闲状态下的存活时间
                unit：控制上面存活时间的单位
                workQueue：用于存放给线程执行的任务，存在一个队列的长度【一定要指定】
                threadFactory：控制每个线程的生成、线程的属性（比如线程名）
                RejectedExecutionHandler：拒绝策略，任务队列满的时候，我们采取什么措施，比如抛异常、自定义策略
         */
        return new ThreadPoolExecutor(
                2,
                4,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                threadFactory);
    }

}
