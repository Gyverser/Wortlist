package com.wortlist.wortlist.common.DTO.words;

public record WordResponse(
        Long id,
        String word,
        String meaning,
        String sentence,
        String level
) {
}
