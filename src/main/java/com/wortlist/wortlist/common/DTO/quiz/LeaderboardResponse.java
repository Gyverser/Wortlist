package com.wortlist.wortlist.common.DTO.quiz;

import java.time.LocalDateTime;

public record LeaderboardResponse(
        String playerName,
        Integer score
) {
}
