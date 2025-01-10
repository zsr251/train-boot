package com.train.test.vo;

import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import lombok.Data;
import lombok.experimental.Accessors;

@DictNeed
@Accessors(chain = true)
@Data
public class TestVO {
    @Dict(key = "user_gender")
    private String sex;
    @Dict(key = "user_gender",ref = "sexL")
    private String sex2;
    private String sexL;
    private String sex3;

    @DictNeed
    private TestInVO testInVO;

    @DictNeed
    private TestNoVO testNoVO;

    private TestNoVO testNoVO2;

}
