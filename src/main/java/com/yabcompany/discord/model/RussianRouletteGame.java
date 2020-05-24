package com.yabcompany.discord.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RussianRouletteGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /*
    Used join to game
     */
    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private Integer pot;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<User> players;

    @ManyToMany()
    @JoinTable(
            name = "russian_roulette_game_players",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> players = new HashSet<>();


}
