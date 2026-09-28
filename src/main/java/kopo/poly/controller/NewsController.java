package kopo.poly.controller;

import kopo.poly.dto.NewsDTO;
import kopo.poly.service.INewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final INewsService newsService;

    // 신문기사 URL 입력 화면
    @GetMapping("/readNews")
    public String readNews() {

        return "news/readNews";
    }

    // 신문기사 크롤링
    @GetMapping("/crawl")
    @ResponseBody
    public NewsDTO crawlNews(@RequestParam String url) {

        return newsService.getNews(url);
    }
}