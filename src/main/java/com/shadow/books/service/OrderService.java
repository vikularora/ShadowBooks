package com.shadow.books.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shadow.books.domain.Address;
import com.shadow.books.domain.LineItem;
import com.shadow.books.domain.Order;

public interface OrderService {

	Order add(Order order);

//	Order update(Order order);
//
//	Page<Order> getOrderList(int page, int size);

	Optional<Order> updateDelevieryAddressByOrderId(Long id, Address address);

	Optional<Order> updateOrderStatus(Order order);

	List<Order> findOrdersByUserId(long userId);
	
	List<LineItem> findOrderById(long orderId);

	Page<Order> findAll(Pageable pageable);


}

