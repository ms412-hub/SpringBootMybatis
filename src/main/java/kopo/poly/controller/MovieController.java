package kopo.poly.controller;

import kopo.poly.dto.MovieDTO;
import kopo.poly.service.IMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
@Controller
public class MovieController {
    private final IMovieService movieService;

    @GetMapping(value = "collectMovieRank")
    public String collectMovieRank(ModelMap model)
            throws Exception {

        log.info("{}.collectMovieRank Start!", this.getClass().getName());

        int res = movieService.collectMovieRank();

        model.addAttribute("msg","CGV 홈페이지로부터 수집한 영화는 총" + res + "건입니다.");

        log.info("{}.collectMovieRank End!", this.getClass().getName());

        return  "/movie/collectMovieRank";
    }
    @GetMapping(value = "getMovieRank")
    public String getMovieRank(ModelMap model)
            throws Exception {
        log.info("{}.getMovieRank Start!", this.getClass().getName());

        List<MovieDTO> rList = Optional.ofNullable(movieService.getMovieInfo()).orElseGet(ArrayList::new);

        model.addAttribute("rList",rList);

        log.info("{}.getMovieRank End!", this.getClass().getName());

        return "/movie/movieList";
    }
}
