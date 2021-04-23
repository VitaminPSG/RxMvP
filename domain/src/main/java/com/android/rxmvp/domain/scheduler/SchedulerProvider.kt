package com.android.rxmvp.domain.scheduler

import io.reactivex.rxjava3.core.Scheduler

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {

    val io: Scheduler
    val ui: Scheduler
    val computation: Scheduler
}
