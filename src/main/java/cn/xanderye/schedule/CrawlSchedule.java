package cn.xanderye.schedule;

import cn.xanderye.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/9 9:38
 */
@EnableScheduling
@Component
public class CrawlSchedule {
    @Autowired
    private CrawlService crawlService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void crawl() {
        crawlService.start();
    }
}
