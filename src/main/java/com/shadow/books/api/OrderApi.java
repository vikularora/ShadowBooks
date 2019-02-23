package com.shadow.books.api;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.Constants.DBConstants;
import com.shadow.books.domain.Address;
import com.shadow.books.domain.LineItem;
import com.shadow.books.domain.Order;
import com.shadow.books.service.OrderService;

@RestController
@RequestMapping("/orders/")
public class OrderApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	OrderService orderService;

	@CrossOrigin
	@PostMapping()
	public ResponseEntity<Order> add(@RequestBody Order order) throws Exception {

		logger.info("CALLING ORDER ADD :: " + order);
		Order result = orderService.add(order);

		if (result != null) {
			if (result.getStatus().equalsIgnoreCase(DBConstants.PENDING)) {

				return new ResponseEntity<Order>(result, HttpStatus.CREATED);
			} else {

				return new ResponseEntity<Order>(result, HttpStatus.NOT_MODIFIED);
			}
		}
		return new ResponseEntity<Order>(result, HttpStatus.NO_CONTENT);
	}

	// for testing failed flow

//	@CrossOrigin
//	@PostMapping()
//	public ResponseEntity<Order> add(@RequestBody Order order) throws Exception {
//
//		logger.info("CALLING ORDER ADD :: " + order);
//		Order result = orderService.add(order);
//
//		if (result != null) {
//			if (result.getStatus().equalsIgnoreCase(DBConstants.PENDING)) {
//
//				return new ResponseEntity<Order>(result, HttpStatus.NO_CONTENT);
//			} else {
//
//				return new ResponseEntity<Order>(result, HttpStatus.NO_CONTENT);
//			}
//		}
//		return new ResponseEntity<Order>(result, HttpStatus.NO_CONTENT);
//	}

	@CrossOrigin
	@PutMapping("{id}/address")
	public ResponseEntity<Optional<Order>> updateOrders(@RequestBody Address address,
			@PathVariable(name = "id", required = true) Long id) throws Exception {

		logger.info("CALLING UPDATE DELEVIERY ADDRESS BY ORDERID :: " + address);
		Optional<Order> optOrder = orderService.updateDelevieryAddressByOrderId(id, address);
		logger.info("UPDATED DELEVIERY ADDRESS :: " + optOrder);

		if (optOrder.isPresent()) {
			return new ResponseEntity<Optional<Order>>(optOrder, HttpStatus.OK);
		}
		return new ResponseEntity<Optional<Order>>(optOrder, HttpStatus.NOT_MODIFIED);
	}

	@CrossOrigin
	@PutMapping()
	public ResponseEntity<Optional<Order>> updateOrderStatus(@RequestBody Order order) throws Exception {

		logger.info("CALLING UPDATE ORDER STATUS :: " + order);
		Optional<Order> optionalOrder = orderService.updateOrderStatus(order);
		logger.info("UPDATED ORDER STATUS :: " + optionalOrder);

		if (optionalOrder.isPresent()) {
			return new ResponseEntity<Optional<Order>>(optionalOrder, HttpStatus.OK);
		}
		return new ResponseEntity<Optional<Order>>(optionalOrder, HttpStatus.NOT_MODIFIED);
	}

	@GetMapping("{id}")
	public ResponseEntity<List<LineItem>> orderById(@PathVariable("id") long id) {

		logger.info("CALLING FIND ORDER BY ID:: " + id);
		List<LineItem> orderedItems = orderService.findOrderById(id);
		logger.info("ORDERED ITEMS LIST :: " + orderedItems);

		if (!orderedItems.isEmpty()) {
			return new ResponseEntity<List<LineItem>>(orderedItems, HttpStatus.OK);
		}
		return new ResponseEntity<List<LineItem>>(orderedItems, HttpStatus.NOT_MODIFIED);

	}

	@GetMapping
	public ResponseEntity<Page<Order>> allOrders(
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "status", defaultValue = "") String status) {

		Pageable pageable = PageRequest.of(page, size);
		logger.info("CALLING ALL ORDER LIST");
		Page<Order> orders = orderService.findAll(status, pageable);
		logger.info("ORDERED LIST :: " + orders);

		if (!orders.isEmpty()) {
			return new ResponseEntity<Page<Order>>(orders, HttpStatus.OK);
		}
		return new ResponseEntity<Page<Order>>(orders, HttpStatus.NOT_MODIFIED);

	}

}
