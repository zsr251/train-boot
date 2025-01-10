package com.train.train.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonMemberTypeEnum {
    AUDITION("audition", "试听学生"),
    STUDENT("student", "正式学生");
    public static LessonMemberTypeEnum getByCode(String code) {
        for (LessonMemberTypeEnum value : LessonMemberTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return LessonMemberTypeEnum.STUDENT;
    }

    private final String code;
    private final String label;
}
