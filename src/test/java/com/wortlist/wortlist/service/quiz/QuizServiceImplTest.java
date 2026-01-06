package com.wortlist.wortlist.service.quiz;

import com.wortlist.wortlist.common.DTO.quiz.LeaderboardResponse;
import com.wortlist.wortlist.common.DTO.quiz.QuizQuestionResponse;
import com.wortlist.wortlist.entity.Leaderboard;
import com.wortlist.wortlist.entity.Words;
import com.wortlist.wortlist.repository.LeaderboardRepository;
import com.wortlist.wortlist.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private WordRepository wordRepository;

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    private Words word1;
    private Words word2;
    private Words word3;
    private Words word4;
    private List<Words> wordsList;

    @BeforeEach
    void setUp() {
        word1 = new Words("word1", "meaning1", "sentence1", "A1");
        word1.setId(1L);
        word2 = new Words("word2", "meaning2", "sentence2", "A1");
        word2.setId(2L);
        word3 = new Words("word3", "meaning3", "sentence3", "A1");
        word3.setId(3L);
        word4 = new Words("word4", "meaning4", "sentence4", "A1");
        word4.setId(4L);
        wordsList = Arrays.asList(word1, word2, word3, word4);
    }

    @Test
    void getRandomQuestion_ShouldReturnQuizQuestionResponse() {
        when(wordRepository.findAll()).thenReturn(wordsList);

        QuizQuestionResponse result = quizService.getRandomQuestion();

        assertNotNull(result);
        assertNotNull(result.options());
        assertEquals(4, result.options().size());
        assertNotNull(result.correctAnswer());
        verify(wordRepository, times(1)).findAll();
    }

    @Test
    void getRandomQuestion_ShouldThrowExceptionWhenLessThan4Words() {
        List<Words> insufficientWords = Arrays.asList(word1, word2, word3);
        when(wordRepository.findAll()).thenReturn(insufficientWords);

        assertThrows(IllegalArgumentException.class, () -> quizService.getRandomQuestion());
        verify(wordRepository, times(1)).findAll();
    }

    @Test
    void getLeaderboard_ShouldReturnLeaderboardResponses() {
        List<Leaderboard> leaderboards = Arrays.asList(
            new Leaderboard(150, "user1"),
            new Leaderboard(120, "user2")
        );

        when(leaderboardRepository.findTop10ByOrderByScoreDesc()).thenReturn(leaderboards);

        List<LeaderboardResponse> result = quizService.getLeaderboard();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(leaderboardRepository, times(1)).findTop10ByOrderByScoreDesc();
    }
}