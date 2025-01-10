package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 教室查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "教室查询")
public class ClassroomQuery extends Query {
    @Schema(description = "教室名称")
    private String classroomName;
    @Schema(description = "教室编码")
    private String classroomCode;
    @Schema(description = "教室状态")
    private String classroomStatus;
    @Schema(description = "忽略排课冲突")
    private Integer ignoreConflict;
}