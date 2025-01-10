package com.train.framework.common.utils.holiday;

import java.util.HashSet;
import java.util.Set;

/**
 * 2024假期
 */
public class Date2024 implements DateService {

    /**
     * 手动维护2024年的法定节假日
     *
     * @return
     */
    @Override
    public Set<String> getLegalHoliday() {
        Set<String> holidays = new HashSet<String>();
        //元旦
        holidays.add("20231230");
        holidays.add("20231231");
        holidays.add("20240101");

        //春节
        holidays.add("20240210");
        holidays.add("20240211");
        holidays.add("20240212");
        holidays.add("20240213");
        holidays.add("20240214");
        holidays.add("20240215");
        holidays.add("20240216");
        holidays.add("20240217");
        //清明节
        holidays.add("20240404");
        holidays.add("20240405");
        holidays.add("20240406");
        //劳动节
        holidays.add("20240501");
        holidays.add("20240502");
        holidays.add("20240503");
        holidays.add("20240504");
        holidays.add("20240505");

        //端午节
        holidays.add("20240608");
        holidays.add("20240609");
        holidays.add("20240610");
        //中秋节
        holidays.add("20240915");
        holidays.add("20240916");
        holidays.add("20240917");
        //国庆节
        holidays.add("20241001");
        holidays.add("20241002");
        holidays.add("20241003");
        holidays.add("20241004");
        holidays.add("20241005");
        holidays.add("20241006");
        holidays.add("20241007");
        return holidays;
    }

    /**
     * 手动维护2024年的法定节假日的调休上班工作日
     *
     * @return
     */
    @Override
    public Set<String> getAdjustRestWorkDays() {
        Set<String> restDays = new HashSet<String>();
        //元旦
        //春节
        restDays.add("20240204");
        restDays.add("20240218");
        //清明节
        restDays.add("20240407");
        //劳动节
        restDays.add("20240428");
        restDays.add("20240511");
        //端午节
        //中秋节
        restDays.add("20240914");
        //国庆节
        restDays.add("20240929");
        restDays.add("20241012");

        return restDays;
    }


}