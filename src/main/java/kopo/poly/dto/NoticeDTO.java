package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class NoticeDTO {

    private String noticeSeq;

    private String title;

    private String noticeYn;

    private String contents;

    private String userId;

    private String readCnt;

    private String regId;

    private String regDt;

    private String chgId;

    private String chgDt;

    private String userName;
}


