package com.shadow.books.api;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.domain.LineItem;
import com.shadow.books.service.LineItemService;
import com.shadow.books.util.ShoppingCart;

@RestController
@RequestMapping("/lineitems/")
public class LineItemApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private LineItemService lineItemService;

	@PostMapping("add")
	private ResponseEntity<ShoppingCart> addToCart(@Valid @RequestBody ShoppingCart shoppingCart) {

		shoppingCart = lineItemService.addLineItemToCart(shoppingCart);

		if (shoppingCart != null) {
			return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.CREATED);
		}
		return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.NO_CONTENT);
	}

	@PutMapping("update")
	private ResponseEntity<ShoppingCart> updateToCart(@Valid @RequestBody ShoppingCart shoppingCart) {
		shoppingCart = lineItemService.updateLineItemToCart(shoppingCart);
		if (shoppingCart != null) {
			return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.OK);
		}
		return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.NOT_MODIFIED);
	}

	@GetMapping("list/{userId}")
	private ResponseEntity<Page<LineItem>> list(@PathVariable(name = "userId", required = true) Long userId,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page) {

		Pageable pageable = PageRequest.of(page, size);
		Page<LineItem> pageList = lineItemService.listByUserId(userId, pageable);

		if (pageList.isEmpty()) {
			return new ResponseEntity<Page<LineItem>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Page<LineItem>>(pageList, HttpStatus.OK);
	}

}