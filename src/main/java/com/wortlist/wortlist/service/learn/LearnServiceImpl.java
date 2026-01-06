package com.wortlist.wortlist.service.learn;

import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.entity.Words;
import com.wortlist.wortlist.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LearnServiceImpl implements LearnService {
    private final WordRepository wordRepository;
    Random random = new Random();

    public LearnServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public WordResponse getWord() {
        List<Words> allWords = wordRepository.findAll();
        if (allWords.isEmpty()) {
            throw new IllegalStateException("No words available in the database.");
        }
        Words word = allWords.get(random.nextInt(allWords.size()));
        return new WordResponse(word.getId(), word.getWord(), word.getMeaning(), word.getSentence(), word.getLevel());
    }
}
