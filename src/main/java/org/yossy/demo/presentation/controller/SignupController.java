package org.yossy.demo.presentation.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yossy.demo.presentation.form.SignupForm;
import org.yossy.demo.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SignupController {
    
    private final AccountService accountService;

    public SignupController(@Qualifier("CustomAccountService") AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * トップページ
     *
     * @param model モデル（ユーザーリスト）
     * @return login
     */
    @GetMapping(value = "signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }


    /**
     * アカウント登録処理
     *
     * @param signupForm サインアップフォームデータ
     * @param redirectAttributes リダイレクト先へメッセージを送るため
     * @return index (redirect)
     */
    @PostMapping(value = "signup")
    public String signup(@ModelAttribute("signup") @Valid SignupForm signupForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<String> errorList = result.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            model.addAttribute("validationError", errorList);
            model.addAttribute("signupForm", new SignupForm());
            return "signup";
        }
        String[] roles = {"ROLE_USER"};
        accountService.register(signupForm.getName(), signupForm.getEmail(), signupForm.getPassword(), roles);
        redirectAttributes.addFlashAttribute("successMessage", "Your account is registered!");
        log.info("Your account is registered!");
        return "redirect:/";
    }
}