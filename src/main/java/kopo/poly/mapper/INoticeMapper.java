package kopo.poly.mapper;

import kopo.poly.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface INoticeMapper {
    List<NoticeDTO> getNoticeList() throws Exception;

    void insertNoticeInfo(NoticeDTO pDto) throws  Exception;

    NoticeDTO getNoticeInfo(NoticeDTO pDto) throws Exception;

    void updateNoticeReadCnt(NoticeDTO pDto) throws Exception;

    void updateNoticeInfo(NoticeDTO pDto) throws Exception;

    void deleteNoticeInfo(NoticeDTO pDto) throws Exception;
}
