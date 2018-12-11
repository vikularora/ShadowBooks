package com.shadow.books.service;

import java.util.List;

import javax.validation.Valid;

import com.shadow.books.domain.LineItem;
import com.shadow.books.util.ShoppingCart;

public interface LineItemService {

//	public ShoppingCart addItemToCart(ShoppingCart shoppingCart);

	public ShoppingCart updateCartItems(@Valid LineItem lineItem,Long userId);

	public List<LineItem> getShoppingCartByUserId(Long userId);

	ShoppingCart addItemToCart(long userId, LineItem lineItem);

	ShoppingCart deleteCartItemByUserId(long itemId, Long userId);

}
