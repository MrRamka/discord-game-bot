package com.yabcompany.discord.util;

import com.yabcompany.discord.command.AllCommands;
import com.yabcompany.discord.command.Command;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MainCommandParser {

    public final static String COMMAND_PREFIX = "_";
    public final static String COMMAND_SEPARATOR = " ";

    @Autowired
    private AllCommands commands;


    public void parse(Message m) {
        Message message = m;
        String rawMessage = message.getContentRaw();
        /*
         * prefix + command
         */
        if (rawMessage.length() >= COMMAND_PREFIX.length() + 1 && !message.getAuthor().isBot()) {

            if (rawMessage.startsWith(COMMAND_PREFIX)) {
                String msg = rawMessage.substring(COMMAND_PREFIX.length());
                String[] splitMessage = msg.split(COMMAND_SEPARATOR);

                boolean flag = true;
                for (Command command : commands.getCommands()) {
                    if (command.getName().equals(splitMessage[0]) && flag) {
                        command.execute(message);
                        flag = false;
                    }
                }
                if (flag) {
                    commands.getHelpCommand().execute(message);
                }
            }
        }


    }

}
