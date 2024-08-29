package com.yeongbee.store.mydelight.discord;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiscordController {

    @GetMapping("/discord")
    public String discord() {
        return "discord/discordmain";
    }
}
