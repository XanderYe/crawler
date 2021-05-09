package cn.xanderye.crawl;

import cn.xanderye.entity.RecruitData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/9 9:42
 */
public class BeilunCrawler extends AbstractCrawler {

    @Override
    public List<RecruitData> crawlData() throws IOException {
        String url = "http://www.bl.gov.cn/module/xxgk/search.jsp?divid=div1229055237&jdid=3499&area=113302060029545102&infotypeId=BL17&isAllList=1&standardXxgk=1";
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36").get();
        Elements links = doc.select("body > table > tbody > tr > td > div.zfxxgk_item > div > ul > li > a");
        List<RecruitData> recruitDataList = new ArrayList<>();
        for (Element link : links)
        {
            RecruitData recruitData = new RecruitData();
            recruitData.setLink(link.attr("href"));
            recruitData.setTitle(link.attr("title"));
            String date = link.nextElementSibling().text();
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                recruitData.setDate(sdf.parse(date));
            }
            catch (Exception ignored) {}
            recruitDataList.add(recruitData);
        }
        return recruitDataList;
    }
}
