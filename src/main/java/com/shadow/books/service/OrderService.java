package com.shadow.books.service;

import org.springframework.data.domain.Page;

import com.shadow.books.domain.Orders;

public interface OrderService {

	Orders add(Orders order);

	Orders update(Orders order);

	Page<Orders> getOrderList(int page, int size);

}
