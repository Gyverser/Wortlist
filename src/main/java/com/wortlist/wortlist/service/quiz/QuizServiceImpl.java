package com.wortlist.wortlist.service.quiz;

import com.wortlist.wortlist.common.DTO.quiz.LeaderboardResponse;
import com.wortlist.wortlist.common.DTO.quiz.QuizQuestionResponse;
import com.wortlist.wortlist.entity.Leaderboard;
import com.wortlist.wortlist.entity.Words;
import com.wortlist.wortlist.repository.LeaderboardRepository;
import com.wortlist.wortlist.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizServiceImpl implements QuizService {
    private final WordRepository wordRepository;
    private final LeaderboardRepository leaderboardRepository;
    Random random = new Random();

    public QuizServiceImpl(WordRepository wordRepository, LeaderboardRepository leaderboardRepository) {
        this.wordRepository = wordRepository;
        this.leaderboardRepository = leaderboardRepository;
    }

    public QuizQuestionResponse getRandomQuestion() {
        List<Words> allWords = wordRepository.findAll();

        if (allWords.size() < 4) {
            throw new IllegalArgumentException("4 words are needed for the quiz");
        }

        Words correctWord = allWords.get(random.nextInt(allWords.size()));
        String correctAnswer = correctWord.getMeaning();

        Set<String> options = new HashSet<>();
        options.add(correctAnswer);

        while (options.size() < 4) {
            Words randomWord = allWords.get(random.nextInt(allWords.size()));
            options.add(randomWord.getMeaning());
        }

        List<String> shuffledOptions = new ArrayList<>(options);
        Collections.shuffle(shuffledOptions);

        return new QuizQuestionResponse(
                correctWord.getWord(),
                shuffledOptions,
                correctAnswer
        );
    }

    @Override
    public List<LeaderboardResponse> getLeaderboard() {
        return leaderboardRepository
                .findTop10ByOrderByScoreDesc()
                .stream()
                .map(e -> new LeaderboardResponse(
                        e.getPlayerName(),
                        e.getScore()
                ))
                .toList();
    }
}
