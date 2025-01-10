package com.train.train.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonArrivalStatusEnum {
    WAIT("wait","等待上课"),
    ARRIVED("arrived","已到课"),
    DELAY("delay","迟到"),
    ABSENT("absent","旷课"),
    LEAVE("leave","请假")
    ;
    public static LessonArrivalStatusEnum getByCode(String code) {
        for (LessonArrivalStatusEnum value : LessonArrivalStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return LessonArrivalStatusEnum.WAIT;
    }

    private final String code;
    private final String label;
}
