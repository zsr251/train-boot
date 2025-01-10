package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工资方案
 */
@Data
@Accessors(chain = true)
public class WageRuleVO {
    @Schema(description = "固定工资")
    private BigDecimal fixedSalary = BigDecimal.ZERO;
    @Schema(description = "其他固定工资")
    private List<FixedVO> fixedList = new ArrayList<>();
    @Schema(description = "课程课时提成")
    private List<CourseVO> courseList = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    public static class FixedVO {
        private String code;
        private String name;
        private BigDecimal value;
    }

    @Data
    @Accessors(chain = true)
    public static class CourseVO {
        private String courseCode;
        private String courseName;
        private BigDecimal oneHourAmount;
        private String desc;
    }
}

