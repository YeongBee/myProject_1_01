package com.yeongbee.store.mydelight.ipconfig.allow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AllowIpController {


    @Autowired
    private AllowIpService banIpService;

    @GetMapping("/admin/page/allowip")
    public String adminPage(Model model) {

        model.asMap().forEach((key, value) -> log.info("key={}, value={}", key, value));

        return "admin/allow_ip_setting";
    }

    @PostMapping("/admin/page/allowip")
    public String adminPageSubmit(String ipName, String content, Model model) {
        if (banIpService.findByIp(ipName)) {
            model.addAttribute("errorMessage", "이미 존재하는 IP입니다."); // 오류 메시지 추가
            return "admin/allow_ip_setting";
        }
        banIpService.save(ipName,content);
        return "redirect:/admin/page/allowip";
    }
}
