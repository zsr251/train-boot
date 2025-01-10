package com.train.train.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonStatusEnum {
    WAIT("wait", "未开始"),
    DONE("done", "已完成"),
    CANCEL("cancel", "已取消"),
    TRANSFER("transfer", "已调课");

    public static LessonStatusEnum getByCode(String code) {
        for (LessonStatusEnum value : LessonStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return LessonStatusEnum.WAIT;
    }


    private final String code;
    private final String label;
}
