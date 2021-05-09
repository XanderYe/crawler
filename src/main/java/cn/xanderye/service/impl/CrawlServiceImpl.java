package cn.xanderye.service.impl;

import cn.xanderye.crawl.AbstractCrawler;
import cn.xanderye.crawl.BeilunCrawler;
import cn.xanderye.entity.RecruitData;
import cn.xanderye.entity.User;
import cn.xanderye.mapper.RecruitDataMapper;
import cn.xanderye.mapper.UserMapper;
import cn.xanderye.service.CrawlService;
import cn.xanderye.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/8 17:07
 */
@Slf4j
@Service
public class CrawlServiceImpl implements CrawlService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecruitDataMapper recruitDataMapper;

    @Autowired
    private MailService mailService;

    @Override
    public void start() {
        List<RecruitData> recruitDataList = new ArrayList<>();
        // 设置数据源
        List<AbstractCrawler> crawlerList = new ArrayList<>();
        crawlerList.add(new BeilunCrawler());

        for (AbstractCrawler crawler : crawlerList) {
            try {
                recruitDataList.addAll(crawler.crawlData());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        List<User> userList = this.userMapper.selectAll();
        List<RecruitData> cached = this.recruitDataMapper.selectAll();
        List<String> titleList = cached.parallelStream().map(RecruitData::getTitle).collect(Collectors.toList());
        recruitDataList = recruitDataList.parallelStream().filter(recruitData -> !titleList.contains(recruitData.getTitle())).collect(Collectors.toList());
        log.info("获取到不重复的数据{}条", recruitDataList.size());
        for (RecruitData recruitData : recruitDataList) {
            if (!userList.isEmpty()) {
                for (User user : userList) {
                    if (recruitData.getTitle().contains(user.getKeyword())) {
                        long afterDate = System.currentTimeMillis() - 604800000L;
                        if (null == recruitData.getDate() || recruitData.getDate().getTime() >= afterDate) {
                            String string = user.getName() + "您好，匹配到关键字<" + user.getKeyword() + ">的数据，查看详情：\r\n标题：" + recruitData.getTitle() + "\r\n链接："+ recruitData.getLink() + "\r\n";
                            this.mailService.sendMail(user.getEmail(), "匹配到数据", string);
                            this.recruitDataMapper.insert(recruitData);
                            log.info("插入已通知数据：{}", recruitData.toString());
                            try {
                                Thread.sleep(3000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}

