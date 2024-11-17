package com.yeongbee.store.mydelight.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
@Slf4j
public class ContentsController {


    private final ContentsService contentsService;

    @GetMapping("/my")
    public String readContents(Model model) {
        ContentsEntity entity = contentsService.getContents();
        model.addAttribute("contents", entity);

//        log.info("Title = {}", entity.getTitle());
//        log.info("Content = {}", entity.getContent());
        return "contents/my";
    }

    @GetMapping("/my-edit")
    public String editContents() {
        return "contents/my-edit";
    }

    @PostMapping("/my-edit")
    public String writeContents(@RequestParam String title, @RequestParam String content) {

        log.info("Title = {}", title);
        log.info("Content = {}", content);

        contentsService.saved(title, content);
        return "redirect:/page/my";
    }

    @GetMapping("/my-modify")
    public String getUpdateContents(Model model) {
        ContentsEntity entity = contentsService.getContents();
        model.addAttribute("contents", entity);
        return "contents/my-modify";
    }


    @PostMapping("/my-modify")
    public String updateContents(@RequestParam String title, @RequestParam String content) {

        log.info("Title = {}", title);
        log.info("Content = {}", content);


        contentsService.update(title, content);
        return "redirect:/page/my";
    }
}
