package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	@SuppressWarnings("unchecked")
	@Override
	
	/*
	public ShoppingCart addItemToCart(long userId, LineItem lineItem) {

		System.out.println("------list--------"+lineItem);
		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		if (optItem.isPresent()) {
			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount(lineItem.getUnitPrice() * lineItem.getQuantity());
			lineItem.setStatus("ADDED");
			lineItem.setUserId(userId);

			lineItem.setDeleted(false);
			lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			
			System.out.println("-------before save----"+lineItem);
			lineItemRepository.save(lineItem);
		}
		return new ShoppingCart(userId, getShoppingCartByUserId(userId).stream().collect(Collectors.toSet()));
	}
	 */
	
	public LineItem addItemToCart(long userId, LineItem lineItem) {

		System.out.println("------list--------"+lineItem);
		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		LineItem result = null;
		if (optItem.isPresent()) {
			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount(lineItem.getUnitPrice() * lineItem.getQuantity());
			lineItem.setStatus("ADDED");
			lineItem.setUserId(userId);

			lineItem.setDeleted(false);
			lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			
			result = lineItemRepository.save(lineItem);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShoppingCart updateCartItems(@Valid LineItem lineItem,  Long userId) {

			Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
			if (optItem.isPresent()) {
				float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

				lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
				lineItem.setAmount(lineItem.getUnitPrice() * lineItem.getQuantity());
				lineItem.setStatus("ADDED");
				lineItem.setUserId(lineItem.getUserId());
				lineItem.setDeleted(false);
				lineItem.setOrderId(null);
				lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
				lineItemRepository.save(lineItem);
			}
		return new ShoppingCart(userId, (Set<LineItem>) getShoppingCartByUserId(userId));
	}

	@Override
	public List<LineItem> getShoppingCartByUserId(Long userId) {
		List<LineItem> pageItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId, "Added");
		pageItems.forEach(item -> {
			Optional<Item> optItem = itemRepository.findById(item.getProductId());
			item.setName(optItem.get().getName());
			item.setLanguage(optItem.get().getLanguage());
			item.setPicture(optItem.get().getPicture());
		});

		return pageItems;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ShoppingCart deleteCartItemByUserId(long itemId,Long userId) {

		itemRepository.deleteById(itemId);
		return new ShoppingCart(userId, (Set<LineItem>) getShoppingCartByUserId(userId));
	}

}
