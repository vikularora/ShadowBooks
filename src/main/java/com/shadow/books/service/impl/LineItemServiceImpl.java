package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Address;
import com.shadow.books.domain.Item;
import com.shadow.books.domain.LineItem;
import com.shadow.books.dto.CartDto;
import com.shadow.books.repository.AddressRepository;
import com.shadow.books.repository.ItemRepository;
import com.shadow.books.repository.LineItemRepository;
import com.shadow.books.service.LineItemService;

@Service
public class LineItemServiceImpl implements LineItemService {

	@Autowired
	LineItemRepository lineItemRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	AddressRepository addressRepository;

	/*
	 * public ShoppingCart addItemToCart(long userId, LineItem lineItem) {
	 * 
	 * System.out.println("------list--------"+lineItem); Optional<Item> optItem =
	 * itemRepository.findById(lineItem.getProductId()); if (optItem.isPresent()) {
	 * float discountPerItem = (optItem.get().getPrice() *
	 * optItem.get().getDiscount()) / 100;
	 * 
	 * lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
	 * lineItem.setAmount(lineItem.getUnitPrice() * lineItem.getQuantity());
	 * lineItem.setStatus("ADDED"); lineItem.setUserId(userId);
	 * 
	 * lineItem.setDeleted(false);
	 * lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).
	 * getTimeInMillis());
	 * lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).
	 * getTimeInMillis());
	 * 
	 * System.out.println("-------before save----"+lineItem);
	 * lineItemRepository.save(lineItem); } return new ShoppingCart(userId,
	 * getShoppingCartByUserId(userId).stream().collect(Collectors.toSet())); }
	 */
	@Override
	public LineItem addItemToCart(long userId, LineItem lineItem) {

		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		LineItem result = null;
		if (optItem.isPresent()) {
			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
			lineItem.setStatus("ADDED");
			lineItem.setUserId(userId);

			lineItem.setDeleted(false);
			lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());

			result = lineItemRepository.save(lineItem);
		}
		return result;
	}

	@Override
	public LineItem updateCartItems(@Valid LineItem lineItem, Long userId) {

		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		if (optItem.isPresent()) {
			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
			lineItem.setStatus("ADDED");
			lineItem.setUserId(userId);
			lineItem.setDeleted(false);
			lineItem.setOrderId(null);
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			System.out.println("lineItem ::" + lineItem);
			return lineItemRepository.save(lineItem);
		}
		return null;
	}

	@Override
	public List<LineItem> getShoppingCartByUserId(Long userId) {
		List<LineItem> pageItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId, "Added");
		pageItems.forEach(item -> {
			Optional<Item> optItem = itemRepository.findById(item.getProductId());
			item.setName(optItem.get().getName());
			item.setLanguage(optItem.get().getLanguage());
			item.setImageUrl(optItem.get().getImageUrl());
		});

		return pageItems;

	}

	@Override
	public CartDto getCartFullDetails(CartDto cartDto) {

		if (cartDto.getType().equalsIgnoreCase("cart")) {

			List<LineItem> pageItems = getShoppingCartByUserId(cartDto.getUserId());
			if (pageItems.isEmpty()) {
				return null;
			}
			List<Address> address = addressRepository.findByIsSelectedAndUserId(true, cartDto.getUserId());

			System.out.println(address + " ::  Address ,, Cart details ::" + pageItems);

			cartDto.setAddress(address);
			cartDto.setCartDetails(pageItems);

			return cartDto;
		} else {
			Optional<Item> optItem = itemRepository.findById(cartDto.getProductId());
			if (optItem.isPresent()) {

				float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;
				LineItem lineItem = new LineItem();

				lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
				lineItem.setAmount((float) (lineItem.getUnitPrice() * cartDto.getQuantity()));
				lineItem.setName(optItem.get().getName());
				lineItem.setUserId(cartDto.getUserId());
				lineItem.setQuantity(cartDto.getQuantity());
				lineItem.setProductId(cartDto.getProductId());
				lineItem.setLanguage(optItem.get().getLanguage());
				lineItem.setImageUrl(optItem.get().getImageUrl());
				cartDto.getCartDetails().add(lineItem);
//				cartDto.getCartDetails().forEach(cart->{
//					float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;
//					cart.setUnitPrice(optItem.get().getPrice() - discountPerItem);
//					cart.setAmount((float) (cart.getUnitPrice() * cartDto.getQuantity()));
//					cart.setName(optItem.get().getName());
//					cart.setQuantity(cartDto.getQuantity());
//					cart.setProductId(cartDto.getProductId());
//					cart.setLanguage(optItem.get().getLanguage());
//					cart.setPicture(optItem.get().getPicture());
//									
//				});
			}

			System.out.println(cartDto + "  :: ");

			List<Address> address = addressRepository.findByIsSelectedAndUserId(true, cartDto.getUserId());
			cartDto.setAddress(address);
			return cartDto;
		}
	}

	@Override
	public void deleteCartItemByUserId(long itemId, Long userId) {
		lineItemRepository.deleteById(itemId);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public ShoppingCart deleteCartItemByUserId(long itemId,Long userId) {
//
//		itemRepository.deleteById(itemId);
//		return new ShoppingCart(userId, (Set<LineItem>) getShoppingCartByUserId(userId));
//	}

}
