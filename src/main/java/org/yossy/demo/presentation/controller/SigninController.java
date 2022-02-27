package org.yossy.demo.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {

    /**
     * トップページ
     *
     * @param model モデル（ユーザーリスト）
     * @return login
     */
    @GetMapping(value = "signin")
    public String signin(Model model) {
        return "signin";
    }
}
