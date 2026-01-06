package com.wortlist.wortlist.controller.learn;

import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.service.learn.LearnService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learn")
public class LearnController {
    private final LearnService learnService;

    public LearnController(LearnService learnService) {
        this.learnService = learnService;
    }

    @GetMapping("/word")
    public WordResponse getWord() {
        return learnService.getWord();
    }
}
