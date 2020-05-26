package com.yabcompany.discord.command;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.RussianRouletteGame;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.model.User;
import com.yabcompany.discord.repository.RussianRouletteRepository;
import com.yabcompany.discord.repository.UserRepository;
import com.yabcompany.discord.service.RussianRouletteService;
import com.yabcompany.discord.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.yabcompany.discord.command.RussianRouletteCommand.GAME_WAIT_TIME;
import static com.yabcompany.discord.util.MessageSender.GREEN_COLOR;
import static com.yabcompany.discord.util.MessageSender.RED_COLOR;

public class JoinGameCommand extends Command {

    @Autowired
    private RussianRouletteRepository russianRouletteRepository;

    @Autowired
    private RussianRouletteService russianRouletteService;

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
    public JoinGameCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(ClientMessage message) {
        String gameHash = message.getMessage().split(" ")[1];
        Optional<RussianRouletteGame> game = russianRouletteRepository.getByHash(gameHash);
        Optional<User> user = userRepository.findUserByUsername(message.getUsername());
        User u = user.orElseGet(() -> userService.saveUser(message.getUsername()));

        if (game.isPresent()) {
            RussianRouletteGame g = game.get();
            g = russianRouletteService.addUser(u, g);

            StringBuilder playersMessage = new StringBuilder();
            for (User user1 : g.getPlayers()) {
                playersMessage.append(user1.getUsername()).append("\n");
            }
            String authorField = "You joined to game:";
            String description = "Pot is: " + g.getPot() + "\n" +
                    "Members: \n" + playersMessage.toString();
            long time = GAME_WAIT_TIME - LocalDateTime.now().minusSeconds(g.getDateCreated().getSecond()).getSecond();
            String timeField = "Time left " + time + " seconds";
            ServerMessage serverMessage = messageSender.createMessage(message, authorField, description, timeField, GREEN_COLOR);
            messageSender.sendMessage(serverMessage);

        } else {
            String authorField = "No game with this hash:";
            String description = "";
            String timeField = "Try use help command";
            ServerMessage serverMessage = messageSender.createMessage(message, authorField, description, timeField, RED_COLOR);
            messageSender.sendMessage(serverMessage);

        }


    }
}
