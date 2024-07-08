package com.yeongbee.store.mydelight.blog.controller;


import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import com.yeongbee.store.mydelight.blog.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/login")
    public String blogLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "blog/login_form";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "blog/signup_form";
    }

    @PostMapping("/signup")
    public String postSignup(@Validated AccountDTO accountDTO, BindingResult bindingResult,Model model) {

        String checkName = accountService.checkUsername(accountDTO.getUsername());
        String checkNickname = accountService.checkNickname(accountDTO.getNickname());

        if(!checkName.equals("available")){
            model.addAttribute("error",checkName);
            return "blog/signup_form";
        }

        if(!checkNickname.equals("available")){
            model.addAttribute("error",checkNickname);
            return "blog/signup_form";
        }

        if (bindingResult.hasErrors()) {
            log.error("오류 Account-postSignUp ={}", bindingResult.getAllErrors());

            return "blog/signup_form";
        }

        accountService.save(accountDTO);

        return "redirect:/blog";
    }

    @PostMapping("/checkusername")
    public ResponseEntity<String> checkUsername(@RequestParam("username") String username) {

        String result = accountService.checkUsername(username);

        if (result.startsWith("Char")) {
            return ResponseEntity.badRequest().body("6글자에서 20글자 사이로 작성해 주세요");
        }
        if(result.startsWith("Invalid")){
            return ResponseEntity.badRequest().body("사용할 수 없는 문자 입니다.");
        }
        if(result.startsWith("Id")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 Id 입니다.");
        }

        return ResponseEntity.ok("사용 가능한 Id 입니다.");
    }

    @PostMapping("/checknickname")
    public ResponseEntity<String> checkNickname(@RequestParam("nickname") String nickname) {

        String result = accountService.checkNickname(nickname);

        if (result.startsWith("Char")) {
            return ResponseEntity.badRequest().body("4글자에서 15글자 사이로 작성해 주세요");
        }
        if(result.startsWith("Invalid")){
            return ResponseEntity.badRequest().body("사용할 수 없는 문자 입니다.");
        }
        if(result.startsWith("Nickname")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이름 입니다.");
        }

        return ResponseEntity.ok("사용 가능한 Nickname 입니다.");

    }

    @PostMapping("/checkemail")
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email, Model model) {

        if (accountService.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }

        return ResponseEntity.ok("Use email");
    }



}
