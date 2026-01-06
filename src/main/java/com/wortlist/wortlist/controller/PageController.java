package com.wortlist.wortlist.controller;

import com.wortlist.wortlist.common.DTO.quiz.LeaderboardResponse;
import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.service.learn.LearnService;
import com.wortlist.wortlist.service.quiz.QuizService;
import com.wortlist.wortlist.service.words.WordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    private final WordService wordService;
    private final LearnService learnService;
    private final QuizService quizService;

    public PageController(WordService wordService, LearnService learnService, QuizService quizService) {
        this.wordService = wordService;
        this.learnService = learnService;
        this.quizService = quizService;
    }

    @GetMapping("/words")
    public String wordsPage(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model,
                            Authentication authentication) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("level"));
        Page<WordResponse> wordsPage = wordService.findAll(pageable);
        model.addAttribute("words", wordsPage);

        if (authentication != null && authentication.isAuthenticated()) {
            boolean isStudent = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"));
            
            if (isStudent) {
                return "words-student";
            }
        }
        
        return "words";
    }

    @GetMapping("/learn")
    public String learnPage(Model model) {
        WordResponse randomWord = learnService.getWord();
        model.addAttribute("word", randomWord);
        return "learn";
    }

    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz";
    }

    @GetMapping("/leaderboard")
    public String showLeaderboard(Model model) {
        List<LeaderboardResponse> leaderboard = quizService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        return "leaderboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/words";
    }
}