package com.train.framework.common.utils.holiday;

import java.util.HashSet;
import java.util.Set;

/**
 * 2025假期
 */
public class Date2025 implements DateService {

    /**
     * 手动维护2025年的法定节假日
     *
     * @return
     */
    @Override
    public Set<String> getLegalHoliday() {
        Set<String> holidays = new HashSet<String>();
        //元旦
        holidays.add("20250101");

        //春节
        holidays.add("20250128");
        holidays.add("20250129");
        holidays.add("20250130");
        holidays.add("20250131");
        holidays.add("20250201");
        holidays.add("20250202");
        holidays.add("20250203");
        holidays.add("20250204");
        //清明节
        holidays.add("20250404");
        holidays.add("20250405");
        holidays.add("20250406");
        //劳动节
        holidays.add("20250501");
        holidays.add("20250502");
        holidays.add("20250503");
        holidays.add("20250504");
        holidays.add("20250505");

        //端午节
        holidays.add("20250531");
        holidays.add("20250601");
        holidays.add("20250602");
        //中秋节
        //国庆节
        holidays.add("20251001");
        holidays.add("20251002");
        holidays.add("20251003");
        holidays.add("20251004");
        holidays.add("20251005");
        holidays.add("20251006");
        holidays.add("20251007");
        holidays.add("20251008");
        return holidays;
    }

    /**
     * 手动维护2025年的法定节假日的调休上班工作日
     *
     * @return
     */
    @Override
    public Set<String> getAdjustRestWorkDays() {
        Set<String> restDays = new HashSet<String>();
        //元旦
        //春节
        restDays.add("20250126");
        restDays.add("20250208");
        //清明节
        //劳动节
        restDays.add("20250427");
        //端午节
        //中秋节
        //国庆节
        restDays.add("20250928");
        restDays.add("20251011");

        return restDays;
    }


}