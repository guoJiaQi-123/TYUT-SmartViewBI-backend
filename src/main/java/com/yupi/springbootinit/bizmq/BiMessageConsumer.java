package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.constant.MqConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.model.enums.ChartStatus;
import com.yupi.springbootinit.service.ChartService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version v1.0
 * @author OldGj 2024/9/2
 * @apiNote 消息消费者
 */
@Component
@Slf4j
public class BiMessageConsumer {

    @Resource
    private ChartService chartService;

    @Resource
    private AiManager aiManager;

    @SneakyThrows
    @RabbitListener(queues = MqConstant.BI_QUEUE, ackMode = "MANUAL")
    private void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message -> {}", message);

        if (StringUtils.isBlank(message)) {
            // 参数二：批量处理
            // 参数三：重新放回到队列
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "消息为null");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if (chart == null) {
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "图表为null");
        }
        // 执行中
        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setState(ChartStatus.CHART_RUNNING.getStateCode());
        boolean updateChartBool = chartService.updateById(updateChart);
        if (!updateChartBool) {
            handleChartUpdateError(chart.getId(), "更新图表执行中状态失败");
            return;
        }
        // 鱼聪明AI
        String aiResponse = aiManager.doChat(CommonConstant.AI_MODEL_ID, buildUserInput(chart));
        String[] split = aiResponse.split("【【【【【"); // 按照分隔符拆分
        // 校验
        if (split.length < 3) {
            handleChartUpdateError(chart.getId(), "AI 生成错误");
            return;
        }
        String genChart = split[1].trim(); // 图标数据
        String genResult = split[2].trim(); // 结论数据
        // 执行成功
        Chart updateChartRes = new Chart();
        updateChartRes.setId(chart.getId());
        updateChartRes.setGenChart(genChart);
        updateChartRes.setGenResult(genResult);
        updateChartRes.setState(ChartStatus.CHART_SUCCEED.getStateCode());
        boolean updateChartResBool = chartService.updateById(updateChartRes);
        if (!updateChartResBool) {
            handleChartUpdateError(chart.getId(), "更新图表成功状态失败");
        }


        channel.basicAck(deliveryTag, false);
    }

    /**
     * 构建用户输入
     * @param chart
     * @return
     */
    private String buildUserInput(Chart chart) {
        String goal = chart.getGoal();
        String chartType = chart.getChartType();
        String csvData = chart.getChartData();

        // 构造用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");

        // 拼接分析目标
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据：").append("\n");
        userInput.append(csvData).append("\n");
        return userInput.toString();
    }

    /**
     * 统一更新图表生成失败（各种原因）
     * @param chartId
     * @param execMessage
     */
    private void handleChartUpdateError(long chartId, String execMessage) {
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setState(ChartStatus.CHART_FAILED.getStateCode());
        updateChartResult.setExecMessage(execMessage);
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult) {
            log.error("更新图表失败状态失败" + chartId + "," + execMessage);
        }
    }
}
