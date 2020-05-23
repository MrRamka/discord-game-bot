package com.yabcompany.discord.command;

import com.yabcompany.discord.util.MessageSender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
public abstract class Command {

    /**
     * Name of command
     */
    protected String name;

    /**
     * Message from Discord JDA
     */
    protected String description;

    @Autowired
    protected MessageSender messageSender;

    /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name
     * @param description
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Execute command
     */
    public abstract void execute(Message message);


}
