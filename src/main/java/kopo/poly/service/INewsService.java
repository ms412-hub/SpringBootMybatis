package kopo.poly.service;

import kopo.poly.dto.NewsDTO;

public interface INewsService {

    NewsDTO getNews(String url);

}