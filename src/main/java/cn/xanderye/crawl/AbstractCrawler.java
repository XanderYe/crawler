package cn.xanderye.crawl;

import cn.xanderye.entity.RecruitData;

import java.io.IOException;
import java.util.List;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/9 9:40
 */
public abstract class AbstractCrawler {

    public abstract List<RecruitData> crawlData() throws IOException;
}
