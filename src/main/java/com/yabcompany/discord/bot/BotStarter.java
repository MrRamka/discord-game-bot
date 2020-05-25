package com.yabcompany.discord.bot;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Slf4j
@Component
public class BotStarter {

    @Value("${discord-bot-token}")
    private String SECRET_TOKEN;

    @Autowired
    private GamingBotListener gamingBotListener;

    private static final String WATCHING = "cute cat videos";

    @PostConstruct
    public void init() {
        try {
            JDABuilder jda = JDABuilder.createDefault(SECRET_TOKEN);
            jda.setActivity(Activity.watching(WATCHING))
                    .addEventListeners(gamingBotListener)
                    .build();

            log.info("Bot has been started");
        } catch (LoginException e) {
            log.error("Login Exception" + e.getMessage());
        }
    }

}
