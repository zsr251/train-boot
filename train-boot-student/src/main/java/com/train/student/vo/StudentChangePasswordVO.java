package com.train.student.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "学员变更密码")
public class StudentChangePasswordVO {
    @Schema(description = "旧登录密码")
    String password;
    @Schema(description = "新登录密码")
    String newPassword;
}
