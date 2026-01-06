package com.wortlist.wortlist.controller.quiz;

import com.wortlist.wortlist.common.DTO.quiz.LeaderboardRequest;
import com.wortlist.wortlist.common.DTO.quiz.LeaderboardResponse;
import com.wortlist.wortlist.common.DTO.quiz.QuizQuestionResponse;
import com.wortlist.wortlist.entity.Leaderboard;
import com.wortlist.wortlist.repository.LeaderboardRepository;
import com.wortlist.wortlist.service.quiz.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;
    private final LeaderboardRepository leaderboardRepository;

    public QuizController(QuizService quizService, LeaderboardRepository leaderboardRepository) {
        this.quizService = quizService;
        this.leaderboardRepository = leaderboardRepository;
    }

    @GetMapping("/question")
    public QuizQuestionResponse getQuestion() {
        return quizService.getRandomQuestion();
    }

    @PostMapping("/leaderboard")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitScore(@RequestBody @Valid LeaderboardRequest request,
                            HttpSession session) {
        Boolean alreadySubmitted = (Boolean) session.getAttribute("scoreSubmitted");

        if (Boolean.TRUE.equals(alreadySubmitted)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Score already submitted");
        }
        
        System.out.println("Submitting score: " + request.getScore() + " for player: " + request.getPlayerName());
        
        Leaderboard leaderboardEntry = new Leaderboard(request.getScore(), request.getPlayerName());
        leaderboardRepository.save(leaderboardEntry);
        
        System.out.println("Score submitted successfully with ID: " + leaderboardEntry.getId());
        
        session.setAttribute("scoreSubmitted", true);
    }

    @GetMapping("/leaderboard")
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
