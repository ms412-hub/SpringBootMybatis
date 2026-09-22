package kopo.poly.service.impl;

import kopo.poly.dto.NewsDTO;
import kopo.poly.service.INewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements INewsService {

    @Override
    public NewsDTO getNews(String url) {

        NewsDTO dto = new NewsDTO();

        try {

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            String title = doc.title();
            String contents = doc.select("p").text();

            dto.setUrl(url);
            dto.setTitle(title);
            dto.setContents(contents);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return dto;
    }
}