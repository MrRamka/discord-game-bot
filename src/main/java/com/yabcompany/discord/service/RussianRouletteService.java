package com.yabcompany.discord.service;

import com.yabcompany.discord.model.RussianRouletteGame;
import com.yabcompany.discord.model.User;
import com.yabcompany.discord.repository.RussianRouletteRepository;
import com.yabcompany.discord.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RussianRouletteService {

    @Autowired
    private RussianRouletteRepository russianRouletteRepository;

    @Autowired
    private UserRepository userRepository;

    public RussianRouletteGame createGame(User user, int pot) {
        Set<User> players = new HashSet<>();
        players.add(user);
        RussianRouletteGame game = RussianRouletteGame.builder()
                .dateCreated(LocalDateTime.now())
                .hash(randomHash())
                .pot(pot)
                .players(players)
                .build();
        user.setMoney(user.getMoney() - pot);

        russianRouletteRepository.save(game);
        userRepository.save(user);
        return game;
    }


    public RussianRouletteGame addUser(User user, RussianRouletteGame game) {

        int pot = game.getPot() / game.getPlayers().size();

        if (user.getMoney() >= pot) {
            game.getPlayers().add(user);
            game.setPot(game.getPot() + pot);
            user.setMoney(user.getMoney() - game.getPot());
            russianRouletteRepository.save(game);
            userRepository.save(user);
        }
        return game;
    }


    private String randomHash() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
