package com.shadow.books.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shadow.books.domain.LineItem;
import com.shadow.books.util.ShoppingCart;

public interface LineItemService {

	public ShoppingCart addLineItemToCart(ShoppingCart shoppingCart);

	public ShoppingCart updateLineItemToCart(@Valid ShoppingCart shoppingCart);

	public Page<LineItem> listByUserId(Long userId, Pageable pageable);

}
