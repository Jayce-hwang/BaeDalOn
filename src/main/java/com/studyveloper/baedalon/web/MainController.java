package com.studyveloper.baedalon.web;

import com.studyveloper.baedalon.web.constant.WebPagePath;
import com.studyveloper.baedalon.web.constant.WebUrlPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.studyveloper.baedalon.web.constant.WebPagePath.HOME_PAGE;
import static com.studyveloper.baedalon.web.constant.WebUrlPath.HOME_URL;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @GetMapping(HOME_URL)
    public String showHomePage() {
        return HOME_PAGE;
    }

}
