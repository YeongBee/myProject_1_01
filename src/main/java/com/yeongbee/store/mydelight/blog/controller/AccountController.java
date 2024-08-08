package com.yeongbee.store.mydelight.blog.controller;


import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import com.yeongbee.store.mydelight.blog.service.AccountFindPasswordService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.CompletableFuture;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private String number;
    private boolean checkNum = false;
    private final AccountFindPasswordService findPasswordService;

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
    public String postSignup(@Validated @ModelAttribute("accountDTO") AccountDTO accountDTO,
                             BindingResult bindingResult, Model model) {

        String checkName = accountService.checkUsername(accountDTO.getUsername());
        String checkNickname = accountService.checkNickname(accountDTO.getNickname());

        if (!checkName.equals("available")) {
            model.addAttribute("error", checkName);
            return "blog/signup_form";
        }

        if (!checkNickname.equals("available")) {
            model.addAttribute("error", checkNickname);
            return "blog/signup_form";
        }

        if (!checkNum) {
            model.addAttribute("error", "이메일 인증을 완료 해주세요");
            return "blog/signup_form";
        }

        if (!accountDTO.getPassword1().equals(accountDTO.getPassword2())) {
            model.addAttribute("error", "비밀번호를 확인해 주세요");
            return "blog/signup_form";
        }

        if (bindingResult.hasErrors()) {
            log.error("오류 Account-postSignUp ={}", bindingResult.getAllErrors());
            return "blog/signup_form";
        }


        accountService.save(accountDTO);
        return "redirect:/blog";
    }


    // 이메일만 검증 HTML
    //TODO
    @GetMapping("/findid")
    public String findId() {
        return "blog/find_id";

    }

    @GetMapping("/checkfindid")
    public ResponseEntity<String> findId(String id_email) {

        String findEmail = findPasswordService.findByEmail(id_email);
        log.info("UserEmail = {}", id_email);

        return findEmail.startsWith("이메일") ? ResponseEntity.badRequest().body(findEmail) :
                ResponseEntity.ok(findEmail);
    }


    @GetMapping("/findpassword")
    public ResponseEntity<String> findPassword(String pass_id, String pass_email) {


        // Id or Email 틀릴 시
        if (!findPasswordService.CheckUsernameEmail(pass_id, pass_email)) {
            return ResponseEntity.badRequest().body("Id 또는 Email를 확인해 주세요");
        }

        // 비동기 통신
        CompletableFuture.runAsync(() -> findPasswordService.TemporaryPassword(pass_id));
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/checkusername")
    public ResponseEntity<String> checkUsername(String username) {

        String result = accountService.checkUsername(username);

        return result.startsWith("Char") ? ResponseEntity.badRequest().body("Id 길이를 6글자에서 20글자 사이로 작성해 주세요") :
                result.startsWith("Invalid") ? ResponseEntity.badRequest().body("사용할 수 없는 문자 입니다.") :
                        result.startsWith("Id") ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 Id 입니다.") :
                                ResponseEntity.ok("사용 가능한 Id 입니다.");
    }

    @GetMapping("/checknickname")
    public ResponseEntity<String> checkNickname(String nickname) {

        String result = accountService.checkNickname(nickname);

        return result.startsWith("Char") ? ResponseEntity.badRequest().body("4글자에서 15글자 사이로 작성해 주세요") :
                result.startsWith("Invalid") ? ResponseEntity.badRequest().body("사용할 수 없는 문자 입니다.") :
                        result.startsWith("Nickname") ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이름 입니다.") :
                                ResponseEntity.ok("사용 가능한 Nickname 입니다.");
    }


    @GetMapping("/checkemail")
    public ResponseEntity<String> checkEmail(String email) {

        if (accountService.checkEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이메일 입니다.");
        }


        number = accountService.sendMail(email);

        if (number.startsWith("Failed")) {
            return ResponseEntity.badRequest().body("전송 실패");
        } else {
            return ResponseEntity.ok("전송 완료");
        }
    }

    @GetMapping("/checkmailnum")
    public ResponseEntity<?> checkMailNum(String emailNum) {

        if (emailNum.equals(number)) {
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
