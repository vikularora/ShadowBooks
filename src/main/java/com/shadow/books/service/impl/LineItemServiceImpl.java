package com.shadow.books.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	LineItemRepository lineItemRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	AddressRepository addressRepository;

//	@Override
//	public LineItem addItemToCart(LineItem lineItem) {
//
//		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
//		LineItem result = null;
//		if (optItem.isPresent()) {
//
//			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;
//
//			LineItem isPresent = checkIfProductAlreadyExistInCart(lineItem, discountPerItem, optItem);
//			if (isPresent != null) {
//				return isPresent;
//			}
//
//			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
//			lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
//			lineItem.setName(optItem.get().getName());
//			lineItem.setStatus(DBConstants.IN_CART);
//			lineItem.setUserId(lineItem.getUserId());
//
//			lineItem.setDeleted(false);
//			lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
//			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
//
//			result = lineItemRepository.save(lineItem);
//		}
//		return result;
//	}
//
//	private LineItem checkIfProductAlreadyExistInCart(LineItem lineItem, float discountPerItem,
//			Optional<Item> optItem) {
//
//		LineItem lineItemDetails = lineItemRepository.findByUserIdAndStatusAndProductIdAndOrderIdIsNull(
//				lineItem.getUserId(), DBConstants.IN_CART, lineItem.getProductId());
//
//		if (lineItemDetails != null) {
//			lineItemDetails.setQuantity(lineItemDetails.getQuantity() + lineItem.getQuantity());
//			lineItemDetails.setUnitPrice(optItem.get().getPrice() - discountPerItem);
//			lineItemDetails.setAmount((float) (lineItemDetails.getUnitPrice() * lineItemDetails.getQuantity()));
//			return lineItemRepository.save(lineItemDetails);
//		}
//		return null;
//	}

	@Override
	public SizeDto addItemToCart(LineItem lineItem) {

		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		SizeDto size = null;
		logger.info("OPTIONAL ITEM :: " + optItem);

		if (optItem.isPresent()) {

			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;
			logger.info("CHECKING IF PRODUCT ALREADY EXISTS IN CART ");
			LineItem lineItemDetail = lineItemRepository.findByUserIdAndStatusAndProductIdAndOrderIdIsNull(
					lineItem.getUserId(), DBConstants.IN_CART, lineItem.getProductId());

			if (lineItemDetail != null) {
				logger.info("PRODUCT ALREADY IN CART :: " + lineItemDetail);
				int totalQuantity = lineItemDetail.getQuantity() + lineItem.getQuantity();

				if (optItem.get().getQuantity() >= totalQuantity) {

					logger.info("QUANTITY AVAILABLE");
					lineItemDetail.setQuantity(lineItemDetail.getQuantity() + lineItem.getQuantity());
					lineItemDetail.setUnitPrice(optItem.get().getPrice() - discountPerItem);
					lineItemDetail.setAmount((float) (lineItemDetail.getUnitPrice() * lineItemDetail.getQuantity()));
					logger.info("BEFORE UPDATING LINEITEMS");
					lineItemRepository.save(lineItemDetail);

					size = checkCartSize(lineItem.getUserId());
					size.setStatus(DBConstants.AVAILABLE);
					return size;

				} else {

					size = checkCartSize(lineItem.getUserId());
					size.setStatus(DBConstants.BOOK_OUT_OF_STOCK);
					return size;
				}

			} else {
				logger.info("PRODUCT NOT EXISTS IN CART");
				if (optItem.get().getQuantity() >= lineItem.getQuantity()) {

					logger.info("QUANTITY AVAILABLE");
					lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
					lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
					lineItem.setName(optItem.get().getName());
					lineItem.setStatus(DBConstants.IN_CART);
					lineItem.setUserId(lineItem.getUserId());
					lineItem.setDeleted(false);
					lineItem.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
					lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
					logger.info("BEFORE UPDATING LINE ITEMS");
					lineItemRepository.save(lineItem);

					size = checkCartSize(lineItem.getUserId());
					size.setStatus(DBConstants.AVAILABLE);
					return size;

				} else {
					size = checkCartSize(lineItem.getUserId());
					size.setStatus(DBConstants.BOOK_OUT_OF_STOCK);
					return size;
				}
			}
		}
		return size;
	}

	@Override
	public LineItem updateCartItems(@Valid LineItem lineItem) {

		Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
		logger.info("OPTIONAL ITEM DETAIL :: " + optItem);

		if (optItem.isPresent()) {
			float discountPerItem = (optItem.get().getPrice() * optItem.get().getDiscount()) / 100;

			lineItem.setUnitPrice(optItem.get().getPrice() - discountPerItem);
			lineItem.setAmount((float) (lineItem.getUnitPrice() * lineItem.getQuantity()));
			lineItem.setStatus(DBConstants.IN_CART);
			lineItem.setUserId(lineItem.getUserId());
			lineItem.setName(optItem.get().getName());
			lineItem.setDeleted(false);
			lineItem.setOrderId(null);
			lineItem.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			logger.info("BEFORE UPDATE LINE ITEM :: " + lineItem);
			return lineItemRepository.save(lineItem);
		}
		return null;
	}

	public List<LineItem> getShoppingCartReviewDetails(Long userId) {
		List<LineItem> pageItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId,
				DBConstants.IN_CART);

		logger.info("LIST OF CART DETAILS :: " + pageItems);
		List<LineItem> lineItems = new ArrayList<LineItem>();

		pageItems.forEach(item -> {
			Optional<Item> optItem = itemRepository.findById(item.getProductId());
			logger.info("ITEM OPTIONAL LIST :: " + optItem);
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
		List<LineItem> pageLineItems = lineItemRepository.findByUserIdAndStatusAndOrderIdIsNull(userId,
				DBConstants.IN_CART);

		logger.info("LIST OF LINE ITEMS :: " + pageLineItems);
		pageLineItems.forEach(lineItem -> {
			Optional<Item> optItem = itemRepository.findById(lineItem.getProductId());
			logger.info("OPTIONAL ITEM DETAIL :: " + optItem);
			if (optItem.isPresent()) {
				lineItem.setName(optItem.get().getName());
				lineItem.setLanguage(optItem.get().getLanguage());
				lineItem.setImageUrl(optItem.get().getImageUrl());
				lineItem.setItemStatus(optItem.get().getStatus());
				lineItem.setAvailableQuantity(optItem.get().getQuantity());
			}
		});
		return pageLineItems;
	}

	@Override
	public CartDto getCartFullDetails(CartDto cartDto) {

		if (cartDto.getType().equalsIgnoreCase("cart")) {

			logger.info("ORDER REVIEW THROUGH CART");
			List<LineItem> pageItems = getShoppingCartReviewDetails(cartDto.getUserId());
			logger.info("SHOPPING CART REVIEW DETAILS :: " + pageItems);

			if (pageItems.isEmpty()) {
				return null;
			}
			List<Address> address = addressRepository.findByIsSelectedAndUserId(true, cartDto.getUserId());
			logger.info("REVIEW ADDRESS :: " + address);

			cartDto.setAddress(address);
			cartDto.setCartDetails(pageItems);
			return cartDto;

		} else {

			logger.info("ORDER REVIEW THROUGH BUY NOW");
			Optional<Item> optItem = itemRepository.findById(cartDto.getProductId());
			logger.info("OPTIONAL ITEM DETAIL :: " + optItem);

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
				logger.info("CART REVIEW DETAILS :: " + cartDto);
			}

			List<Address> address = addressRepository.findByIsSelectedAndUserId(true, cartDto.getUserId());
			logger.info("REVIEW ADDRESS :: " + address);
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
		size.setSize(0);
		return size;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public ShoppingCart deleteCartItemByUserId(long itemId,Long userId) {
//
//		itemRepository.deleteById(itemId);
//		return new ShoppingCart(userId, (Set<LineItem>) getShoppingCartByUserId(userId));
//	}

}
