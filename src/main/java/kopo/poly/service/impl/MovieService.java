package kopo.poly.service.impl;

import kopo.poly.dto.MovieDTO;
import kopo.poly.mapper.IMovieMapper;
import kopo.poly.service.IMovieService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieService implements IMovieService {

    private final IMovieMapper movieMapper;

    @Transactional
    @Override
    public int collectMovieRank() throws Exception {
        log.info("{}.collectMovieRank Start!", this.getClass().getName());

        String collectTime = DateUtil.getDateTime("yyyyMMdd");

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        movieMapper.deleteMovieInfo(pDTO);

        pDTO = null;

        int res = 0;

        String url = "https://kobis.or.kr/kobis/business/stat/boxs/findRealTicketList.do?loadEnd=0";

        Document doc;

        doc = Jsoup.connect(url).get();

        Elements element = doc.select("table.tbl_comm > tbody");

        Iterator<Element> movie_rank = element.select("td:nth-child(1)").iterator(); //영화 순위
        Iterator<Element> movie_name = element.select("td:nth-child(2) a").iterator(); //영화 이름
        Iterator<Element> movie_reserve = element.select("td:nth-child(4)").iterator(); //영화 예매율
        Iterator<Element> open_day = element.select("td:nth-child(3)").iterator(); //개봉일

        while (movie_rank.hasNext()) {
            pDTO = new MovieDTO();

            pDTO.setCollectTime(collectTime);

            pDTO.setMovieRank(CmmUtil.nvl(movie_rank.next().text()).trim());

            pDTO.setMovieNm(CmmUtil.nvl(movie_name.next().text()).trim());

            pDTO.setMovieReserve(CmmUtil.nvl(movie_reserve.next().text()).trim());

            String openDay = CmmUtil.nvl(open_day.next().text()).trim();
            openDay = openDay.replace("재개봉일 : ", "");
            pDTO.setOpenDay(openDay.substring(0, Math.min(openDay.length(), 10)));

            pDTO.setRegId("admin");

            res += movieMapper.insertMovieInfo(pDTO);
        }
        log.info("{}.collectMovieRank End!", this.getClass().getName());

        return res;
    }

    @Override
    public List<MovieDTO> getMovieInfo() throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info("{}.getMovieInfo Start!", this.getClass().getName());

        String collectTime = DateUtil.getDateTime("yyyyMMdd"); // 수집날짜 = 오늘 날짜

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        // DB에서 조회하기
        List<MovieDTO> rList = movieMapper.getMovieInfo(pDTO);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info("{}.getMovieInfo End!", this.getClass().getName());

        return rList;
    }


}
