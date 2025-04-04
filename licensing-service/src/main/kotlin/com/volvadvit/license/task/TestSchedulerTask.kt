package com.volvadvit.license.task

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class TestSchedulerTask {

    //TODO:
    // 1: Scheduler which will retrieve all license with "expirationDate = currentDate + 1 day" and update it
    // - each instance of the app should work with max number of 100 records
    // - use batching & skip lock & separate table for licenseId (to lock on it).
    // 2: Scheduler with real example for @SchedulerLock


    @Scheduled(fixedDelayString = "\${scheduler.interval.millis}")
    @SchedulerLock(name = "sampleTask", lockAtLeastFor = "20000",lockAtMostFor = "30000")
    fun printMessageWithDelay() {
        println("Test message. Time: " + LocalTime.now().format(DateTimeFormatter.ISO_TIME))
    }

    @Async
    @Scheduled(initialDelay = 3000, fixedRateString = "\${scheduler.interval.millis}")
    fun printMessageWithDelayAsync() {
        println("Test async message. Time: " + LocalTime.now().format(DateTimeFormatter.ISO_TIME))
        Thread.sleep(20000)
    }
}