package com.train.student.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "学员登录")
public class StudentLoginVO {
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "学员登录密码")
    private String password;
}
