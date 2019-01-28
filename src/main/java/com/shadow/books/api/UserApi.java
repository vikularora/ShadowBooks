package com.shadow.books.api;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.domain.Order;
import com.shadow.books.domain.OrderOnDemand;
import com.shadow.books.domain.Suggestion;
import com.shadow.books.domain.User;
import com.shadow.books.service.OrderOnDemandService;
import com.shadow.books.service.OrderService;
import com.shadow.books.service.SuggestionService;
import com.shadow.books.service.UserService;

@RestController
@RequestMapping("/users/")
public class UserApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	OrderService orderService;

	@Autowired
	OrderOnDemandService orderOnDemandService;

	@Autowired
	SuggestionService suggestionService;

	@Autowired
	private UserService userService;

	@CrossOrigin
	@PostMapping("add")
	public ResponseEntity<User> add(@RequestBody User user) throws Exception {
		user = userService.add(user);

		if (user != null) {
			logger.info("USER NOT NULL:: " + user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		logger.info("NULLA USER :: " + user);
		return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@PutMapping("update")
	public ResponseEntity<User> update(@RequestBody User user) throws Exception {

		user = userService.update(user);

		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.NOT_MODIFIED);
	}

	@CrossOrigin
	@GetMapping("list")
	public ResponseEntity<Page<User>> list(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "filter") String filter) throws Exception {

		Page<User> usersList = userService.list(page, size);

		if (usersList.getContent() == null) {
			return new ResponseEntity<Page<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<User>>(usersList, HttpStatus.OK);

	}

	@CrossOrigin
	@GetMapping("list/{id}")
	public ResponseEntity<Optional<User>> getById(@PathVariable(name = "id", required = true) Long id)
			throws Exception {
		Optional<User> opUser = userService.finById(id);

		if (opUser.isPresent()) {
			return new ResponseEntity<Optional<User>>(opUser, HttpStatus.OK);
		}
		return new ResponseEntity<Optional<User>>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable(name = "id", required = true) Long id) throws Exception {
		userService.delete(id);
	}

	@GetMapping("{userId}/orders")
	public ResponseEntity<Page<Order>> orderByUserId(@PathVariable("userId") long userId,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Order> orderList = orderService.findOrdersByUserId(userId, pageable);
		if (orderList != null) {
			return new ResponseEntity<Page<Order>>(orderList, HttpStatus.OK);
		}
		return new ResponseEntity<Page<Order>>(orderList, HttpStatus.NO_CONTENT);

	}

	@PostMapping("on_demand/orders")
	public ResponseEntity<OrderOnDemand> addOnDemandOrder(@RequestBody OrderOnDemand orderOnDemand) throws Exception {

		logger.info("ORDER ON DEMAND ADD BODY :: " + orderOnDemand);
		orderOnDemand = orderOnDemandService.addOnDemandOrder(orderOnDemand);

		if (orderOnDemand != null) {
			return new ResponseEntity<OrderOnDemand>(orderOnDemand, HttpStatus.CREATED);
		}
		return new ResponseEntity<OrderOnDemand>(orderOnDemand, HttpStatus.NO_CONTENT);
	}

	@PostMapping("{userId}/suggestions")
	public ResponseEntity<Suggestion> addUserSuggestions(@PathVariable("userId") long userId,
			@RequestBody Suggestion suggestion) throws Exception {

		logger.info("ORDER ON DEMAND ADD BODY :: " + suggestion);
		suggestion = suggestionService.add(suggestion, userId);

		if (suggestion != null) {
			return new ResponseEntity<Suggestion>(suggestion, HttpStatus.CREATED);
		}
		return new ResponseEntity<Suggestion>(suggestion, HttpStatus.NO_CONTENT);
	}

}
