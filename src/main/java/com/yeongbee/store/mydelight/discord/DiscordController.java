package com.yeongbee.store.mydelight.discord;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiscordController {

//    @GetMapping("/discord")
    public String discord() {
        return "discord/discord_main";
    }

    @GetMapping("/discord")
    public String discordPage() {
        return "discord/fix_page";
    }
}
