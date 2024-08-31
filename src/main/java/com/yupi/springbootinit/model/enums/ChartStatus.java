package com.yupi.springbootinit.model.enums;

import lombok.Getter;

@Getter
public enum ChartStatus {

    /**
     * 执行等待
     */
    CHART_WAITE(0, "wait"),
    /**
     * 执行中
     */
    CHART_RUNNING(1, "running"),
    /**
     * 执行失败
     */
    CHART_FAILED(2, "failed"),
    /**
     * 执行成功
     */
    CHART_SUCCEED(3, "succeed");

    private final Integer stateCode;
    private final String stateMess;


    ChartStatus(Integer stateCode, String stateMess) {
        this.stateCode = stateCode;
        this.stateMess = stateMess;
    }
}
