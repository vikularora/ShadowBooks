package com.shadow.books.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shadow.books.domain.Item;

public interface ItemService {

	Item add(Item inventory);

	Item update(Item inventory);

	Page<Item> list(int page, int size);

	Optional<Item> finById(Long id);

	void delete(Long id);

	Page<Item> listByCategoryGroupByLanguage(String category, String language, Pageable pageable);

}
