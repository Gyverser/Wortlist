package com.wortlist.wortlist.controller;

import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.service.learn.LearnService;
import com.wortlist.wortlist.service.quiz.QuizService;
import com.wortlist.wortlist.service.words.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageControllerTest {

    @Mock
    private WordService wordService;

    @Mock
    private LearnService learnService;

    @Mock
    private QuizService quizService;

    @Mock
    private Model model;

    @InjectMocks
    private PageController pageController;

    private WordResponse wordResponse;
    private Page<WordResponse> wordPage;

    @BeforeEach
    void setUp() {
        wordResponse = new WordResponse(1L, "test", "test", "test", "A1");
        List<WordResponse> wordList = Arrays.asList(wordResponse);
        Pageable pageable = PageRequest.of(0, 10);
        wordPage = new PageImpl<>(wordList, pageable, wordList.size());
    }

    @Test
    void wordsPage_ShouldReturnWordsTemplate() {
        when(wordService.findAll(any(Pageable.class))).thenReturn(wordPage);

        String result = pageController.wordsPage(0, 10, model, null);

        assertEquals("words", result);
        verify(wordService, times(1)).findAll(any(Pageable.class));
        verify(model, times(1)).addAttribute(eq("words"), any());
    }

    @Test
    void wordsPage_ShouldReturnStudentTemplateForStudentRole() {
        when(wordService.findAll(any(Pageable.class))).thenReturn(wordPage);
        
        // Create mock authentication with STUDENT role
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        
        Collection<GrantedAuthority> authorities = Arrays.asList(
            new SimpleGrantedAuthority("ROLE_STUDENT")
        );
        when(auth.getAuthorities()).thenReturn((Collection) authorities);

        String result = pageController.wordsPage(0, 10, model, auth);

        assertEquals("words-student", result);
        verify(wordService, times(1)).findAll(any(Pageable.class));
        verify(model, times(1)).addAttribute(eq("words"), any());
    }

    @Test
    void learnPage_ShouldReturnLearnTemplate() {
        when(learnService.getWord()).thenReturn(wordResponse);

        String result = pageController.learnPage(model);

        assertEquals("learn", result);
        verify(learnService, times(1)).getWord();
        verify(model, times(1)).addAttribute(eq("word"), any());
    }

    @Test
    void quizPage_ShouldReturnQuizTemplate() {
        String result = pageController.quizPage();

        assertEquals("quiz", result);
    }

    @Test
    void showLeaderboard_ShouldReturnLeaderboardTemplate() {
        String result = pageController.showLeaderboard(model);

        assertEquals("leaderboard", result);
        verify(model, times(1)).addAttribute(eq("leaderboard"), any());
    }
}