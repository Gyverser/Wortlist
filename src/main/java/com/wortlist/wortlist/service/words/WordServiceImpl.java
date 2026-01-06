package com.wortlist.wortlist.service.words;

import com.wortlist.wortlist.common.DTO.words.CreateWordRequest;
import com.wortlist.wortlist.common.DTO.words.PatchWordRequest;
import com.wortlist.wortlist.common.DTO.words.UpdateWordRequest;
import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.common.Exception.NotFoundException;
import com.wortlist.wortlist.entity.Words;
import com.wortlist.wortlist.repository.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WordServiceImpl implements WordService {

    private final WordRepository repository;

    public WordServiceImpl(WordRepository repository) {
        this.repository = repository;
    }

    @Override
    public WordResponse create(CreateWordRequest request) {
        if (repository.existsByWordAndLevel(request.getWord(), request.getLevel())) {
            throw new IllegalArgumentException("Word already exists for this level.");
        }

        Words word = new Words(
                request.getWord(),
                request.getMeaning(),
                request.getSentence(),
                request.getLevel()
        );

        Words saved = repository.save(word);

        return mapToResponse(saved);
    }

    @Override
    public Page<WordResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<WordResponse> findByLevel(String level, Pageable pageable) {
        return repository.findByLevel(level, pageable)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public WordResponse findById(Long id) {
        Words word = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Word not found."));
        return mapToResponse(word);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Word not found.");
        }

        repository.deleteById(id);

    }

    @Override
    public WordResponse update(Long id, UpdateWordRequest request) {
        Words word = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Word not found"));

        word.setWord(request.getWord());
        word.setMeaning(request.getMeaning());
        word.setSentence(request.getSentence());
        word.setLevel(request.getLevel());

        return mapToResponse(repository.save(word));
    }

    @Override
    public WordResponse patch(Long id, PatchWordRequest request) {
        Words word = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Word not found"));

        if (request.getWord() != null) {
            word.setWord(request.getWord());
        }
        if (request.getMeaning() != null) {
            word.setMeaning(request.getMeaning());
        }
        if (request.getSentence() != null) {
            word.setSentence(request.getSentence());
        }
        if (request.getLevel() != null) {
            word.setLevel(request.getLevel());
        }

        return mapToResponse(repository.save(word));
    }

    private WordResponse mapToResponse(Words word) {
        return new WordResponse(
                word.getId(),
                word.getWord(),
                word.getMeaning(),
                word.getSentence(),
                word.getLevel()
        );
    }
}
