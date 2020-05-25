package com.yabcompany.discord.command;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.RussianRouletteGame;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.service.RussianRouletteService;
import com.yabcompany.discord.repository.UserRepository;
import com.yabcompany.discord.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.yabcompany.discord.util.MainCommandParser.COMMAND_SEPARATOR;
import static com.yabcompany.discord.util.MessageSender.BLUE_COLOR;
import static com.yabcompany.discord.util.MessageSender.RED_COLOR;

@CommandHandler
@Slf4j
public class RussianRouletteCommand extends Command {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RussianRouletteService russianRouletteService;

    @Autowired
    private AllCommands allCommands;

    private static final int GAME_WAIT_TIME = 60;

    /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name
     * @param description
     */
    public RussianRouletteCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(ClientMessage message) {
        String user = message.getUsername();
        Optional<com.yabcompany.discord.model.User> dbUser = userRepository.findUserByUsername(user);
        String rawMessage = message.getMessage();
        String[] split = rawMessage.split(COMMAND_SEPARATOR);
        try {
            int bet = Integer.parseInt(split[1]);
            if (dbUser.isPresent()) {
                if (dbUser.get().getMoney() >= bet) {
                    startGame(dbUser.get(), bet, message);
                }
            } else {
                com.yabcompany.discord.model.User u = userService.saveUser(user);
                startGame(u, bet, message);
            }

        } catch (NumberFormatException n) {
            log.error("Command argument error");

            ServerMessage serverMessage = ServerMessage.builder()
                    .disMessage(message.getDisMessage())
                    .author("Cant understand command:")
                    .color(RED_COLOR)
                    .description(rawMessage)
                    .footer("Have good play")
                    .message(message)
                    .build();


            messageSender.sendMessage(serverMessage);
        }


    }


    private void startGame(com.yabcompany.discord.model.User user, int pot, ClientMessage message) {
        RussianRouletteGame game = russianRouletteService.createGame(user, pot);

        long time = GAME_WAIT_TIME - LocalDateTime.now().minusSeconds(game.getDateCreated().getSecond()).getSecond();
        ServerMessage serverMessage = ServerMessage.builder()
                .disMessage(message.getDisMessage())
                .author("User " + user.getUsername() + " has created a game")
                .color(BLUE_COLOR)
                .description("Pot is " + pot + "\nUse _join #" + game.getHash() + " to join")
                .footer("You have: " +
                        time + " seconds")
                .message(message)
                .build();
        messageSender.sendMessage(serverMessage);
    }
}
