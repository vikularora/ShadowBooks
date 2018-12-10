package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Item;
import com.shadow.books.domain.LineItem;
import com.shadow.books.repository.ItemRepository;
import com.shadow.books.repository.LineItemRepository;
import com.shadow.books.service.LineItemService;
import com.shadow.books.util.ShoppingCart;

@Service
public class LineItemServiceImpl implements LineItemService {

	@Autowired
	LineItemRepository lineItemRepository;

	@Autowired
	ItemRepository itemRepository;

	@Override
	public ShoppingCart addLineItemToCart(ShoppingCart shoppingCart) {

		shoppingCart.getLineItems().forEach(lineItem -> {
			Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
			if (optItem.isPresent()) {
				float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

				lineItem.setPrice(optItem.get().getPrice() - discountPerItem);
				lineItem.setAmount(lineItem.getPrice() * lineItem.getQuantity());
				lineItem.setStatus("ADDED");
				lineItem.setUserId(shoppingCart.getUserId());

				lineItem.setDeleted(false);
				lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
				lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
				lineItemRepository.save(lineItem);
			}
		});
		return shoppingCart;
	}

	@Override
	public ShoppingCart updateLineItemToCart(@Valid ShoppingCart shoppingCart) {

		shoppingCart.getLineItems().forEach(lineItem -> {
			Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
			if (optItem.isPresent()) {
				float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

				lineItem.setPrice(optItem.get().getPrice() - discountPerItem);
				lineItem.setAmount(lineItem.getPrice() * lineItem.getQuantity());
				lineItem.setStatus("ADDED");
				lineItem.setUserId(shoppingCart.getUserId());
				lineItem.setDeleted(false);
				lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
				lineItemRepository.save(lineItem);
			}
		});
		return shoppingCart;
	}

	@Override
	public Page<LineItem> listByUserId(Long userId, Pageable pageable) {
		Page<LineItem> pageItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId, "Added", pageable);
		pageItems.forEach(item -> {
			Optional<Item> optItem = itemRepository.findById(item.getProductId());
			item.setName(optItem.get().getName());
			item.setLanguage(optItem.get().getLanguage());
			item.setPicture(optItem.get().getPicture());
		});

		return pageItems;

	}

}
