package com.yabcompany.discord.command;

import com.yabcompany.discord.repository.UserRepository;
import com.yabcompany.discord.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
@CommandHandler
public class RegistrationCommand extends Command {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name
     * @param description
     */
    public RegistrationCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(Message message) {
        User user = message.getAuthor();
        userService.saveUser(user);
    }
}
