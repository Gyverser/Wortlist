package com.wortlist.wortlist.repository;

import com.wortlist.wortlist.entity.Words;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Words, Long> {
    boolean existsByWordAndLevel(String word, String level);
    Page<Words> findByLevel(String level, Pageable page);

}
