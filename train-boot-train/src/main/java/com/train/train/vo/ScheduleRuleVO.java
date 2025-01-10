package com.train.train.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import com.train.train.constant.TrainConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
public class ScheduleRuleVO {
    /**
     * 固定频率排课 frequency、指定时间排课 appoint
     */
    private String scheduleType = "frequency";
    /**
     * 上课时间范围 使用 - 分割，无空格
     * 12:00-15:30
     */
    private List<String> timeSection = new ArrayList<>();
    //  --------------------固定频率 开始-------------------------
    /**
     * 开始日期 yyyy-MM-dd
     */
    private String beginDate;
    /**
     * 结束日期 yyyy-MM-dd
     */
    private String endDate;
    /**
     * 频率类型 daily 每天、weekly 每周、monthly 每月
     */
    private String frequencyType = "daily";
    /**
     * 如果是每周则填入星期几、每月则填入几号
     */
    private List<Integer> frequencyAppoint = new ArrayList<>();
    /**
     * 排除类型 appoint 排除指定日期 holiday 节假日及指定日期
     */
    private String excludeType = "appoint";
    /**
     * 需要排除的日期  yyyy-MM-dd
     */
    private List<String> excludeDays = new ArrayList<>();
    //  --------------------固定频率 结束-------------------------


    //  --------------------指定日期 开始-------------------------
    /**
     * 指定日期 yyyy-MM-dd
     */
    List<String> appointDays = new ArrayList<>();
    //  --------------------指定日期 结束-------------------------

    public static void main(String[] args) {
        ScheduleRuleVO scheduleRuleVO = new ScheduleRuleVO().setScheduleType(TrainConstant.SCHEDULE_TYPE_FREQUENCY)
                .setTimeSection(Arrays.asList("10:00-12:00")).setFrequencyType("daily");
    }

}
