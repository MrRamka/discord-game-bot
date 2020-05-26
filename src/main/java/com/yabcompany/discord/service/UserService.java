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

    public User saveUser(String user) {
        return userRepository.save(
                com.yabcompany.discord.model.User.builder()
                        .username(user)
                        .lastDailyMoneyReceive(LocalDateTime.now().minusDays(1))
                        .money(User.START_MONEY_AMOUNT)
                        .build()
        );
    }

    public User addMoney(User user, int money) {
        user.setMoney(user.getMoney() + money);
        return userRepository.save(user);
    }


}
