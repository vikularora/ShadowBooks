package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Item;
import com.shadow.books.repository.ItemRepository;
import com.shadow.books.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Override
	public Item add(Item inventory) {

		inventory.setDeleted(false);
		inventory.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		inventory.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return itemRepository.save(inventory);

	}

	@Override
	public Item update(Item item) {
		item.setDeleted(false);
		item.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return itemRepository.save(item);
	}

	@Override
	public Page<Item> list(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Item> inventoryList = itemRepository.findAll(pageable);
		return inventoryList;
	}

	@Override
	public Optional<Item> finById(Long id) {
		Optional<Item> optItem = itemRepository.findById(id);
		if (optItem.isPresent()) {
			optItem.get().setDiscountedPrice(optItem.get().getPrice()*optItem.get().getDiscount()/100);
		}
		return optItem;

	}

	@Override
	public void delete(Long id) {
		itemRepository.deleteById(id);
	}

	@Override
	public Page<Item> listByCategoryGroupByLanguage(String category, String language, Pageable pageable) {
		Page<Item> items = itemRepository.findByCategoryAndLanguage(category, language, pageable);
		
		items.getContent().forEach(item->item.setDiscountedPrice(item.getPrice()*item.getDiscount()/100));
		return items;
	}


}
