package com.train.framework.dict.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 字典值
 */
@Data
@AllArgsConstructor
public class DictDTO {
    private String value;
    private String label;
}
