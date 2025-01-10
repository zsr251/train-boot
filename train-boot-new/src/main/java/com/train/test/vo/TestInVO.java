package com.train.test.vo;

import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import lombok.Data;
import lombok.experimental.Accessors;

@DictNeed
@Accessors(chain = true)
@Data
public class TestInVO {
    @Dict(key = "user_gender")
    private String gender;
}
