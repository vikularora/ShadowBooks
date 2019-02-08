package com.shadow.books.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.Constants.DBConstants;
import com.shadow.books.domain.Address;
import com.shadow.books.domain.Item;
import com.shadow.books.domain.LineItem;
import com.shadow.books.dto.CartDto;
import com.shadow.books.dto.SizeDto;
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

//	 public ShoppingCart addItemToCart(long userId, LineItem lineItem) {
//	 
//	 System.out.println("------list--------"+lineItem); Optional<Item> optItem =
//	 itemRepository.findById(lineItem.getProductId()); if (optItem.isPresent()) {
//	 float discountPerItem = (optItem.get().getPrice() *
//	 optItem.get().getDiscount()) / 100;
//	 
//	 lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
//	 lineItem.setAmount(lineItem.getUnitPrice() * lineItem.getQuantity());
//	 lineItem.setStatus("ADDED"); lineItem.setUserId(userId);
//	 
//	 lineItem.setDeleted(false);
//	 lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).
//	 getTimeInMillis());
//	 lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).
//	 getTimeInMillis());
//	 
//	 System.out.println("-------before save----"+lineItem);
//	lineItemRepository.save(lineItem); } return new ShoppingCart(userId,
//	 getShoppingCartByUserId(userId).stream().collect(Collectors.toSet())); }

	@Override
	public LineItem addItemToCart(LineItem lineItem) {

		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		LineItem result = null;
		if (optItem.isPresent()) {

			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			LineItem isPresent = checkIfProductAlreadyExistInCart(lineItem, discountPerItem, optItem);
			if (isPresent != null) {
				return isPresent;
			}

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
			lineItem.setName(optItem.get().getName());
			lineItem.setStatus(DBConstants.IN_CART);
			lineItem.setUserId(lineItem.getUserId());

			lineItem.setDeleted(false);
			lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());

			result = lineItemRepository.save(lineItem);
		}
		return result;
	}

	private LineItem checkIfProductAlreadyExistInCart(LineItem lineItem, float discountPerItem,
			Optional<Item> optItem) {

		LineItem lineItemDetails = lineItemRepository.findByUserIdAndStatusAndProductIdAndOrderIdIsNull(
				lineItem.getUserId(), DBConstants.IN_CART, lineItem.getProductId());

		if (lineItemDetails != null) {
			lineItemDetails.setQuantity(lineItemDetails.getQuantity() + lineItem.getQuantity());
			lineItemDetails.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItemDetails.setAmount((float) (lineItemDetails.getUnitPrice() * lineItemDetails.getQuantity()));
			return lineItemRepository.save(lineItemDetails);
		}
		return null;
	}

	@Override
	public LineItem updateCartItems(@Valid LineItem lineItem, Long userId) {

		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		if (optItem.isPresent()) {
			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
			lineItem.setStatus(DBConstants.IN_CART);
			lineItem.setUserId(userId);
			lineItem.setName(optItem.get().getName());
			lineItem.setDeleted(false);
			lineItem.setOrderId(null);
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			return lineItemRepository.save(lineItem);
		}
		return null;
	}

	public List<LineItem> getShoppingCartReviewDetails(Long userId) {
		List<LineItem> pageItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId,
				DBConstants.IN_CART);
		List<LineItem> lineItems = new ArrayList<LineItem>();
		pageItems.forEach(item -> {
			Optional<Item> optItem = itemRepository.findById(item.getProductId());
			if (optItem.isPresent() && optItem.get().getStatus().equalsIgnoreCase(DBConstants.AVAILABLE)) {
				item.setName(optItem.get().getName());
				item.setLanguage(optItem.get().getLanguage());
				item.setImageUrl(optItem.get().getImageUrl());
				item.setItemStatus(optItem.get().getStatus());
				lineItems.add(item);

			}
		});

		return lineItems;
	}

	@Override
	public List<LineItem> getShoppingCartByUserId(Long userId) {
		List<LineItem> pageItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId,
				DBConstants.IN_CART);
		pageItems.forEach(item -> {
			Optional<Item> optItem = itemRepository.findById(item.getProductId());
			if (optItem.isPresent()) {
				item.setName(optItem.get().getName());
				item.setLanguage(optItem.get().getLanguage());
				item.setImageUrl(optItem.get().getImageUrl());
				item.setItemStatus(optItem.get().getStatus());
			}
		});

		return pageItems;
	}

	@Override
	public CartDto getCartFullDetails(CartDto cartDto) {

		if (cartDto.getType().equalsIgnoreCase("cart")) {

			List<LineItem> pageItems = getShoppingCartReviewDetails(cartDto.getUserId());
			if (pageItems.isEmpty()) {
				return null;
			}
			List<Address> address = addressRepository.findByIsSelectedAndUserId(true, cartDto.getUserId());

			cartDto.setAddress(address);
			cartDto.setCartDetails(pageItems);

			return cartDto;
		} else {
			Optional<Item> optItem = itemRepository.findById(cartDto.getProductId());

			if (optItem.isPresent() && optItem.get().getStatus().equalsIgnoreCase(DBConstants.AVAILABLE)) {

				float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;
				LineItem lineItem = new LineItem();

				lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
				lineItem.setAmount((float) (lineItem.getUnitPrice() * cartDto.getQuantity()));
				lineItem.setName(optItem.get().getName());
				lineItem.setItemStatus(optItem.get().getStatus());
				lineItem.setUserId(cartDto.getUserId());
				lineItem.setQuantity(cartDto.getQuantity());
				lineItem.setProductId(cartDto.getProductId());
				lineItem.setLanguage(optItem.get().getLanguage());
				lineItem.setImageUrl(optItem.get().getImageUrl());
				cartDto.getCartDetails().add(lineItem);
			}

			List<Address> address = addressRepository.findByIsSelectedAndUserId(true, cartDto.getUserId());
			cartDto.setAddress(address);
			return cartDto;
		}
	}

	@Override
	public void deleteCartItemByUserId(long itemId, Long userId) {
		lineItemRepository.deleteById(itemId);
	}

	@Override
	public SizeDto checkCartSize(long userId) {

		SizeDto size = new SizeDto();
		List<LineItem> lineItemList = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId,
				DBConstants.IN_CART);

		if (!lineItemList.isEmpty()) {

			size.setSize(lineItemList.size());
			return size;
		}
		return null;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public ShoppingCart deleteCartItemByUserId(long itemId,Long userId) {
//
//		itemRepository.deleteById(itemId);
//		return new ShoppingCart(userId, (Set<LineItem>) getShoppingCartByUserId(userId));
//	}

}
