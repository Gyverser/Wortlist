package com.wortlist.wortlist.common.DTO.quiz;

import java.util.List;

public record QuizQuestionResponse(
        String word,
        List<String> options,
        String correctAnswer
) {
}
