package com.train.framework.common.excel;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.train.framework.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 增加最大长度限制
 */
@Slf4j
public class NoSaveLimitDataListener<T> extends AnalysisEventListener<T> {
    private List<T> list = new ArrayList<T>();

    /**
     * 读取最大条数 0不限制
     */
    private int maxSize = 0;

    public List<T> getList() {
        return list;
    }

    public NoSaveLimitDataListener(Integer maxSize){
        if(maxSize != null && maxSize > 0){
            this.maxSize = maxSize;
        }
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

    @Override
    public boolean hasNext(AnalysisContext context) {
        log.debug("hasNext..");
        if(maxSize > 0 && CollUtil.isNotEmpty(list) && list.size() >= maxSize){
            //超过最大长度，读取结束
            return false;
        }
        return super.hasNext(context);
    }
}
