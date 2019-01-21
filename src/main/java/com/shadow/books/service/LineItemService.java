package com.shadow.books.service;

import java.util.List;

import javax.validation.Valid;

import com.shadow.books.domain.LineItem;
import com.shadow.books.dto.CartDto;

public interface LineItemService {

//	public ShoppingCart addItemToCart(ShoppingCart shoppingCart);

	public LineItem updateCartItems(@Valid LineItem lineItem,Long userId);

	public List<LineItem> getShoppingCartByUserId(Long userId);

//	ShoppingCart addItemToCart(long userId, LineItem lineItem);
	
	LineItem addItemToCart(LineItem lineItem);

//	ShoppingCart deleteCartItemByUserId(long itemId, Long userId);
	
	void deleteCartItemByUserId(long itemId, Long userId);

	public CartDto getCartFullDetails(CartDto cartDto);

}
