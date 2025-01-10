package com.train.test.vo;

import com.train.framework.dict.annotations.TransTable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestTableVO {
    @TransTable(table = "tt_course", fields = "course_name", refs = "courseIdToName")
    private Long courseId;
    private String courseIdToName;
    @TransTable(table = "tt_course", key = "course_code", fields = {"course_name", "course_desc"}, refs = {"courseCodeToName","courseCodeToDesc"})
    private String courseCode;
    private String courseCodeToName;
    private String courseCodeToDesc;
    @TransTable(table = "tt_teacher", key = "teacher_code", fields = {"user_id", "teacher_name"}, refs = {"teacherCodeToId","teacherCodeToName"})
    private String teacherCode;
    private Long teacherCodeToId;
    private String teacherCodeToName;
}
