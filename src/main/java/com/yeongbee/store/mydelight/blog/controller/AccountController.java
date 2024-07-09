package com.yeongbee.store.mydelight.blog.controller;


import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import com.yeongbee.store.mydelight.blog.service.AccountService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private String number;
    private boolean checkNum;

    @GetMapping("/login")
    public String blogLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "blog/login_form";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute AccountDTO accountDTO) {
        return "blog/signup_form";
    }

    @PostMapping("/signup")
    public String postSignup(@Validated @ModelAttribute("accountDTO") AccountDTO accountDTO, BindingResult bindingResult,Model model) {

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

        if(!checkNum){
            model.addAttribute("error","이메일 인증을 완료 해주세요");
            return "blog/signup_form";

        }

        accountService.save(accountDTO);
        return "redirect:/blog";
    }

    @GetMapping("/checkusername")
    public ResponseEntity<String> checkUsername(@RequestParam("username") String username) {

        String result = accountService.checkUsername(username);

        return result.startsWith("Char") ? ResponseEntity.badRequest().body("Id 길이를 6글자에서 20글자 사이로 작성해 주세요"):
                result.startsWith("Invalid") ?  ResponseEntity.badRequest().body("사용할 수 없는 문자 입니다."):
                        result.startsWith("Id") ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 Id 입니다."):
                                ResponseEntity.ok("사용 가능한 Id 입니다.");
    }

    @GetMapping("/checknickname")
    public ResponseEntity<String> checkNickname(@RequestParam("nickname") String nickname) {

        String result = accountService.checkNickname(nickname);

        return result.startsWith("Char") ? ResponseEntity.badRequest().body("4글자에서 15글자 사이로 작성해 주세요"):
                result.startsWith("Invalid") ? ResponseEntity.badRequest().body("사용할 수 없는 문자 입니다."):
                        result.startsWith("Nickname") ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이름 입니다."):
                                ResponseEntity.ok("사용 가능한 Nickname 입니다.");
    }


    @GetMapping("/checkemail")
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {

        if(accountService.checkEmail(email).startsWith("Email")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이메일 입니다.");
        }

        number = accountService.sendMail(email);

        if(number.startsWith("Failed")){
            return ResponseEntity.badRequest().body(number);
        } else {
            return ResponseEntity.ok("전송 완료");
        }
    }

    @GetMapping("/checkmailnum")
    public ResponseEntity<?> checkMailNum(@RequestParam("emailNum") String emailNum) {

        log.info("emailNum={}", emailNum);

        if(emailNum.equals(number)){
            checkNum = true;
            log.info("true");

            return ResponseEntity.ok("인증 성공");
        } else {
            checkNum = false;
            log.info("false");

            return ResponseEntity.badRequest().body("인증 번호를 확인하세요");
        }
    }



}
