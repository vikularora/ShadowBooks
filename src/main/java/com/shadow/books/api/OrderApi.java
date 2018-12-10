package com.shadow.books.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.domain.Orders;
import com.shadow.books.service.OrderService;

@RestController
@RequestMapping("/orders/")
public class OrderApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	OrderService orderService;

	@CrossOrigin
	@PostMapping("add")
	public ResponseEntity<Orders> add(@RequestBody Orders order) throws Exception {
		Orders result = orderService.add(order);

		if (result != null) {
			return new ResponseEntity<Orders>(result, HttpStatus.CREATED);
		}
		return new ResponseEntity<Orders>(result, HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@PutMapping("update")
	public ResponseEntity<Orders> updateOrders(@RequestBody Orders order) throws Exception {
		Orders result = orderService.update(order);

		if (result != null) {
			return new ResponseEntity<Orders>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Orders>(result, HttpStatus.NOT_MODIFIED);
	}

	@CrossOrigin
	@GetMapping("list")
	private ResponseEntity<Page<Orders>> list(
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "filter") String filter) throws Exception {

		orderService.getOrderList(page, size);
		return null;
	}
//	@CrossOrigin
//	@GetMapping("list")
//	public ResponseEntity<Page<User>> list(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
//			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
//			@RequestParam(required = false, name = "filter") String filter) throws Exception {
//
//		Page<User> usersList = userService.list(page, size);
//
//		if (usersList.getContent() == null) {
//			return new ResponseEntity<Page<User>>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<Page<User>>(usersList, HttpStatus.OK);
//
//	}

}
