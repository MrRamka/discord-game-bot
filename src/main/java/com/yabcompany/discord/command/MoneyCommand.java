package com.yabcompany.discord.command;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.model.User;
import com.yabcompany.discord.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.yabcompany.discord.util.MessageSender.BLUE_COLOR;


public class MoneyCommand extends Command {

    @Autowired
    private UserRepository userRepository;

    /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name        name
     * @param description description
     */
    public MoneyCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(ClientMessage message) {
        Optional<User> u = userRepository.findUserByUsername(message.getUsername());

        if (u.isPresent()) {
            User user = u.get();
            String author = "Yor money (" + user.getUsername() + ") is " + user.getMoney();
            ServerMessage serverMessage = messageSender.createMessage(message, author, "", "", BLUE_COLOR);
            messageSender.sendMessage(serverMessage);
        }


    }
}
