package com.sunway.common.rx

import com.sunway.common.rx.scheduler.IoMainScheduler

/**
 *  @author BuMingYang
 *  @des
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}