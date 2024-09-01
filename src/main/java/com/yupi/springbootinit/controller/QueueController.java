package com.yupi.springbootinit.controller;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @version v1.0
 * @author OldGj 2024/8/30
 * @apiNote 线程池测试controller
 * 通过测试，可以得出线程池的工作流程：<br></br>
 * 1、当任务提交到线程池时，如果当前运行的线程数小于核心线程数，会创建新的核心线程来执行任务。<br></br>
 * 2、如果任务数大于核心线程数，新提交的任务会被放入任务队列等待核心线程空闲时来执行。<br></br>
 * 3、如果任务数大于核心线程数加上任务队列的容量，并且当前线程数小于最大线程数，会创建新的非核心线程来执行任务。<br></br>
 * 4、如果任务数大于最大线程数加上任务队列的容量，此时线程全部繁忙且队列也满了，线程池会根据拒绝策略来处理新提交的任务。
 */
@RestController
@RequestMapping("queue")
@Slf4j
public class QueueController {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;


    /**
     * 将任务加入到线程池中
     */
    @GetMapping("/add")
    public void add(String name) {
        // 用于异步执行一个没有返回值的任务
        // 参数一：待执行的任务
        // 参数二：指定的线程池
        CompletableFuture.runAsync(() -> {
            log.info("任务执行中：" + name + " 执行人：" + Thread.currentThread().getName());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, threadPoolExecutor);
    }

    /**
     * 获取当前线程池的状态信息
     */
    @GetMapping("/get")
    public String get() {
        Map<String, Object> map = new HashMap<>();
        int size = threadPoolExecutor.getQueue().size(); // 队列长度
        map.put("队列长度：", size);
        long taskCount = threadPoolExecutor.getTaskCount(); // 任务总数
        map.put("任务总数", taskCount);
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount(); // 已完成任务
        map.put("已完成任务数", completedTaskCount);
        int activeCount = threadPoolExecutor.getActiveCount();// 正在工作的线程数
        map.put("正在工作的线程数", activeCount);
        return JSONUtil.toJsonStr(map);
    }

}
