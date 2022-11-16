package com.project.traning.backend.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    // 1 => second
    // 2 => minute
    // 3 => hour
    // 4 => day
    // 5 => month
    // 6 => year
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Bangkok")
    public void testEveryMinute() {
       log.info("Hello, What's your name?");
    }

}
