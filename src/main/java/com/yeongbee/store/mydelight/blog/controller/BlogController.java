package com.yeongbee.store.mydelight.blog.controller;


import com.yeongbee.store.mydelight.blog.domain.FIleStore;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntityDTO;
import com.yeongbee.store.mydelight.blog.domain.comment.CommentDTO;
import com.yeongbee.store.mydelight.blog.service.AccountService;
import com.yeongbee.store.mydelight.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    private final BlogService blogService;
    private final AccountService accountService;
    private final FIleStore fIleStore;

    @GetMapping("")
    public String blogMain(Model model) {
        List<BlogEntity> blogList = blogService.findAll();
        Collections.reverse(blogList);

        model.addAttribute("blogList", blogList);
        return "blog/blog_home";
    }

    @GetMapping("/post/{id}")
    public String blogPost(@PathVariable Long id, Model model, CommentDTO commentDTO) {
        BlogEntity blog = blogService.findById(id);
        model.addAttribute("post", blog);
        return "blog/post";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        log.info("filename: {}", filename);
        log.info("filePath: {}", fIleStore.getFullPath(filename));
        return new UrlResource("file:" + fIleStore.getFullPath(filename));
    }


    @GetMapping("/create")
    public String blogCreate(Model model) {
        return "blog/post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String blogCreate(@Validated BlogEntityDTO blogEntityDTO, BindingResult bindingResult,
                             Principal principal, RedirectAttributes redirectAttributes) throws IOException {



        try {
            Account account = accountService.findByUsername(principal.getName());

            if (bindingResult.hasErrors()) {
                log.error("실행 오류 post_create ={}", bindingResult.getAllErrors());
                return "redirect:/blog/create";
            }

            BlogEntity blog = blogService.save(blogEntityDTO, account);

            return "redirect:/blog/post/" + blog.getId();

        } catch (IOException e) {
            log.error(e.getMessage());
            return "redirect:/blog/create";

        }
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String blogModify(BlogEntityDTO blogEntityDTO, @PathVariable("id") Long id, Principal principal) {
        BlogEntity blog = blogService.findById(id);

        if (!blog.getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        blogEntityDTO.setTitle(blog.getTitle());
        blogEntityDTO.setContent(blog.getContent());
        return "blog/blog_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String blogModify(@Validated BlogEntityDTO blogEntityDTO, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            log.info("실행 오류 post_modify");
            return "blog/blog_form";
        }
        BlogEntity blog = blogService.findById(id);
        if (!blog.getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        blogService.update(blogEntityDTO, id);
        return "redirect:/blog/post" + id;
    }

    @GetMapping("/delete/{id}")
    public String blogDelete(@PathVariable Long id, Principal principal) {
        BlogEntity blog = blogService.findById(id);
        if (!blog.getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        return "redirect:/blog/list";
    }
}


