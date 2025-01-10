package com.train.framework.common.excel;

import lombok.Data;

@Data
public class ExcelSheet {
    /**
     * SHEET名称
     */
    private String sheetName;
    /**
     * 对象Class
     */
    private Class clazz;
    /**
     * 回调方法
     */
    private String callBackMethodName;

    public ExcelSheet(String sheetName, Class clazz, String callBackMethodName) {
        this.sheetName = sheetName;
        this.clazz = clazz;
        this.callBackMethodName = callBackMethodName;
    }
}
