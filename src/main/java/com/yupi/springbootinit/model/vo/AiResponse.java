package com.yupi.springbootinit.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version v1.0
 * @author OldGj 2024/8/28
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiResponse {

    /**
     * AI生成的图标数据
     */
    private String genChart;

    /**
     * AI生成的结论数据
     */
    private String genResult;

    /**
     * 新生成的图表ID
     */
    private Long chartId;

}
