package com.shadow.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shadow.books.domain.Item;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

	Page<Item> findByCategoryAndLanguageAllIgnoreCase(String category, String language, Pageable pageable);

}
