package com.yabcompany.discord.service;

import com.yabcompany.discord.model.User;
import com.yabcompany.discord.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    public User getById(long id) {
//        return userRepository..(id);
//    }

    public User saveUser(net.dv8tion.jda.api.entities.User user) {
        return userRepository.save(
                com.yabcompany.discord.model.User.builder()
                        .username(user.getName())
                        .lastDailyMoneyReceive(LocalDateTime.now().minusDays(1))
                        .money(User.START_MONEY_AMOUNT)
                        .build()
        );
    }


}
