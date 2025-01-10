package com.train.framework.common.utils.holiday;

import java.util.Set;

public interface DateService {
    /**
     * 法定假期
     * @return
     */
    Set<String> getLegalHoliday();

    /**
     * 法定调休
     * @return
     */
    Set<String> getAdjustRestWorkDays();
}
