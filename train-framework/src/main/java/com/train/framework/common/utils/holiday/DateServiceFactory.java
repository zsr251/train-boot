package com.train.framework.common.utils.holiday;

import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Set;

/**
 * 假期工厂
 */
public class DateServiceFactory {
    public static DateService getDateByYear(int year) {

        switch (year) {
            case 2025: {
                return new Date2025();
            }
            case 2024: {
                return new Date2024();
            }

            default:
                //省略
                break;
        }

        return null;
    }

    /**
     * 获取日期字符串
     *
     * @param year
     * @return
     */
    public static String getHStr(int year) {
        int allDay = DateUtil.lengthOfYear(year);
        char[] hoc = new char[allDay];
        Date first = DateUtil.parseDate(year + "-01-01");
        for (int i = 0; i < allDay; i++) {
            // 获取周几
            int w = DateUtil.dayOfWeek(DateUtil.offsetDay(first, i));
            // 周末
            if (w == 1 || w == 7) {
                hoc[i] = '1';
            } else {
                hoc[i] = '0';
            }
        }
        // 获取假期类
        DateService dateService = getDateByYear(year);
        if (dateService == null) {
            return String.valueOf(hoc);
        }
        Set<String> hoS = dateService.getLegalHoliday();
        for (String ho : hoS) {
            hoc[DateUtil.dayOfYear(DateUtil.parse(ho, "yyyyMMdd")) - 1] = '1';
        }
        Set<String> reS = dateService.getAdjustRestWorkDays();
        for (String re : reS) {
            hoc[DateUtil.dayOfYear(DateUtil.parse(re, "yyyyMMdd")) - 1] = '0';
        }
        return String.valueOf(hoc);
    }

    public static void main(String[] args) {
        System.out.println(getHStr(2025));
//        Date first = DateUtil.parseDate("2020" + "-01-05");
//        int allDay = DateUtil.lengthOfYear(2020);
//        System.out.println(allDay);
//        System.out.println(DateUtil.dayOfYear(first));
//        String hs = getHStr(2021);
//        System.out.println(hs);
//        System.out.println(hs.length());
//        System.out.println(hs.charAt(DateUtil.dayOfYear(first)-1));
//        System.out.println(DateUtil.dayOfWeek(DateUtil.offsetDay(first, 0)));
    }
}
