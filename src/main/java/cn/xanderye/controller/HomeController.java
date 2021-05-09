package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {
    @Autowired
    private CrawlService crawlService;

    @PostMapping("manual")
    public ResultBean manual() {
        crawlService.start();
        return new ResultBean();
    }
}
