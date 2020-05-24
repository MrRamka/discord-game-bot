package com.yabcompany.discord.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CommandCreator {

    @Bean("Help")
    public HelpCommand helpCommand() {
        return new HelpCommand("help", "Help Command. Shows all available commands");
    }

    @Bean("Registration")
    public RegistrationCommand registrationCommand() {
        return new RegistrationCommand("reg", "Registration to play");
    }

    @Bean("RussianRoulette")
    public RussianRouletteCommand russianRouletteCommand() {
        return new RussianRouletteCommand("russianroulette", "<bet> Starts Russian Roulette game");
    }

}
