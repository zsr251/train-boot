package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 班级查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "班级查询")
public class ClassInfoQuery extends Query {
    @Schema(description = "班级编码")
    private String classCode;

    @Schema(description = "班级名称")
    private String className;

    @Schema(description = "班级类型一对一or一对多")
    private String classType;

    @Schema(description = "关联课程")
    private String courseCode;

    @Schema(description = "管理老师")
    private String managerTeacher;

    @Schema(description = "班级状态")
    private String classStatus;
}