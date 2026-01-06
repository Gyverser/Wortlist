package com.wortlist.wortlist.service.words;

import com.wortlist.wortlist.common.DTO.words.CreateWordRequest;
import com.wortlist.wortlist.common.DTO.words.PatchWordRequest;
import com.wortlist.wortlist.common.DTO.words.UpdateWordRequest;
import com.wortlist.wortlist.common.DTO.words.WordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WordService {

    WordResponse create(CreateWordRequest request);
    Page<WordResponse> findAll(Pageable pageable);
    Page<WordResponse> findByLevel(String level, Pageable pageable);
    WordResponse findById(Long id);
    void delete(Long id);
    WordResponse update(Long id, UpdateWordRequest request);
    WordResponse patch(Long id, PatchWordRequest request);

 }
