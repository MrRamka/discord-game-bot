package com.yabcompany.discord.repository;

import com.yabcompany.discord.model.RussianRouletteGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RussianRouletteRepository extends JpaRepository<RussianRouletteGame, Long> {
    Optional<RussianRouletteGame> getByHash(String hash);
}
