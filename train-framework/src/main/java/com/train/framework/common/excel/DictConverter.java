package com.train.framework.common.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.utils.DictUtils;

/**
 * String类型 字典翻译
 */
public class DictConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Dict dict = excelContentProperty.getField().getAnnotation(Dict.class);
        String str = StrUtil.trim(cellData.getStringValue());
        if (dict == null) {
            // 原样输出
            return str;
        }
        String key = dict.key();
        return DictUtils.transLabelToValue(key, str);
    }

    @Override
    public WriteCellData<String> convertToExcelData(String value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (StrUtil.isBlank(value)) {
            // 原样输出
            return new WriteCellData<>(value);
        }
        Dict dict = excelContentProperty.getField().getAnnotation(Dict.class);
        if (dict == null) {
            // 原样输出
            return new WriteCellData<>(value);
        }
        String key = dict.key();
        return new WriteCellData<>(DictUtils.transValueToLabel(key, value));
    }
}
