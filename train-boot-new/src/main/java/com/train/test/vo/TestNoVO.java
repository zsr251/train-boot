package com.train.test.vo;

import com.train.framework.dict.annotations.Dict;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TestNoVO {
    @Dict(key = "user_gender")
    private String ge;
}
