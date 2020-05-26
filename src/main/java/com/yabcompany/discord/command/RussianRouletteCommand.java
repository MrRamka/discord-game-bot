package com.yabcompany.discord.command;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.RussianRouletteGame;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.model.User;
import com.yabcompany.discord.repository.RussianRouletteRepository;
import com.yabcompany.discord.service.RussianRouletteService;
import com.yabcompany.discord.repository.UserRepository;
import com.yabcompany.discord.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static com.yabcompany.discord.util.MainCommandParser.COMMAND_SEPARATOR;
import static com.yabcompany.discord.util.MessageSender.*;

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
    private RussianRouletteRepository russianRouletteRepository;

    public static final int GAME_WAIT_TIME = 60;

    private static final int ROUND_WAIT_TIME = 5;

    /**
     * Constructor for command
     * Dont forget call super(name, message)
     *
     * @param name        name
     * @param description description
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
            String messageTitle = "Cant understand command:";
            ServerMessage serverMessage = messageSender.createMessage(message, messageTitle, rawMessage, "", RED_COLOR);
            messageSender.sendMessage(serverMessage);
        }


    }


    private void startGame(com.yabcompany.discord.model.User user, int pot, ClientMessage message) {
        RussianRouletteGame game = russianRouletteService.createGame(user, pot);

        StringBuilder playersMessage = new StringBuilder();
        for (User u : game.getPlayers()) {
            playersMessage.append(u.getUsername()).append("\n");
        }


        long time = GAME_WAIT_TIME - LocalDateTime.now().minusSeconds(game.getDateCreated().getSecond()).getSecond();
        String gameCreated = "User " + user.getUsername() + " has created a game";
        String description = "Members: \n" + playersMessage.toString() +
                "Pot is " + pot + "\nUse \n_join " + game.getHash() + "\n to join";
        String footer = "You have: " + time + " seconds";
        ServerMessage serverMessage = messageSender.createMessage(message, gameCreated, description, footer, BLUE_COLOR);
        messageSender.sendMessage(serverMessage);

        waitGame(game, message);
    }

    private void waitGame(RussianRouletteGame game, ClientMessage message) {
        Runnable runnable = () -> {
            try {
                System.out.println("Start wait time");
                Thread.sleep(1000 * GAME_WAIT_TIME);
                System.out.println("End wait time");
                getNextDeath(game.getHash(), message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void getNextDeath(String hash, ClientMessage message) {
        Optional<RussianRouletteGame> op = russianRouletteRepository.getByHash(hash);
        RussianRouletteGame game = op.get();
        Random random = new Random();
        List<User> users = new ArrayList<>(game.getPlayers());
        int pot = game.getPot() / users.size();
        if (users.size() == 1) {
            String title = "No players((";
            String description = "Take back you money";
            String roundWaitMessage = "";
            ServerMessage serverMessage = messageSender.createMessage(message, title, description, roundWaitMessage, GREEN_COLOR);
            messageSender.sendMessage(serverMessage);
            userService.addMoney(users.get(0), game.getPot());
        }

        while (users.size() > 1) {
            /*
                Getting random user
             */
            User u = users.get(
                    random.nextInt(users.size() - 1)
            );

            StringBuilder playersMessage = new StringBuilder();
            for (User us : game.getPlayers()) {
                playersMessage.append(us.getUsername()).append("\n");
            }
            if (users.size() == 2) {
                String authorField = "Winner " + users.get(0).getUsername();
                String prizePool = "Prize: " + game.getPot();
                ServerMessage serverMessage = messageSender.createMessage(message, authorField, prizePool, "", GREEN_COLOR);
                messageSender.sendMessage(serverMessage);
                userService.addMoney(users.get(0), game.getPot());
            } else {
                String title = "User " + u.getUsername() + " put his head in the wrong place";
                String description = "Members alive: \n" + playersMessage.toString();
                String roundWaitMessage = "Next player in " + ROUND_WAIT_TIME + "seconds";
                ServerMessage serverMessage = messageSender.createMessage(message, title, description, roundWaitMessage, RED_COLOR);
                messageSender.sendMessage(serverMessage);
            }
            users.remove(u);
            userService.subMoney(u, pot);
            try {
                Thread.sleep(1000 * ROUND_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


}
