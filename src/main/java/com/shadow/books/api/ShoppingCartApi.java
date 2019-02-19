package com.shadow.books.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.domain.LineItem;
import com.shadow.books.dto.CartDto;
import com.shadow.books.dto.SizeDto;
import com.shadow.books.service.LineItemService;

@RestController
@RequestMapping("users/{id}/cart")
public class ShoppingCartApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private LineItemService lineItemService;

	@PostMapping()
	private ResponseEntity<SizeDto> addToCart(@PathVariable("id") long userId, @Valid @RequestBody LineItem lineItem) {

		lineItem.setUserId(userId);

		logger.info("CALLING ADD TO CART :: " + lineItem);
		SizeDto shoppingCart = lineItemService.addItemToCart(lineItem);
		logger.info("SHOPPING CART DETAILS :: " + shoppingCart);

		if (shoppingCart != null) {
			return new ResponseEntity<SizeDto>(shoppingCart, HttpStatus.CREATED);

		}
		return new ResponseEntity<SizeDto>(HttpStatus.NO_CONTENT);
	}

	@PutMapping()
	private ResponseEntity<LineItem> updateCartByUserId(@PathVariable("id") long userId,
			@Valid @RequestBody LineItem lineItem) {

		lineItem.setUserId(userId);
		logger.info("CALLING UPDATE CART BY USERID :: " + lineItem);
		LineItem updatedResult = lineItemService.updateCartItems(lineItem);
		logger.info("UPDATED CART BY USERID :: " + updatedResult);

		if (updatedResult != null) {
			return new ResponseEntity<LineItem>(updatedResult, HttpStatus.OK);
		}
		return new ResponseEntity<LineItem>(updatedResult, HttpStatus.NOT_MODIFIED);
	}

	@GetMapping()
	private ResponseEntity<List<LineItem>> list(@PathVariable(name = "id", required = true) Long id) {

		logger.info("CALLING LIST OF CART ITEMS BY USER ID :: " + id);
		List<LineItem> cartItems = lineItemService.getShoppingCartByUserId(id);
		logger.info("LIST OF CART ITEMS :: " + cartItems);

		if (cartItems.isEmpty()) {
			return new ResponseEntity<List<LineItem>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<LineItem>>(cartItems, HttpStatus.OK);
	}

//	@DeleteMapping("{itemId}")
//	private ResponseEntity<ShoppingCart> deleteCartItemByUserId(@PathVariable("id") long userId,
//			@PathVariable("itemId") long itemId) {
//		logger.info("userid is :: " + userId);
//
//		ShoppingCart shoppingCart = lineItemService.deleteCartItemByUserId(itemId, userId);
//		if (shoppingCart != null && !shoppingCart.getLineItems().isEmpty()) {
//			return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.CREATED);
//		}
//		return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.NO_CONTENT);
//	}
	@DeleteMapping("{itemId}")
	private ResponseEntity<Object> deleteCartItemByUserId(@PathVariable("id") long userId,
			@PathVariable("itemId") long itemId) {

		logger.info("CALLING DELETE WHERE PRODUCT ID IS ::" + itemId + "AND USERID IS :: " + userId);
		lineItemService.deleteCartItemByUserId(itemId, userId);
		return new ResponseEntity<Object>("Record Deleted Successfully", HttpStatus.OK);
	}

	@PostMapping("details")
	private ResponseEntity<CartDto> getFullCartDetails(@PathVariable("id") long userId,
			@Valid @RequestBody CartDto cartDto) {

		cartDto.setUserId(userId);
		logger.info("CALLING FULL CART DETAILS  ::" + cartDto);
		CartDto cartDetails = lineItemService.getCartFullDetails(cartDto);
		logger.info("LIST OF CART DETAILS :: " + cartDetails);

		if (cartDetails != null) {
			return new ResponseEntity<CartDto>(cartDetails, HttpStatus.OK);
		}
		return new ResponseEntity<CartDto>(cartDetails, HttpStatus.NO_CONTENT);
	}

}