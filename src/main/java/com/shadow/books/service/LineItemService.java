package com.shadow.books.service;

import java.util.List;

import javax.validation.Valid;

import com.shadow.books.domain.LineItem;
import com.shadow.books.dto.CartDto;
import com.shadow.books.dto.SizeDto;

public interface LineItemService {

//	public ShoppingCart addItemToCart(ShoppingCart shoppingCart);

	public LineItem updateCartItems(@Valid LineItem lineItem);

	public List<LineItem> getShoppingCartByUserId(Long userId);

//	ShoppingCart addItemToCart(long userId, LineItem lineItem);

	SizeDto addItemToCart(LineItem lineItem);

//	ShoppingCart deleteCartItemByUserId(long itemId, Long userId);

	void deleteCartItemByUserId(long itemId, Long userId);

	public CartDto getCartFullDetails(CartDto cartDto);


	public SizeDto checkCartSize(long userId);

}
