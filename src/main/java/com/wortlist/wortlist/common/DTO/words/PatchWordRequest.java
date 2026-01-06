package com.wortlist.wortlist.common.DTO.words;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchWordRequest {
    @Size(max = 50)
    private String word;

    @Size(max = 100)
    private String meaning;

    @Size(max = 255)
    private String sentence;

    @Pattern(regexp = "A1|A2|B1|B2|C1")
    private String level;
}
