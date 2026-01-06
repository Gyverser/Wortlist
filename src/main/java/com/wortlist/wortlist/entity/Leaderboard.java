package com.wortlist.wortlist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboard")
@Getter
@Setter
@NoArgsConstructor
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", nullable = false, length = 50)
    private String playerName;

    @Column(nullable = false)
    private int score;

    public Leaderboard(int score, String playerName) {
        this.score = score;
        this.playerName = playerName;
    }
}
