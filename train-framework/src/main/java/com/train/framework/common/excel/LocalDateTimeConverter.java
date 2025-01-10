package com.train.framework.common.excel;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换
 *
 * @author eden
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String dateString = cellData.getStringValue();
        if (StrUtil.isBlank(dateString)) {
            return null;
        }
        DateTimeFormat df = contentProperty.getField().getAnnotation(DateTimeFormat.class);
        String pattern = DateUtils.DATE_TIME_PATTERN;
        if (df != null) {
            pattern = df.pattern();
        }
        return LocalDateTimeUtil.parse(dateString, DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public WriteCellData<LocalDateTime> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>();
        }
        JsonFormat jf = contentProperty.getField().getAnnotation(JsonFormat.class);
        String pattern = DateUtils.DATE_TIME_PATTERN;
        if (jf != null) {
            pattern = jf.pattern();
        }
        String dateValue = LocalDateTimeUtil.format(value, pattern);
        return new WriteCellData<>(dateValue);
    }
}
