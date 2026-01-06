package com.wortlist.wortlist.service.learn;

import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.entity.Words;
import com.wortlist.wortlist.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearnServiceImplTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private LearnServiceImpl learnService;

    private Words word;
    private WordResponse wordResponse;

    @BeforeEach
    void setUp() {
        word = new Words("test", "test", "test", "A1");
        word.setId(1L);
        wordResponse = new WordResponse(1L, "test", "test", "test", "A1");
    }

    @Test
    void getWord_ShouldReturnWordResponse() {
        List<Words> wordList = Arrays.asList(word);
        when(wordRepository.findAll()).thenReturn(wordList);

        WordResponse result = learnService.getWord();

        assertNotNull(result);
        assertEquals(wordResponse.word(), result.word());
        verify(wordRepository, times(1)).findAll();
    }

    @Test
    void getWord_ShouldThrowExceptionWhenNoWordsAvailable() {
        when(wordRepository.findAll()).thenReturn(List.of());

        assertThrows(IllegalStateException.class, () -> learnService.getWord());
        verify(wordRepository, times(1)).findAll();
    }
}