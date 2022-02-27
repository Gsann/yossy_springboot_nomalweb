package org.yossy.demo.presentation.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yossy.demo.presentation.form.forum.CommentPostForm;
import org.yossy.demo.presentation.form.forum.EntryPostForm;
import org.yossy.demo.service.ForumService;
import org.yossy.demo.setting.security.LoginUser;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/forum")
@Slf4j
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping
    public String forum(Model model) {
        return this.list(model);
    }
    
    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("entry_list", forumService.getEntryList());
        return "/forum/list";
    }

    @GetMapping(value = "/entry_post")
    public String entryPost(Model model) {
        model.addAttribute("entryPostForm", new EntryPostForm());
        return "/forum/entry_post";
    }

    @PostMapping(value = "/entry_post")
    public String entryPost(@ModelAttribute("/forum/entry_post") EntryPostForm entryPostForm, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
        forumService.registerEntry(entryPostForm.getEntryName(), entryPostForm.getComment(), loginUser.getUserName(), loginUser.getUser().getRoles());
        redirectAttributes.addFlashAttribute("successMessage", "Posting completed!");
        log.info("Posting completed!");
        return "redirect:/forum/list";
    }

    @GetMapping(value = "/entry/{id}")
    public String entry(@PathVariable long id, Model model) {
        model.addAttribute("commentPostForm", new CommentPostForm());
        model.addAttribute("entry", forumService.getEntry(id));
        model.addAttribute("comment_list", forumService.getEntryList(id));
        return "/forum/entry";
    }

    @PostMapping(value = "/comment_post")
    public String commentPost(@ModelAttribute("/forum/comment_post") CommentPostForm commentPostForm, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
        forumService.registerComment(commentPostForm.getEntryId(), commentPostForm.getComment(), loginUser.getUserName(), loginUser.getUser().getRoles());
        redirectAttributes.addFlashAttribute("successMessage", "Posting completed!");
        log.info("Posting completed!");
        return "redirect:/forum/entry/" + commentPostForm.getEntryId();
    }
}
