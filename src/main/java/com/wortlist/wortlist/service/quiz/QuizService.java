package com.wortlist.wortlist.service.quiz;

import com.wortlist.wortlist.common.DTO.quiz.LeaderboardResponse;
import com.wortlist.wortlist.common.DTO.quiz.QuizQuestionResponse;

import java.util.List;

public interface QuizService {
    QuizQuestionResponse getRandomQuestion();
    List<LeaderboardResponse> getLeaderboard();
}
