package com.wortlist.wortlist.controller.quiz;

import com.wortlist.wortlist.common.DTO.quiz.LeaderboardRequest;
import com.wortlist.wortlist.common.DTO.quiz.LeaderboardResponse;
import com.wortlist.wortlist.common.DTO.quiz.QuizQuestionResponse;
import com.wortlist.wortlist.entity.Leaderboard;
import com.wortlist.wortlist.repository.LeaderboardRepository;
import com.wortlist.wortlist.service.quiz.QuizService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @InjectMocks
    private QuizController quizController;

    private QuizQuestionResponse quizQuestionResponse;
    private LeaderboardRequest leaderboardRequest;
    private List<LeaderboardResponse> leaderboardResponses;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        quizQuestionResponse = new QuizQuestionResponse("test", Arrays.asList("option1", "option2", "option3"), "correct");
        leaderboardRequest = new LeaderboardRequest();
        leaderboardRequest.setPlayerName("testUser");
        leaderboardRequest.setScore(100);
        leaderboardResponses = Arrays.asList(
            new LeaderboardResponse("user1", 150),
            new LeaderboardResponse("user2", 120)
        );
        session = mock(HttpSession.class);
    }

    @Test
    void getQuestion_ShouldReturnQuizQuestion() {
        when(quizService.getRandomQuestion()).thenReturn(quizQuestionResponse);

        QuizQuestionResponse result = quizController.getQuestion();

        assertEquals(quizQuestionResponse, result);
        verify(quizService, times(1)).getRandomQuestion();
    }

    @Test
    void submitScore_ShouldCallRepository() {
        when(session.getAttribute("scoreSubmitted")).thenReturn(null);

        quizController.submitScore(leaderboardRequest, session);

        verify(leaderboardRepository, times(1)).save(any(Leaderboard.class));
    }

    @Test
    void getLeaderboard_ShouldReturnLeaderboard() {
        List<Leaderboard> leaderboards = Arrays.asList(
            new Leaderboard(150, "user1"),
            new Leaderboard(120, "user2")
        );
        when(leaderboardRepository.findTop10ByOrderByScoreDesc()).thenReturn(leaderboards);

        List<LeaderboardResponse> result = quizController.getLeaderboard();

        assertEquals(2, result.size());
        verify(leaderboardRepository, times(1)).findTop10ByOrderByScoreDesc();
    }
}