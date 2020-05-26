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

    @Bean("Join")
    public JoinGameCommand joinGameCommand() {
        return new JoinGameCommand("join", "<#hash> Join to game using game hash");
    }

    @Bean("Money")
    public MoneyCommand moneyCommand() {
        return new MoneyCommand("money", "Shows your money");
    }

    @Bean("Daily")
    public DailyCommand dailyCommand() {
        return new DailyCommand("daily", "You can get 2500");
    }
}
