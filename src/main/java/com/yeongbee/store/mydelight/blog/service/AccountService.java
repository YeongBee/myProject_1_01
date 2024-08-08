package com.yeongbee.store.mydelight.blog.service;


import com.yeongbee.store.mydelight.blog.domain.HasRole;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import com.yeongbee.store.mydelight.blog.repository.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public void save(AccountDTO accountDTO) {
        log.info(" account pass = {}", accountDTO.getPassword1());
        Account account = new Account(accountDTO.getUsername(), accountDTO.getNickname(),
                passwordEncoder.encode(accountDTO.getPassword1()), accountDTO.getEmail(), HasRole.USER.getValue());
        accountRepository.save(account);
    }

    public String checkUsername(String username) {

        if (username.length() < 6 || username.length() > 20) {
            return "Char length  Id 6 ~ 20";
        } else if(!username.matches("^[a-zA-Z0-9]{6,20}$")){
            return "Invalid character";
        } else if (findByUsernameString(username).isPresent()) {
            return "Id in used";
        } else{
            return "available";
        }
    }

    public String checkNickname(String nickname) {
        if (nickname.length() < 4 || nickname.length() > 15) {
            return "Char length Nickname 4 ~ 15";
        } else if(!nickname.matches("^[a-zA-Z0-9]{4,15}$")){
            return "Invalid character";
        } else if (findByUsernameString(nickname).isPresent()) {
            return "Nickname in used";
        } else{
            return "available";
        }
    }


    public boolean checkEmail(String email){
        return accountRepository.existsByEmail(email);
    }


    private static int number;



    public static void createNumber() {
        number = (int)(Math.random() * (90000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    private boolean CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("mainyeongb@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<table width='100%' cellpadding='0' cellspacing='0' style='border-collapse: collapse; border: none;'>";
            body += "  <tr>";
            body += "    <td align='center' style='padding: 20px 0;'>";
            body += "      <h2 style='color: #333; font-size: 24px; font-weight: bold;'>요청하신 인증 번호입니다.</h2>";
            body += "    </td>";
            body += "  </tr>";
            body += "  <tr>";
            body += "    <td align='center' style='padding: 30px 0;'>";
            body += "      <h1 style='color: #007bff; font-size: 48px; font-weight: bold;'>" + number + "</h1>";
            body += "    </td>";
            body += "  </tr>";
            body += "  <tr>";
            body += "    <td align='center' style='padding: 20px 0;'>";
            body += "      <p style='color: #666; font-size: 16px;'>감사합니다.</p>";
            body += "    </td>";
            body += "  </tr>";
            body += "</table>";
            message.setText(body, "UTF-8", "html");

            javaMailSender.send(message);
            log.info("mailService 보냄");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String sendMail(String mail) {
        if(isValid(mail) && CreateMail(mail)){
            return number+"";
        } else {
            return "Failed Send Email";
        }
    }

    public String checkNumber(){
        return number+"";
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public Optional<Account> findByUsernameString(String username){
        return accountRepository.findByUsername(username);
    }


    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Account with username " + username + " not found")
        );
    }


}
