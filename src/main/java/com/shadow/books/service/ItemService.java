package com.shadow.books.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shadow.books.domain.Item;

public interface ItemService {

	Item add(Item inventory) throws IOException;

	Item update(Item inventory);

	Page<Item> list(int page, int size);

	Optional<Item> finById(Long id);

	void delete(Long id);

	Page<Item> listByCategoryGroupByLanguage(String category, String language, Pageable pageable);

	Map<String, List<Item>> listByCategoryGroupByLanguage(String category, Pageable pageable);

	List<Item> search(String name);

//	Map<String, List<Item>> search(String name);


}
