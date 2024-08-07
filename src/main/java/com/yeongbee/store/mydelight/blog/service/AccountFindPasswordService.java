package com.yeongbee.store.mydelight.blog.service;


import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.repository.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountFindPasswordService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS;
    private static final int PASSWORD_LENGTH = 11;

    private static String newPassword;

    public void generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        // 각 종류의 문자를 최소 하나씩 추가
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // 나머지 자리수를 랜덤으로 채움
        for (int i = 3; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // 비밀번호를 랜덤하게 섞음
        newPassword =  shuffleString(password.toString());
    }

    public String findByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            return account.get().getUsername();
        } else {
            return "이메일을 찾을 수 없습니다.";
        }
    }


    private String shuffleString(String password) {
        char[] passwordArray = password.toCharArray();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }
        return new String(passwordArray);
    }

    public boolean CheckUsernameEmail(String username, String email){

        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            Account checkAccount = account.get();
            return email.equals(checkAccount.getEmail());
        }
        return false;
    }

    @Transactional
    public void TemporaryPassword(String username){
        Account  account = accountRepository.findByUsername(username).get();
        String userEmail = account.getEmail();
        sendMail(userEmail);
        String password = passwordEncoder.encode(newPassword);
        account.updatePassword(password);
    }


    private boolean CreateMail(String mail){
        generatePassword();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("mainyeongb@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("임시 비밀번호 발송");
            String body = "";
            body += "<table width='100%' cellpadding='0' cellspacing='0' style='border-collapse: collapse; border: none;'>";
            body += "  <tr>";
            body += "    <td align='center' style='padding: 20px 0;'>";
            body += "      <h2 style='color: #333; font-size: 24px; font-weight: bold;'>임시 비밀번호 입니다.</h2>";
            body += "    </td>";
            body += "  </tr>";
            body += "  <tr>";
            body += "    <td align='center' style='padding: 30px 0;'>";
            body += "      <h1 style='color: #007bff; font-size: 48px; font-weight: bold;'>" + newPassword + "</h1>";
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

        if(CreateMail(mail)){
            return newPassword;
        } else {
            return "이메일 발송 실패";
        }
    }


}
