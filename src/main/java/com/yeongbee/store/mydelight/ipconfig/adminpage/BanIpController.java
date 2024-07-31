package com.yeongbee.store.mydelight.ipconfig.adminpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanIpController {

    @Autowired
    private BanIpService banIpService;

    @GetMapping("/admin/page/ip")
    public String adminPage() {
        return "admin/ip_setting";
    }

    @PostMapping("/admin/page/ip")
    public String adminPageSubmit(@RequestParam String ipName) {
        banIpService.save(ipName);
        return "redirect:/admin/page/ip";
    }
}
