package com.train.framework.common.utils.holiday;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 假期工具类
 *
 * @author xindu
 */
public class HolidayUtil {
    /**
     * 2024年假期
     */
    public static String H2024 = "100001100000110000011000001100000100000011111111000000110000011000001100000110000011000001100011100000011000001100000100011111000000100000110000011000001100000111000011000001100000110000011000001100000110000011000001100000110000011000001100000110000011000000111000110000010011111110000010000011000001100000110000011000001100000110000011000001100000110000011000001110";
    /**
     * 2025年假期
     */
    public static String H2025 = "10011000001100000110000010011111111000010000011000001100000110000011000001100000110000011000011100000110000011000001000011111000011000001100000110000011100001100000110000011000001100000110000011000001100000110000011000001100000110000011000001100000110000011000001100000100011111111000100000110000011000001100000110000011000001100000110000011000001100000110000011000";
    /**
     * 获取假期字符串
     *
     * @param year
     * @return
     */
    public static String getHStr(int year) {
        if (year == 2025) {
            return H2025;
        } else if  (year == 2024) {
            return H2024;
        } else {
            return DateServiceFactory.getHStr(year);
        }
    }

    /**
     * 是否是假期
     *
     * @param day 指定日期
     * @return
     */
    public static boolean isHoliday(Date day) {
        if (day == null) {
            return false;
        }
        return getHStr(DateUtil.year(day)).charAt(DateUtil.dayOfYear(day) - 1) == '1';
    }

    /**
     * 是否是假期
     *
     * @param day 指定日期
     * @return
     */
    public static boolean isHoliday(String day) {
        return isHoliday(DateUtil.parse(day));
    }

    /**
     * 开始日是否是假期，且两个日期都在同一个假期内
     * 中间无工作日
     *
     * @param start 开始日
     * @param end   结束日
     * @return
     */
    public static boolean isInSameHoliday(Date start, Date end) {
        if (start.compareTo(end) > 0) {
            Date temp = start;
            start = end;
            end = temp;
        }
        if (!isHoliday(start)) {
            return false;
        }
        // 获取假期后的第一个工作日
        Date s = getRecentWeekday(start);
        // 如果第一个工作日 大于 结束日 则证明是同一个假期
        return s.compareTo(end) > 0;
    }

    /**
     * 下一个工作日 T+1
     * 如果今天是周一，下一个工作日是周二
     * 如果今天是周五，下一个工作日是周一
     *
     * @param day 指定日期
     * @return
     */
    public static Date nextWeekday(Date day) {
        if (day == null) {
            return null;
        }
        day = DateUtil.offsetDay(day, 1);
        return getRecentWeekday(day);
    }

    /**
     * 下一个工作日 T+1
     * 如果今天是周一，下一个工作日是周二
     * 如果今天是周五，下一个工作日是周一
     *
     * @param day 指定日期
     * @return
     */
    public static Date nextWeekday(String day) {
        return nextWeekday(DateUtil.parse(day));
    }

    /**
     * 最近一个工作日
     * 如指定日期是工作日 则返回当前日期
     * 如当前是假期 则返回假期之后第一个工作日
     *
     * @param day 指定日期
     * @return
     */
    public static Date getRecentWeekday(Date day) {
        if (day == null) {
            return null;
        }
        // 如果当天即是工作日
        if (!isHoliday(day)) {
            return day;
        }
        // 判断下一个工作日 默认365天内不可能全为假期
        for (int i = 1; i < 365; i++) {
            Date cur = DateUtil.offsetDay(day, i);
            if (!isHoliday(cur)) {
                return cur;
            }
        }
        return null;
    }

    /**
     * 最近一个工作日
     * 如指定日期是工作日 则返回当前日期
     * 如当前是假期 则返回假期之后第一个工作日
     *
     * @param day 指定日期
     * @return
     */
    public static Date getRecentWeekday(String day) {
        return getRecentWeekday(DateUtil.parse(day));
    }

    /**
     * 最近一个非工作日
     * 如指定日期是非工作日 则返回当前日期
     * 如当前是工作日 则返回非工作日第一天
     *
     * @param day 指定日期
     * @return
     */
    public static Date getRecentHoliday(Date day) {
        if (day == null) {
            return null;
        }
        // 如果当天即是工作日
        if (isHoliday(day)) {
            return day;
        }
        // 判断下一个工作日 默认365天内不可能全为工作日
        for (int i = 1; i < 365; i++) {
            Date cur = DateUtil.offsetDay(day, i);
            if (isHoliday(cur)) {
                return cur;
            }
        }
        return null;
    }


    /**
     * 最近一个非工作日
     * 如指定日期是非工作日 则返回当前日期
     * 如当前是工作日 则返回非工作日第一天
     *
     * @param day 指定日期
     * @return
     */
    public static Date getRecentHoliday(String day) {
        return getRecentHoliday(DateUtil.parse(day));
    }

    /**
     * 获取最近假期的开始日
     *
     * @param day
     * @return
     */
    public static Date getRecentBeginHoliday(Date day) {
        if (day == null) {
            return null;
        }
        // 如果是工作日 则只需要获取最近一个假期
        if (!isHoliday(day)) {
            return getRecentHoliday(day);
        }
        // 如果是假期
        Date last = day;
        for (int i = -1; i > -365; i--) {
            Date cur = DateUtil.offsetDay(day, i);
            // 如果前一天是工作日 则当天是假期第一天
            if (!isHoliday(cur)) {
                return last;
            }
            last = cur;
        }
        return last;
    }

    /**
     * 获取最近假期的开始日
     *
     * @param day
     * @return
     */
    public static Date getRecentBeginHoliday(String day) {
        return getRecentBeginHoliday(DateUtil.parse(day));
    }

    /**
     * 获取最近假期的结束日
     *
     * @param day
     * @return
     */
    public static Date getRecentEndHoliday(Date day) {
        if (day == null) {
            return null;
        }
        day = getRecentHoliday(day);
        Date last = day;
        // 判断下一个工作日 默认365天内不可能全为工作日
        for (int i = 1; i < 365; i++) {
            Date cur = DateUtil.offsetDay(day, i);
            if (!isHoliday(cur)) {
                return last;
            }
            last = cur;
        }
        return last;
    }

    /**
     * 获取最近假期的结束日
     *
     * @param day
     * @return
     */
    public static Date getRecentEndHoliday(String day) {
        return getRecentEndHoliday(DateUtil.parse(day));
    }

}
