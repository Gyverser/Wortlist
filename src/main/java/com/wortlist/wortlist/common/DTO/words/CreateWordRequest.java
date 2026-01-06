package com.wortlist.wortlist.common.DTO.words;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateWordRequest {
        @NotBlank(message = "Word is required")
        @Size(max = 50, message = "Word must be at most 50 characters")
        String word;

        @NotBlank(message = "Meaning is required")
        @Size(max = 100, message = "Meaning must be at most 100 characters")
        String meaning;

        @Size(max = 255, message = "Sentence must be at most 255 characters")
        String sentence;

        @NotBlank(message = "Level is required")
        @Pattern(
                regexp = "A1|A2|B1|B2|C1",
                message = "Level must be A1, A1, B1, B2, or C1"
        )
        String level;
}
