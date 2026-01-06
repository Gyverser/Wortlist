package com.wortlist.wortlist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vocabulary")
@Getter
@Setter
@NoArgsConstructor
public class Words {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String word;

    @Column(nullable = false)
    String meaning;

    @Column(length = 255)
    String sentence;

    @Column(nullable = false)
    String level;

    public Words(String word, String meaning, String sentence, String level) {
        this.word = word;
        this.meaning = meaning;
        this.sentence = sentence;
        this.level = level;
    }
}
