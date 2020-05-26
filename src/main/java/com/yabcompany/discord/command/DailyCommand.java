package com.yabcompany.discord.command;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.model.User;
import com.yabcompany.discord.repository.UserRepository;
import com.yabcompany.discord.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.yabcompany.discord.util.MessageSender.BLUE_COLOR;
import static com.yabcompany.discord.util.MessageSender.RED_COLOR;

public class DailyCommand extends Command {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public static final int DAILY_PRIZE = 2500;

    /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name        name
     * @param description description
     */
    public DailyCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(ClientMessage message) {
        Optional<User> u = userRepository.findUserByUsername(message.getUsername());

        if (u.isPresent()) {
            User user = u.get();
            if (LocalDateTime.now().minusHours(user.getLastDailyMoneyReceive().getHour()).getHour() >= 12) {
                user = userService.addDailyMoney(user, DAILY_PRIZE);
                String author = "Yor took (" + user.getUsername() + ")  " + DAILY_PRIZE;
                String description = "Now yor money is " + user.getMoney();
                ServerMessage serverMessage = messageSender.createMessage(message, author, description, "", BLUE_COLOR);
                messageSender.sendMessage(serverMessage);
            } else {
                String author = "You already took daily money";
                ServerMessage serverMessage = messageSender.createMessage(message, author, "", "", BLUE_COLOR);
                messageSender.sendMessage(serverMessage);
            }
        } else {
            String author = "Play game first";
            ServerMessage serverMessage = messageSender.createMessage(message, author, "", "", RED_COLOR);
            messageSender.sendMessage(serverMessage);
        }
    }
}
