package com.wortlist.wortlist.service.words;

import com.wortlist.wortlist.common.DTO.words.CreateWordRequest;
import com.wortlist.wortlist.common.DTO.words.PatchWordRequest;
import com.wortlist.wortlist.common.DTO.words.UpdateWordRequest;
import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.common.Exception.NotFoundException;
import com.wortlist.wortlist.entity.Words;
import com.wortlist.wortlist.repository.WordRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private WordServiceImpl wordService;

    private Words word;
    private WordResponse wordResponse;
    private CreateWordRequest createWordRequest;
    private UpdateWordRequest updateWordRequest;
    private PatchWordRequest patchWordRequest;

    @BeforeEach
    void setUp() {
        word = new Words("test", "test", "test", "A1");
        word.setId(1L);
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
    void create_ShouldReturnWordResponse() {
        when(wordRepository.existsByWordAndLevel(anyString(), anyString())).thenReturn(false);
        when(wordRepository.save(any(Words.class))).thenReturn(word);

        WordResponse result = wordService.create(createWordRequest);

        assertNotNull(result);
        assertEquals(wordResponse.word(), result.word());
        verify(wordRepository, times(1)).existsByWordAndLevel("test", "A1");
        verify(wordRepository, times(1)).save(any(Words.class));
    }

    @Test
    void create_ShouldThrowExceptionWhenWordExists() {
        when(wordRepository.existsByWordAndLevel(anyString(), anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> wordService.create(createWordRequest));
        verify(wordRepository, times(1)).existsByWordAndLevel("test", "A1");
        verify(wordRepository, never()).save(any(Words.class));
    }

    @Test
    void findAll_ShouldReturnPageOfWordResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Words> wordList = Arrays.asList(word);
        Page<Words> wordPage = new PageImpl<>(wordList, pageable, wordList.size());

        when(wordRepository.findAll(any(Pageable.class))).thenReturn(wordPage);

        Page<WordResponse> result = wordService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(wordRepository, times(1)).findAll(pageable);
    }

    @Test
    void findByLevel_ShouldReturnPageOfWordResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Words> wordList = Arrays.asList(word);
        Page<Words> wordPage = new PageImpl<>(wordList, pageable, wordList.size());

        when(wordRepository.findByLevel(anyString(), any(Pageable.class))).thenReturn(wordPage);

        Page<WordResponse> result = wordService.findByLevel("A1", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(wordRepository, times(1)).findByLevel("A1", pageable);
    }

    @Test
    void findById_ShouldReturnWordResponse() {
        when(wordRepository.findById(anyLong())).thenReturn(Optional.of(word));

        WordResponse result = wordService.findById(1L);

        assertNotNull(result);
        assertEquals(wordResponse.word(), result.word());
        verify(wordRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        when(wordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> wordService.findById(1L));
        verify(wordRepository, times(1)).findById(1L);
    }

    @Test
    void delete_ShouldCallRepository() {
        when(wordRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(wordRepository).deleteById(anyLong());

        wordService.delete(1L);

        verify(wordRepository, times(1)).existsById(1L);
        verify(wordRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowExceptionWhenNotFound() {
        when(wordRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> wordService.delete(1L));
        verify(wordRepository, times(1)).existsById(1L);
        verify(wordRepository, never()).deleteById(anyLong());
    }

    @Test
    void update_ShouldReturnUpdatedWordResponse() {
        Words updatedWord = new Words("updated", "updated", "test", "B1");
        updatedWord.setId(1L);
        when(wordRepository.findById(anyLong())).thenReturn(Optional.of(word));
        when(wordRepository.save(any(Words.class))).thenReturn(updatedWord);

        WordResponse result = wordService.update(1L, updateWordRequest);

        assertNotNull(result);
        assertEquals("updated", result.word());
        verify(wordRepository, times(1)).findById(1L);
        verify(wordRepository, times(1)).save(any(Words.class));
    }

    @Test
    void update_ShouldThrowExceptionWhenNotFound() {
        when(wordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> wordService.update(1L, updateWordRequest));
        verify(wordRepository, times(1)).findById(1L);
        verify(wordRepository, never()).save(any(Words.class));
    }

    @Test
    void patch_ShouldReturnPatchedWordResponse() {
        Words patchedWord = new Words("patched", "test", "test", "A1");
        patchedWord.setId(1L);
        when(wordRepository.findById(anyLong())).thenReturn(Optional.of(word));
        when(wordRepository.save(any(Words.class))).thenReturn(patchedWord);

        WordResponse result = wordService.patch(1L, patchWordRequest);

        assertNotNull(result);
        assertEquals("patched", result.word());
        verify(wordRepository, times(1)).findById(1L);
        verify(wordRepository, times(1)).save(any(Words.class));
    }

    @Test
    void patch_ShouldThrowExceptionWhenNotFound() {
        when(wordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> wordService.patch(1L, patchWordRequest));
        verify(wordRepository, times(1)).findById(1L);
        verify(wordRepository, never()).save(any(Words.class));
    }
}