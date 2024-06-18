package com.yeongbee.store.mydelight.blog.controller;


import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import com.yeongbee.store.mydelight.blog.domain.comment.Comment;
import com.yeongbee.store.mydelight.blog.domain.comment.CommentDTO;
import com.yeongbee.store.mydelight.blog.service.BlogService;
import com.yeongbee.store.mydelight.blog.service.CommentService;
import com.yeongbee.store.mydelight.ipconfig.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final BlogService blogService;
    private final IpUtils ipUtils;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable Long id,
                                @Validated CommentDTO commentDTO, BindingResult bindingResult,
                                HttpServletRequest request) {

        BlogEntity blog = blogService.findById(id);

        if(bindingResult.hasErrors()) {
            model.addAttribute("blog", blog);
            return "blog/post";
        }

        Comment comment = commentService.save(commentDTO, id);
        return "redirect:/blog/post/" + id+"#comment_"+comment.getId();
    }
}
