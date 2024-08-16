package com.yeongbee.store.mydelight.ipconfig.adminpage.ban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanIpController {

    private static final Logger log = LoggerFactory.getLogger(BanIpController.class);
    @Autowired
    private BanIpService banIpService;

    @GetMapping("/admin/page/banip")
    public String adminPage(Model model) {

        model.asMap().forEach((key, value) -> log.info("key={}, value={}", key, value));

        return "admin/ban_ip_setting";
    }

    @PostMapping("/admin/page/banip")
    public String adminPageSubmit(@RequestParam String ipName, Model model) {
        if (banIpService.findByIp(ipName)) {
            log.info("1번 controller");
            model.addAttribute("errorMessage", "이미 존재하는 IP입니다."); // 오류 메시지 추가
            return "admin/ban_ip_setting";
        }
        banIpService.save(ipName);
        log.info("2번 controller");
        return "redirect:/admin/page/banip";
    }
}
