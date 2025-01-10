package com.train.test.controller;

import com.train.framework.common.utils.PageResult;
import com.train.framework.dict.utils.DictUtils;
import com.train.framework.dict.utils.TransUtils;
import com.train.test.vo.TestInVO;
import com.train.test.vo.TestNoVO;
import com.train.test.vo.TestTableVO;
import com.train.test.vo.TestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 新模块测试
 *

 */
@Slf4j
@RestController
@RequestMapping("new/test")
@Tag(name="新模块测试")
@AllArgsConstructor
public class TestController {

    @GetMapping()
    @Operation(summary = "测试接口")
    public Result<String> test(){

        return Result.ok("测试数据");
    }

    @GetMapping("dict")
    @Operation(summary = "测试字典")
    public Result<String> testDict(){
        String label = DictUtils.transValueToLabel("course_code", "C01");
        log.info("value --> label {} {} -->【{}】","course_code","C01",label);
        String value = DictUtils.transLabelToValue("user_gender", "男");
        log.info("label --> value {} {} -->【{}】","user_gender","男",value);
        label = DictUtils.transValueToLabel("user_gender", "3");
        log.info("value --> label {} {} -->【{}】","user_gender","3",label);
        label = DictUtils.transValueToLabel("user_gender", "3");
        log.info("value --> label {} {} -->【{}】","user_gender","3",label);
        return Result.ok("测试数据2");
    }

    @GetMapping("dictAuto")
    @Operation(summary = "测试自动转换")
    public Result<PageResult<TestVO>> testDictAuto(){
        TestVO vo = new TestVO().setSex("1").setSex2("1").setSex3("1").setSexL("1");
        vo.setTestInVO(new TestInVO().setGender("1"));
        vo.setTestNoVO(new TestNoVO().setGe("1"));
        vo.setTestNoVO2(new TestNoVO().setGe("1"));
        return Result.ok(new PageResult<TestVO>(List.of(vo),1));
    }

    @GetMapping("trans")
    @Operation(summary = "测试数据库转换")
    public Result<String> testTrans(){
        TestTableVO vo = new TestTableVO().setCourseId(1L).setCourseCode("C02").setTeacherCode("lilaoshi");
        log.info("转换单个： {}", TransUtils.transOne(vo, TestTableVO.class));
        List<TestTableVO> list = new ArrayList<>();
        list.add(new TestTableVO().setCourseId(1L).setCourseCode("C02").setTeacherCode("lilaoshi"));
        list.add(new TestTableVO().setCourseId(3L).setCourseCode("C01"));
        list = TransUtils.transList(list, TestTableVO.class);
        log.info("转换列表： {}", list);
        return Result.ok("测试数据库转换");
    }
}
