package com.yupi.springbootinit.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * @author <a href="https://blog.csdn.net/guojiaqi_">oldGj</a>
 * @from <a href="https://github.com/guoJiaQi-123/TYUT-SmartViewBI-backend">GitHub地址</a>
 */
@Data
public class GenChartByAIRequest implements Serializable {

    /**
     * 图表名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;


    /**
     * 图标类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}