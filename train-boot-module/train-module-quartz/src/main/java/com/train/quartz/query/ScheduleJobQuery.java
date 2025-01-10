package com.train.quartz.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 定时任务查询
*
*  
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "定时任务查询")
public class ScheduleJobQuery extends Query {
    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务组名")
    private String jobGroup;

    @Schema(description = "状态")
    private Integer status;

}