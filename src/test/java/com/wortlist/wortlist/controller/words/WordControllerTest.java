package com.wortlist.wortlist.controller.words;

import com.wortlist.wortlist.common.DTO.words.CreateWordRequest;
import com.wortlist.wortlist.common.DTO.words.PatchWordRequest;
import com.wortlist.wortlist.common.DTO.words.UpdateWordRequest;
import com.wortlist.wortlist.common.DTO.words.WordResponse;
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
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordControllerTest {

    @Mock
    private WordService wordService;

    @InjectMocks
    private WordController wordController;

    private WordResponse wordResponse;
    private CreateWordRequest createWordRequest;
    private UpdateWordRequest updateWordRequest;
    private PatchWordRequest patchWordRequest;

    @BeforeEach
    void setUp() {
        wordResponse = new WordResponse(1L, "test", "test", "test", "A1");
        createWordRequest = new CreateWordRequest();
        createWordRequest.setWord("test");
        createWordRequest.setMeaning("test");
        createWordRequest.setLevel("A1");
        updateWordRequest = new UpdateWordRequest();
        updateWordRequest.setWord("updated");
        updateWordRequest.setMeaning("updated");
        updateWordRequest.setLevel("B1");
        patchWordRequest = new PatchWordRequest();
        patchWordRequest.setWord("patched");
    }

    @Test
    void create_ShouldReturnCreatedWord() {
        when(wordService.create(any(CreateWordRequest.class))).thenReturn(wordResponse);

        WordResponse result = wordController.create(createWordRequest);

        assertEquals(wordResponse, result);
        verify(wordService, times(1)).create(createWordRequest);
    }

    @Test
    void findAll_ShouldReturnPageOfWords() {
        Pageable pageable = PageRequest.of(0, 10);
        List<WordResponse> wordList = Arrays.asList(wordResponse);
        Page<WordResponse> wordPage = new PageImpl<>(wordList, pageable, wordList.size());

        when(wordService.findAll(any(Pageable.class))).thenReturn(wordPage);

        Page<WordResponse> result = wordController.findAll(null, pageable);

        assertEquals(wordPage, result);
        verify(wordService, times(1)).findAll(pageable);
    }

    @Test
    void findById_ShouldReturnWord() {
        when(wordService.findById(anyLong())).thenReturn(wordResponse);

        WordResponse result = wordController.findById(1L);

        assertEquals(wordResponse, result);
        verify(wordService, times(1)).findById(1L);
    }

    @Test
    void delete_ShouldCallService() {
        doNothing().when(wordService).delete(anyLong());

        wordController.delete(1L);

        verify(wordService, times(1)).delete(1L);
    }

    @Test
    void update_ShouldReturnUpdatedWord() {
        when(wordService.update(anyLong(), any(UpdateWordRequest.class))).thenReturn(wordResponse);

        WordResponse result = wordController.update(1L, updateWordRequest);

        assertEquals(wordResponse, result);
        verify(wordService, times(1)).update(1L, updateWordRequest);
    }

    @Test
    void patch_ShouldReturnPatchedWord() {
        when(wordService.patch(anyLong(), any(PatchWordRequest.class))).thenReturn(wordResponse);

        WordResponse result = wordController.patch(1L, patchWordRequest);

        assertEquals(wordResponse, result);
        verify(wordService, times(1)).patch(1L, patchWordRequest);
    }
}