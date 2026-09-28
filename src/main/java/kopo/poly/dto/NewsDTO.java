package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsDTO {

    private String url;       // 신문기사 URL
    private String title;     // 기사 제목
    private String contents;  // 기사 내용
}