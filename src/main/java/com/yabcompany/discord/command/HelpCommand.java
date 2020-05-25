package com.yabcompany.discord.command;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.util.MainCommandParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;

@Slf4j
@CommandHandler
public class HelpCommand extends Command {

    @Autowired
    private AllCommands commands;

     /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name
     * @param description
     */

    public HelpCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(ClientMessage message) {
        List<Command> allCommands = commands.getCommands();

        StringBuilder replyMessage = new StringBuilder();

        for (Command command : allCommands) {
            replyMessage.append("\t")
                    .append(command.getName())
                    .append(" - ")
                    .append(command.getDescription())
                    .append("\n");

        }
        replyMessage.append("\n\nCommand prefix if: " + MainCommandParser.COMMAND_PREFIX);
        ServerMessage serverMessage = ServerMessage.builder()
                .disMessage(message.getDisMessage())
                .author("Available commands:")
                .color(new Color(108, 92, 231))
                .description(replyMessage.toString())
                .footer("Have good play")
                .message(message)
                .build();
        messageSender.sendMessage(serverMessage);
    }
}
