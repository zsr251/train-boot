package com.train.framework.common.excel;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.train.framework.common.exception.ExcelException;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用Excel读取监听
 *
 * @author jasonzhu
 * @date 2020/6/26
 */
@Slf4j
public class CommonDataListener<M, T> extends AnalysisEventListener<T> {
    private M service;
    /**
     * 成功条数
     */
    private int successCount;
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    List<T> list = new ArrayList<T>();

    /**
     * 如果指定方法 则为调用service的指定方法 否则调用批次保存方法
     */
    private String methodName;

    /**
     * 插入方法
     *
     * @param service    进行保存处理的服务类
     * @param methodName 进行保存处理的方法 参数为 list 返回值为int
     */
    public CommonDataListener(M service, String methodName) {
        this.service = service;
        this.methodName = methodName;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        if (t != null) {
            log.debug("解析到一条数据:{}", JsonUtils.toJsonString(t));
            list.add(t);
        }
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成！");
    }


    /**
     * 加上数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        try {
            int i = ReflectUtil.invoke(service, methodName, list);
            successCount += i;
        } catch (Exception e) {
            Throwable throwable = ExceptionUtil.getCausedBy(e, ExcelException.class, ServerException.class);
            if (throwable != null) {
                throw new ExcelException(throwable.getMessage(), e);
            }
            log.error("批量保存异常:{}", e.getMessage(), e);
            return;
        }
        log.info("存储数据库成功！");
    }

    public int getSuccessCount() {
        return successCount;
    }
}
