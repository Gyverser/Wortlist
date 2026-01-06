package com.wortlist.wortlist.controller.words;

import com.wortlist.wortlist.common.DTO.words.CreateWordRequest;
import com.wortlist.wortlist.common.DTO.words.PatchWordRequest;
import com.wortlist.wortlist.common.DTO.words.UpdateWordRequest;
import com.wortlist.wortlist.common.DTO.words.WordResponse;
import com.wortlist.wortlist.service.words.WordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService service;

    public WordController(WordService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WordResponse create(@RequestBody @Valid CreateWordRequest request) {
        return service.create(request);
    }

    @GetMapping
    public Page<WordResponse> findAll(
            @RequestParam(required = false) String level,
            @PageableDefault(size = 10, sort = "word")Pageable pageable) {
        if (level != null) {
            return service.findByLevel(level, pageable);
        }
        return service.findAll(pageable);

    }

    @GetMapping("/{id}")
    public WordResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public WordResponse update(@PathVariable Long id, @RequestBody @Valid UpdateWordRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}")
    public WordResponse patch(@PathVariable Long id, @RequestBody @Valid PatchWordRequest request) {
        return service.patch(id, request);
    }
}
