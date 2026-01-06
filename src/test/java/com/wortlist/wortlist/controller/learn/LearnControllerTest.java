package com.wortlist.wortlist.controller.learn;

import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.service.learn.LearnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearnControllerTest {

    @Mock
    private LearnService learnService;

    @InjectMocks
    private LearnController learnController;

    private WordResponse wordResponse;

    @BeforeEach
    void setUp() {
        wordResponse = new WordResponse(1L, "test", "test", "test", "A1");
    }

    @Test
    void getWord_ShouldReturnWord() {
        when(learnService.getWord()).thenReturn(wordResponse);

        WordResponse result = learnController.getWord();

        assertEquals(wordResponse, result);
        verify(learnService, times(1)).getWord();
    }
}