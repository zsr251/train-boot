package com.train.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 邮件格式枚举
 *

 */
@Getter
@AllArgsConstructor
public enum MailFormatEnum {
    /**
     * 纯文本
     */
    TEXT,
    /**
     * HTML
     */
    HTML,
    /**
     * 模板
     */
    TEMPLATE;
}
