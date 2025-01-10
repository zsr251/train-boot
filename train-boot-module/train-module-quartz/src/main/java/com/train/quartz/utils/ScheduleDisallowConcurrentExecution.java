package com.train.quartz.utils;

import org.quartz.DisallowConcurrentExecution;

/**
 * 禁止并发
 *

 *
 */
@DisallowConcurrentExecution
public class ScheduleDisallowConcurrentExecution extends AbstractScheduleJob {

}
