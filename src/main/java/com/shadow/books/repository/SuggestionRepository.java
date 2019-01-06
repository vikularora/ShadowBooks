package com.shadow.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shadow.books.domain.Suggestion;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

}
