package com.yabcompany.discord.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class CommandCreator {

    @Bean("Help command")
    public HelpCommand helpCommand() {
        return new HelpCommand("help", "Help Command. Shows all available commands");
    }


}
