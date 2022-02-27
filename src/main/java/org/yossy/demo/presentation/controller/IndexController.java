package org.yossy.demo.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {


    /**
     * トップページ
     *
     * @return index
     */
    @GetMapping(value = "/")
    public String index() {
        log.info("Welcome!!");
        return "index";
    }

}
