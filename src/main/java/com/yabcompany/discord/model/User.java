package com.yabcompany.discord.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User {

    @Transient
    public static final Long START_MONEY_AMOUNT = 5000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private Long money = START_MONEY_AMOUNT;

    @Column(nullable = false)
    private LocalDateTime lastDailyMoneyReceive;

}
