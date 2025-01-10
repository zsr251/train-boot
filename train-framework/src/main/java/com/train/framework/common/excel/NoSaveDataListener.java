package com.train.framework.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.train.framework.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 不保存到数据库
 *
 * @author jasonzhu
 * @date 2020/6/26
 */
@Slf4j
public class NoSaveDataListener<T> extends AnalysisEventListener<T> {
    private List<T> list = new ArrayList<T>();

    public List<T> getList() {
        return list;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        log.debug("解析到一条数据:{}", JsonUtils.toJsonString(t));
        list.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }
}
