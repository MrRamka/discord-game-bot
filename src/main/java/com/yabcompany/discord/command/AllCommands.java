package com.yabcompany.discord.command;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class AllCommands {


    @Autowired
    private HelpCommand helpCommand;

    @Autowired
    private RegistrationCommand registrationCommand;

    @Autowired
    private RussianRouletteCommand russianRouletteCommand;

    private List<Command> commands = new ArrayList<>();

    @PostConstruct
    private void init() {
        commands.add(helpCommand);
        commands.add(registrationCommand);
        commands.add(russianRouletteCommand);
    }

}
