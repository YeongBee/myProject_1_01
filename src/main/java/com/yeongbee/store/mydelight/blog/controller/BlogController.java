package com.yeongbee.store.mydelight.blog.controller;


import com.yeongbee.store.mydelight.blog.domain.FIleStore;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.blog.*;
import com.yeongbee.store.mydelight.blog.domain.comment.CommentDTO;
import com.yeongbee.store.mydelight.blog.service.AccountService;
import com.yeongbee.store.mydelight.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    private final BlogService blogService;
    private final AccountService accountService;
    private final FIleStore filestore;

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


    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) throws IOException {
        String fullPath = filestore.getFullPath(filename);
        MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(Paths.get(fullPath)));
        UrlResource resource = new UrlResource("file:" + fullPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mediaType.toString())
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Map<String, String>>> uploadImageFiles(@RequestParam("imageFile") List<MultipartFile> files) {

        List<Map<String, String>> imageSave = blogService.imagesave(files);
        if(!imageSave.isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(imageSave);
        } else {
            log.error("File is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String blogCreate(@Validated BlogEntityDTO blogEntityDTO,
                             BindingResult bindingResult, Principal principal){

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


    @GetMapping("/create")
    public String blogCreate(Model model) {
        return "blog/post_form";
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String blogModify(Model model, @PathVariable("id") Long id, Principal principal) {
        BlogEntity blog = blogService.findById(id);

        if (!blog.getAccount().getUsername().equals(principal.getName())) {
            model.addAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/blog/{id}";
        }

        BlogEntityDTO blogEntityDTO = new BlogEntityDTO();
        blogEntityDTO.setTitle(blog.getTitle());
        blogEntityDTO.setContent(blog.getContent());
        model.addAttribute("blogEntityDTO", blogEntityDTO);
        return "blog/post_form_modify";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String blogModify(@Validated BlogEntityDTO blogEntityDTO, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            log.info("실행 오류 post_modify");
            return "blog/post_form_modify";
        }
        BlogEntity blog = blogService.findById(id);
        if (!blog.getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        blogService.update(blogEntityDTO, id);
        return "redirect:/blog/post/" + id;
    }

    @GetMapping("/delete/{id}")
    public String blogDelete(@PathVariable Long id, Principal principal) {
        BlogEntity blog = blogService.findById(id);

        if (!blog.getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

            blogService.delete(id);


        return "redirect:/blog";
    }
}


